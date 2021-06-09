package jvm.mm.ref;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;

/**
 * heap内存不够时, 软引用会被回收
 */
public class SoftRefDemo {
    public static void main(String[] args) {
//        testSrefGc();
        testRefQueue();
    }

    private static void testRefQueue() {
        final ArrayList<SoftReference<byte[]>> list = new ArrayList<>();
        // 引用队列
        ReferenceQueue<byte[]> queue = new ReferenceQueue<>();
        for (int i = 0; i < 5; i++) {
            // 软引用所关联的对象被回收时, 会被加入到q中
            final SoftReference<byte[]> ref = new SoftReference<>(new byte[1024 * 1024 * 4], queue);
            System.out.print(ref.get() + "    ");
            list.add(ref);
            System.out.println(list.size());
        }
        Reference<? extends byte[]> poll = queue.poll();
        // 移除无用的软引用
        while (poll != null) {
            list.remove(poll);
            poll = queue.poll();
        }

        System.out.println("=======================");
        for (SoftReference<byte[]> softReference : list) {
            System.out.println(softReference.get());
        }
    }

    /**
     * 软引用适合做缓存
     */
    private static void testSrefGc() {
        // 10 M
        final SoftReference<byte[]> m = new SoftReference<>(new byte[1024 * 1024 * 10]);
        // m指向 SoftReference, SoftReference指向 10M的内存空间
//        m = null;
        System.out.println(m.get());
        System.gc();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(m.get());
        // 软引用所指向的对象没有被回收

        final byte[] b = new byte[1024 * 1024 * 15];
        // 强引用
        // m本身所指向的SoftReference本身不会被回收
        // 此处再次分配了一部分内存, 当堆 放不下时 系统执行垃圾回收, 先回收一次, 如果不会, 会把软引用干掉

        System.out.println(m.get());
    }
}
