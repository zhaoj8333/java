package jvm.mm.ref;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * gc会直接回收弱引用
 */
public class WeakRefDemo {
    public static void main(String[] args) {
//        testGc();
        testQueue();

    }

    /**
     * 弱引用自身对象的释放要配合引用队列
     */
    private static void testQueue() {
        final ArrayList<WeakReference<byte[]>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final WeakReference<byte[]> ref = new WeakReference<>(new byte[1024 * 1024 * 4]);
            list.add(ref);
            for (WeakReference<byte[]> w : list) {
                System.out.print(w.get() + " " + list.size() + " | ");
            }
            System.out.println();
        }
        System.out.println(list.size());
    }

    private static void testGc() {
        WeakReference<FinalizeRef> wr = new WeakReference<>(new FinalizeRef());
        System.out.println("before gc: " + wr.get());
        System.gc();
        System.out.println("after gc: " + wr.get());
    }
}
