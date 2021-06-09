package lang.dt;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * Java引用：
 *     {@link java.lang.ref.Reference}
 *
 * @TODO
 */
public class References {
    public static void main(String[] args) {


    }

    /**
     * 回收机制跟 {@link WeakReference}差不多，但是被回收之前，会被放入 {@link ReferenceQueue}中，
     * 大多用于引用销毁前的处理工作
     */
    private static void phantomRef() {
        PhantomReference<String> phantomRef = new PhantomReference<String>(
                "Phantom Reference",
                new ReferenceQueue<>()
        );
    }

    /**
     * {@link WeakReference}：
     *     只要被JVM发现，就会被回收
     *
     *     {@link java.util.WeakHashMap}
     */
    private static void weakRef() {
        WeakReference<String> weakRef = new WeakReference<>("Weak Reference");
    }

    /**
     * 软引用：
     *      在内存不足时，会被回收
     *
     *      可广泛引用于 缓存
     */
    private static void softRef() {
        /**
         * {@link SoftReference}对象本身是强引用，只是其本身引用的对象是软引用(其 泛型 )
         */
        SoftReference<String> softRef = new SoftReference<>("Soft reference");
    }

    /**
     * 强引用：
     *     在发生 {@link OutOfMemoryError}时也不会被会回收
     */
    private static void strongRef() {
        String str = new String("string");

    }
}
