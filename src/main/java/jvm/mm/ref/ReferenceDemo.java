package jvm.mm.ref;

import java.lang.ref.*;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("all")
public class ReferenceDemo {

    public static void main(String[] args) {
//        normalRef();
//        softRef();
//        weakRef();
//        phantomRef();
        refQueue();
    }

    /**
     * 引用队列:
     *     当gc准备回收对象时,如果发现他还仅有软引用或弱引用或虚引用指向他,就会在该对象被回收之前, 把这个引用加入到引用队列
     *     如果一个软引用本身在引用队列中,说明该引用对象所指向的对象被回收了
     */
    private static void refQueue() {
        String str = new String("阿斯顿就快了发觉");
        final ReferenceQueue<Object> q = new ReferenceQueue<>();
        final PhantomReference<String> pr = new PhantomReference<>(str, q);
        str = null;
        System.out.println(pr.get());
        //
        System.gc();
        System.runFinalization();
        // 垃圾回收后, 虚引用将被放入引用队列中
        System.out.println();
        System.out.println(q.poll());
        System.out.println(pr);
        //
        System.out.println(q.poll() == pr);
    }

    /**
     * 幻引用 用于管理堆外内存
     *
     * os会将磁盘数据拷贝到os自己管理的内存中,然后再拷贝到jvm内存中,效率较低
     * 因此jvm提供了直接内存管理,直接访问os管理的内存
     */
    private static void phantomRef() {
        final LinkedList<Object> list      = new LinkedList<>();
        final ReferenceQueue<Object> queue = new ReferenceQueue<>();
        // 一个幻引用指向的对象被回收时, 会把一个信息填到queue中
        final PhantomReference<M> p = new PhantomReference<>(new M(), queue);

        new Thread(() -> {
            while (true) {
                list.add(new byte[1024 * 1024]);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // list容量增大, 幻引用会被回收
                System.out.println(p.get());
            }
        }).start();

        new Thread(() -> {
            while (true) {
                Reference<? extends M> poll = (Reference<? extends M>) queue.poll();
                if (poll != null) {
                    System.out.println("幻引用被回收 --- " + poll);
                }
            }
        }).start();
    }

    /**
     * 弱引用一遇到gc都会被回收
     *
     * 弱引用是为了解决某些地方的内存泄漏问题
     */
    private static void weakRef() {
        final WeakReference<M> w = new WeakReference<>(new M());
        System.out.println(w.get());
        System.gc();
        System.out.println(w.get());

        /**
         * 此处, 当main线程结束, tl为null, 但是tl中set注入了数据,内存中:
         *     假设是一个强引用, 线程不退出时, ThreadLocal永远也不会被回收, 及时tl = null, 但是key的引用依然指向threadLocal对象, 所以会有内存泄露
         *     如果是弱引用,只要gc, 就会被回收
         */
        ThreadLocal<Object> tl = new ThreadLocal<>();
        tl.set(new M());
        // 不适用tl时, 务必要remove掉, 否则会造成内存泄漏
        // threadLocalMap中value会导致内存泄露, 也就是说即使key使用了弱引用,也可能导致内存泄漏

        /**
         * 内存泄漏的条件和内存泄露的避免:
         *     当前线程继续执行, 该方式很难控制, 尤其是使用了线程池
         *         由于ThreadLocalMap是Thread的一个属性,被当前线程所引用,所以其生命周期与线程一样,使用完ThreadLocal后,如果线程结束,ThreadLocalMap也被回收,
         *     没有手动删除Entry
         *         使用完ThreadLocal, 调用其remove删除对应的Entry
         *
         * 使用完ThreadLocal及时调用remove, 无论强引用还是弱引用, 都不会有问题, 那为何还要使用弱引用?
         *     在ThreadLocalMap中,会对key为null做出判断,如果为null,会对value置为null, 这意味着使用完THreadLocal, 当前线程仍然运行,及时忘记调用remove方法,弱引用比强引用多一层保障
         *     弱引用threadLocal被回收,对应的value在下一次threadLOcalMap调用set,get,remove中的方法时会被清除,避免内存泄漏
         */
        tl.remove();
    }

    /**
     * m本身所指向的引用为正常引用
     * 而SoftReference又指向了一个软引用
     *
     * 一旦空间不足时, 软引用空间会被回收
     * 但是正常引用即使空间不足,也不会被回收
     *
     * -Xmx20M
     *
     * 软引用很适合用缓存
     */
    private static void softRef() {
        SoftReference<byte[]> m = new SoftReference<>(new byte[1024 * 1024 * 10]);
//        m = null;
        System.out.println(m.get());
        System.gc();

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(m.get());

        byte[] bytes = new byte[1024 * 1024 * 15];
        System.out.println(m.get());
    }

    private static void normalRef() {
        Object o = new M();
        o = null;
        System.gc();    // 此处会执行M的finalize, 因为gc线程是另外的线程, 所以如果main线程率先结束,不一定能能看到finalize的执行
    }

}
