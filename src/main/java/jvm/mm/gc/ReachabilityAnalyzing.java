package jvm.mm.gc;

import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 如何判断对象可以回收:
 *
 *    引用计数算法的弊端:
 *        循环引用: A引用B, B引用A
 *        java未采用该算法
 *
 *    可达性分析算法:(跟搜索算法) java, C#
 *        跟对象: 肯定不能被当成垃圾的而被回收的对象
 *        对堆内存的对象进行扫描, 然后沿着GC Root对象为起点的引用链找到该对象, 找不到, 表示可以回收
 *
 *        GC Roots:
 *            VM栈(帧中的本地变量)中的引用
 *            方法区中的静态引用
 *            JNI中的引用
 *
 *     堆中, 尤其是新生代, 一次GC可以回收 70% ~ 95%的空间, 方法区的GC效率远小于此
 *     方法区的GC, 主要回收废弃常量与无用类
 *     在大量使用发射,动态代理,GCLib等字节码框架, 动态生成JSP以及OSGI这类频繁自定义ClassLoader的场景都需要JVM具备类卸载的支持以保证方法区不会溢出
 *
 *     类回收条件:
 *        该类所有实例都已经被GC
 *        该类的ClassLoader已经被GC
 *        该类的java.lang.Class对象没有在任何地方被引用
 *
 *
 */
public class ReachabilityAnalyzing {

    public static void main(String[] args) {
        testGcRoot();
//        testSoftRefs1();
//        testSoftRefs2();
//        testRefQueue();
    }

    private static void testRefQueue() {
        List<SoftReference<byte[]>> list = new ArrayList<>();
        // 引用队列
        ReferenceQueue<byte[]> q = new ReferenceQueue<>();
        for (int i = 0; i < 5; i++) {
            // 当软引用关联的 byte数组被回收时, 软引用自身就会被加入到q队列中
            final SoftReference<byte[]> sr = new SoftReference<>(new byte[_4MB], q);
            System.out.println(sr.get());
            list.add(sr);
            System.out.print(list.size());
            System.out.println();
        }
        // 当遍历软引用时
        // 获去无用的软引用, 并移除
        Reference<? extends byte[]> poll = q.poll();
        while (poll != null) {
            list.remove(poll);
            poll = q.poll();
//            System.out.println(poll);
        }
        System.out.println("--------------");
        for (SoftReference<byte[]> ref : list) {
            System.out.println(ref.get());
        }
    }

    /**
     * 引用类型:
     *     强引用:
     *        通过new对象, 使用变量指向该对象都是强引用
     *        强引用都断开时,对象才能被回收
     *        只有所有GC Roots对象都不通过 强引用 引用该对象,该对象才能被垃圾回收
     *
     *     软引用(SoftReference):
     *        只要对象没有被变量直接引用, 当gc时,软引用会因为内存不足而被回收;
     *
     *     弱引用(WeakReference):
     *        不论内存是否充裕, 弱引用都会被回收
     *        引用队列:
     *            当被软引用引用的对象被回收掉以后,软引用自身也是一个人对象,如果其被创建时被分配了一个引用队列, 其被引用的对象被回收时, 软引用本身就会进入该队列
     *            当被弱引用引用的对象被回收时, 弱引用也会进入该队列
     *
     *
     *     虚引用(PhantomReference) [ByteBuffer, Cleaner]
     *          虚引用创建时, 会创建一个引用队列
     *          虚引用用于管理直接内存, 当直接内存没有强引用引用时, ByteBuffer可以直接被回收, 直接内存不能被java gc管理, 所以必须在ByteBuffer被回收时让虚引用对象进入引用队列
     *
     *     终结器引用(FinalReference)
     *          Object.finalize()终结方法被调用后, 进入引用队列 ,会被FinalizeHandler线程(该线程优先级很低)回收, 所以不推荐使用finalize释放资源
     */
    private static final int _4MB = 4 * 1024 * 1024;
    private static void testSoftRefs1() {
        final ArrayList<byte[]> bytes = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            bytes.add(new byte[_4MB]); // 某些资源如图片, 由于占用内存, 在使用时可以不使用强引用
        }
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 使用SoftReference消除 以上方法的 OutOfMemory异常
    private static void testSoftRefs2() {
        final ArrayList<SoftReference<byte[]>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            final SoftReference<byte[]> ref = new SoftReference<>(new byte[_4MB]);
            System.out.println(ref.get());
            list.add(ref);
            System.out.println(list.size());
        }
        System.out.println("循环结束: " + list.size());
        for (SoftReference<byte[]> ref : list) {
            System.out.println(ref.get());
        }
    }

    /**
     *
     *  哪些对象可以作为GC Root:
     *      System Class: Object, HashMap ...
     *      Native Stack: Class, String
     *      Thread: 线程栈帧内部引用的对象
     *      Busy Monitor: ReferenceQueue, Reference
     *
     */
    private static void testGcRoot() {
        // 线程栈帧引用对象, 所以gc root会进行引用
        ArrayList<Object> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        System.out.println(1);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        list = null;

        System.out.println(2);
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("end ...");
    }
}
