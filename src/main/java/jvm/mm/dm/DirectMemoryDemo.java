package jvm.mm.dm;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

/**
 * 直接内存:
 *     ByteBuffer,
 *     常见于NIO操作, 用于数据缓冲区
 *     分配回收成本较高, 但是读写性能高
 *     不受JVM内存回收管理
 *
 */
public class DirectMemoryDemo {
    static final String from  = "/home/allen/sdb3/天文/[BBC.太阳之谜].BBC.The.Sun.2006.mkv.pdf";
    static final String ioto  = "/home/allen/sdb1/io.avi";
    static final String nioto = "/home/allen/sdb1/nio.avi";
    static final int _1mb     = 1024 * 1024;
    static final int _100mb   = 1024 * 1024 * 100;
    static final int _1Gb     = 1024 * 1024 * 1024;

    public static void main(String[] args) {
        /*
         * java本身不具备文件读写能力, 必须调用os的文件读写函数
         */
//        io();
//        directBuffer();
//        dmOom();
//        dmGc();
//        dmByUnsafe();
    }

    /**
     * 直接内存与java内存不同, 必须手动释放
     */
//    private static void dmByUnsafe() {
//        final Unsafe unsafe = getUnsafe();
//        assert unsafe != null;
//        // 分配内存
//        System.out.println("分配内存 ...");
//        final long base = unsafe.allocateMemory(_1Gb);
//        unsafe.setMemory(base, _1Gb, (byte) 0);
//        try {
//            System.in.read();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        // 释放
//        System.out.println("释放内存 ...");
//        unsafe.freeMemory(base);
//        try {
//            System.in.read();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public static Unsafe getUnsafe() {
//        final Field f;
//        try {
//            f = Unsafe.class.getDeclaredField("theUnsafe");
//            f.setAccessible(true);
//            final Unsafe o = (Unsafe) f.get(null);
//            return o;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    // 直接内存的gc
    // bytebuffer的内存分配也是使用Unsafe使用的
    // 当bytebuffer被回收时, 会触发虚引用对象中的Cleaner.clean方法
    // Cleaner.clean在后台的一个"ReferenceHandler"的线程(专门检测虚引用对象)进行执行
    // 当虚拟引用对象关联的实际对象被回收时,会调用Cleaner.clean,执行任务对象

    // -XX:+DisableExplicitGC 关闭显式的垃圾回收(让自定义代码中的System.gc()失效)
    //     关闭显式直接内存释放后, 会导致内存迟迟未得到释放,
    //     可以直接使用Unsafe的方式来手动管理内存

    // DirectByteBuffer:
    //     Unsafe.allocateMemory(int size)
    //     Cleaner.create(this, new Deallocator(base, size, cap)), Cleaner继承自幻引用

    /**
     * 引用队列: java.lang.ref.ReferenceQueue
     *     用于保存被回收后对象的引用, 当联合使用软引用,弱引用和引用队列时, 系统在回收被引用的对象后, 将把它所回收的对象对应的引用
     *     添加到关联的引用队列.
     *
     *     虚引用在被释放前,将把它对应的虚引用添加到它关联的引用队列中,使得可以在对象被回收前采取行动
     */
//    private static void dmGc() {
//        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(_1Gb);
//        System.out.println("分配完毕...");
//        try {
//            final int read = System.in.read();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("开始释放..");
//        final long base = byteBuffer.getLong();
//        byteBuffer = null;
//        System.gc();    // 显式的gc, Full GC, 但是还是可以通过Unsafe.freeMemory手动管理, 是一种Full GC
//        final Unsafe unsafe = getUnsafe();
//        unsafe.freeMemory(base);
//        // 此处会将1gb的内存回收掉
//        try {
//            final int read = System.in.read();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    // java.lang.OutOfMemoryError: Direct buffer memory
    // 直接内存溢出
    private static void dmOom() {
        final ArrayList<ByteBuffer> list = new ArrayList<>();
        int i = 0;
        try {
            while (true) {
                final ByteBuffer byteBuffer = ByteBuffer.allocateDirect(_100mb);
                list.add(byteBuffer);
                i++;
            }
        } finally {
            System.out.println(i);
        }
    }

    // 传统io方式
    private static void io() {
        final long s = System.nanoTime();
        try {
            final FileInputStream fis = new FileInputStream(from);
            final FileOutputStream fos = new FileOutputStream(ioto);
            final byte[] buff = new byte[_1mb];
            while (true) {
                final int len = fis.read(buff);
                if (len == -1) {
                    break;
                }
                fos.write(buff, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        final long e = System.nanoTime();
        System.out.println("io time: " + (e - s) / 100_0000);

    }

    // nio方式
    private static void directBuffer() {
        final long s = System.nanoTime();
        try {
            final FileChannel fromC = new FileInputStream(from).getChannel();
            final FileChannel toC   = new FileOutputStream(DirectMemoryDemo.nioto).getChannel();
            final ByteBuffer bb     = ByteBuffer.allocateDirect(_1mb);
            while (true) {
                final int len = fromC.read(bb);
                if (len == -1) {
                    break;
                }
                bb.flip();
                toC.write(bb);
                bb.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        final long e = System.nanoTime();
        System.out.println("nio time: " + (e -s) / 100_0000);
    }
}
