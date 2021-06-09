package jvm.mm.ref;

import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 虚引用:
 *     多用于垃圾回收后的事后清理工作, 安排后事
 *
 * 用途:
 *     管理直接内存(堆外内存, 不归于gc管理)
 *     JVM管理直接内存
 *
 *     直接内存用于NIO(非阻塞IO)
 *
 *     当堆中的对象被回收后, jvm必须回收这些对象指向的直接内存
 *
 * 直接内存回收方式:
 *     new PhantomReference<>(new Finalize(), queue)
 *     回收Finalize()对象时, 将对象放入queue,
 *     gc线程观测到这个队列中有一个对象被回收了, 就查看该对象是否指向了堆外内存
 *
 */
@SuppressWarnings("all")
public class PhantomRef {
    private static final List<Object> list = new LinkedList<>();
    private static final ReferenceQueue<FinalizeRef> queue = new ReferenceQueue<>();

    public static void main(String[] args) {
        testPhantomRef();
//        allo();
    }

    private static void allo() {
        WeakHashMap<String, String> hm = new WeakHashMap<>();
        final ByteBuffer b = ByteBuffer.allocate(1024); // 堆内分配
        final ByteBuffer buff = ByteBuffer.allocateDirect(1024);  // 堆外分配

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testPhantomRef() {
        // 虚引用
        // queue就是Finalize()对象被回收时要放入的队列
        // 垃圾回收线程观察该队列, 处理该队列查看该对象是否指向了直接内存, 有则清理掉
        final PhantomReference<FinalizeRef> pr = new PhantomReference<>(new FinalizeRef(), queue);
        System.out.println(pr.get());
        // null虚引用无法通过get获取到

        new Thread(() -> {
            while (true) {
                list.add(new byte[1024 * 1024]);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                System.out.println(pr.get());
            }
            // 内存满时会被回收
        }).start();

        // 模拟一个垃圾回收线程
        new Thread(() -> {
            while (true) {
                final Reference<? extends FinalizeRef> poll = queue.poll();
                if (poll != null) {
                    System.out.println("--- 虚引用对象被回收 --- " + poll);
                }
            }
        }).start();

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
