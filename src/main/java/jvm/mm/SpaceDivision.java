package jvm.mm;

//import net.sf.cglib.proxy.Enhancer;
//import net.sf.cglib.proxy.MethodInterceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * 虚拟机栈:   Stack Frame栈帧
 * 程序计数器: PC
 * 本地方法栈: 用于处理本地方法
 * 堆(Heap):  hotspot实现是, 引用直接指向实例数据本身,
 *            实例数据本身又有指针指向元数据区域
 * 方法区: 存储元数据, 1.8之后已经彻底废弃了永久代方法区, 使用元空间
 * 运行时常量池: 方法区的一部分内容
 * 直接内存:
 *
 * java对象创建过程:
 *     1. 堆内存创建对象实例
 *         如果堆有序,
 *     2. 为对象实例变量赋初始值
 *     3. 将对象的引用返回
 *
 * 指针碰撞:
 *     对象所需内存大小在类加载完成后可以完全确定, 为对象分配空间的任务等同于把一块确定大小的内存从java堆中划分出来
 *     假设java堆内存绝对规整, 用过的内存放一边,空闲内存方另一边,中间放一个指针作为分界点指示器,那所分配的内存仅仅就是把指针往空闲空间那一边挪动一段与对象大小相等的距离
 *     指针碰撞的前提是
 *
 * 空闲列表:
 *     堆内存空间中已被使用与未被使用交织在一起, 此时jvm需要一个列表来记录
 *
 * 引用访问对象的方式:
 *     句柄
 *     直接指针
 *
 * JAVA永久代去哪儿了:
 *     在过去, 类因很少被卸载和回收, JDK8之前类被放入永久代, 永久代是一块连续的内存空间, 永久代的垃圾回收和老年代的垃圾回收是绑定的
 *     JDK7之前, 纳入字符串常量池的字符串被存储在永久代中, 因此导致了一系列的性能问题问题和内存溢出错误
 *
 * Java8之后, 永久带废弃, 类元数据被移到元空间
 *     由于类的元数据分配在本地内存中, 元空间最大分配空间就是系统可用内存空间, 因此不会存在永久代时代的内存溢出错误
 *
 * 元空间内存管理:
 *     元空间内存管理由jvm来完成, 元空间中, 类和其元数据的生命周期和其对应的类加载器是相同的, 只要加载器存活, 其加载的元数据也是存活的
 *     每一个类加载器的存储空间区域都叫元空间, 元空间中回收过程中没有重定位和压缩等操作
 *
 * 永久代 ---> 元空间:
 *     jdk7之前的设计:
 *         过去类几乎是静态的很少被卸载和回收, 因此类可以被看做是永久的, 由于类作为JVM实现的一部分, 不由程序创建, 因此被认为是非堆内存
 *         永久代的垃圾回收是绑定的, 一旦一个区域被占满, 通过-XX:MaxPermSize设置永久代大小, 一旦元数据超过设定大小, 出现OOM
 *         字符串常量池存储在永久代, 导致一系列性能和OOM
 *         永久带中的数据很难调优, 可能会随着每次full gc发生而进行移动, STW很长
 *     jdk8:
 *         元数据信息被移到本地内存区域
 *         类的元数据存储在本地内存, 元空间最大可分配空间就是系统空间, 因此不会有永久代的OOM
 *         JVM会动增加元空间的容量
 *         永久代的移除并不意味着自定义的类加载器泄漏问题就解决了, 因此要监控内存消耗情况
 *
 * 元空间内存管理细节:
 *     元空间虚拟机负责元空间的分配,采用形式为组块分配, 组块大小因类加载器类型而异
 *     元空间中, 类和元数据的生命周期何其对应的类加载器是相同的
 *     准确说, 每个类加载器的存储区域都称作一个元空间, 所有元空间合在一起就是我们所说的元空间
 *     元空间回收时没有重定位和压缩操作, 但是会进行扫描
 *
 *     元空间分配:
 *         组块分配, 组块大小因类加载器类型而异
 *
 *         空闲组块列表:
 *          元空间虚拟机有一个全局的空闲组块列表, 类加载器需要组块时, 从该全局列表中获取并维持自己的组块列表,
 *          类加载器不再存活时, 其所有的组块列表被释放, 被返还给全局组块列表
 *          类加载器持有的组块又被分为多个块,每一块存储一个单元的信息
 *
 *          <<< 组块中的块是线性分配(指针碰撞分配形式) >>> (内存管理方式: 指针碰撞, 空闲列表)
 *
 *          组块分配自内存映射区域,
 *          这些全局的虚拟内存映射区域以链表形式连接, 一旦某个虚拟内存区域清空, 这部分内存被返还给OS
 *
 *          类的大小各异(成员, 方法信息, 常量池), 因此元信息大小更异,
 *
 * 内存参数调优:
 *     -XX:MetaspaceSize, 64位机器为21M, 这是初始高水位线, 一旦触及会触发full gc, 如果要避免频繁的full gc, 该值要设置为较高值
 *     jmap
 *
 */
public class SpaceDivision {
    public static void main(String[] args) {
        testOom();
//        testSof();
//        testMetaSpaceOom();
    }

    /**
     * 方法区oom
     *
     * -XX:MaxMetaspaceSize=10M
     * -XX:MinMetaspaceFreeRatio
     * -XX:MaxMetaspaceFreeRatio
     *
     * Exception in thread "main" java.lang.OutOfMemoryError: Metaspace
     */
    private static void testMetaSpaceOom() {
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (;;) {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            Enhancer enhancer = new Enhancer();
//            enhancer.setSuperclass(SpaceDivision.class);
//            enhancer.setUseCache(false);
//            enhancer.setCallback((MethodInterceptor) (obj, method, args1, proxy) ->
//                    proxy.invokeSuper(obj, args1)
//            );
//            System.out.println("hello");
//            enhancer.create();
        }
    }

    private static int length;
    /**
     * StackOverflow
     */
    private static void testSof() {
        length++;
        try {
            testSof();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(length);
        }
    }

    // -Xms2m -Xmx2m
    // -XX:+HeapDumpOnOutOfMemoryError
    // -XX:HeapDumpPath=/tmp/
    /**
     * java.lang.OutOfMemoryError: Java heap space
     * Dumping heap to java_pid14486.hprof ...
     * Heap dump file created [9012134 bytes in 0.051 secs]
     * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
     * 	...
     *
     * 	OOM错误:
     * 	    因为内存占用, jvm垃圾收集器无法为对象分配更多内存,
     */
    private static void testOom() {
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final ArrayList<SpaceDivision> objects = new ArrayList<>();
        while (true) {
            objects.add(new SpaceDivision());
//            System.gc();
        }
    }


}
