package jvm.mm.cp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * 常量池中的字符串仅仅是符号, 第一次用到时才会变为对象
 *
 * 利用串池机制, 来避免重复创建字符串对象
 *
 * 字符串拼接原理: StringBuilder(1.8)
 *
 * 字符串拼接的原理是编译期优化
 *
 * 1.8 使用String.intern方法, 主动将串池中还没有的字符串放入串池
 * 1.6 将字符串对象放入串池, 如果有则不会放入, 如果没有会把对象复制一份, 放入串池, 会把串池的对象返回
 *
 */
public class ConstantPool_StringTable {
    public static void main(String[] args) {
//        test1(args);
        testStringTableLocation();
//        testStringTableGc();
//        testStringTableSize();
//        testStringTableIntern();

    }

    /**
     * 考虑将字符串对象是否入池
     */
    private static void testStringTableIntern() {
        final ArrayList<String> list = new ArrayList<>();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++) {
            try {
                final FileInputStream fis = new FileInputStream("/usr/share/dict/american-english");
                final InputStreamReader isr = new InputStreamReader(fis);
                final BufferedReader br = new BufferedReader(isr);
                String line = null;
                final long s = System.nanoTime();
                while (true) {
                    line = br.readLine();
                    if (line == null) {
                        break;
                    }
                    final String intern = line.intern();// 放入池中
//                    list.add(line);
                    list.add(intern);
                }
                System.out.println("const: " + (System.nanoTime() - s) / 1000000);
                br.close();
                isr.close();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 调整StringTableSize = 桶个数
    // -Xmx10m
    // -XX:+PrintStringTableStatistics
    // -XX:+PrintGCDetails -verbose:gc
    //  -XX:StringTableSize=2000 该选项加上时, 执行时间会增加, 默认为 60013
    //      较大的StringTable可以有效减少哈希冲突, 加快执行速度
    private static void testStringTableSize() {
        try {
            final FileInputStream fis = new FileInputStream("/usr/share/dict/american-english");
            final InputStreamReader isr = new InputStreamReader(fis);
            final BufferedReader br = new BufferedReader(isr);
            String line = null;
            final long s = System.nanoTime();
            while (true) {
                line = br.readLine();
                if (line == null) {
                    break;
                }
                line.intern();  // 放入池中
            }
            System.out.println("const: " + (System.nanoTime() - s) / 1000000);
            br.close();
            isr.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -Xmx10m
    // -XX:+PrintStringTableStatistics
    // -XX:+PrintGCDetails -verbose:gc
    private static void testStringTableGc() {
        int i = 0;
        try {
            for (int j = 0; j < 100000; j++) {
                String.valueOf(i).intern();
                i++;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            System.out.println(i);
        }
        /**
         * 打印出的垃圾回收信息:
         * [GC (Allocation Failure) [PSYoungGen: 2048K->496K(2560K)] 2048K->528K(9728K), 0.0012603 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
         * [GC (Allocation Failure) [PSYoungGen: 2544K->496K(2560K)] 2576K->536K(9728K), 0.0022804 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
         * [GC (Allocation Failure) [PSYoungGen: 2544K->512K(2560K)] 2584K->568K(9728K), 0.0016279 secs] [Times: u
         *
         *
         * Heap
         *
         *  PSYoungGen      total 2560K, used 1660K [0x00000000ffd00000, 0x0000000100000000, 0x0000000100000000)
         *   eden space 2048K, 81% used [0x00000000ffd00000,0x00000000ffe9f388,0x00000000fff00000)
         *   from space 512K, 0% used [0x00000000fff80000,0x00000000fff80000,0x0000000100000000)
         *   to   space 512K, 0% used [0x00000000fff00000,0x00000000fff00000,0x00000000fff80000)
         *
         * ParOldGen       total 7168K, used 0K [0x00000000ff600000, 0x00000000ffd00000, 0x00000000ffd00000)
         *   object space 7168K, 0% used [0x00000000ff600000,0x00000000ff600000,0x00000000ffd00000)
         *
         * Metaspace       used 3277K, capacity 4500K, committed 4864K, reserved 1056768K
         *   class space    used 360K, capacity 388K, committed 512K, reserved 1048576K
         *
         * 符号表统计信息:
         * SymbolTable statistics:
         * Number of buckets       :     20011 =    160088 bytes, avg   8.000
         * Number of entries       :     12920 =    310080 bytes, avg  24.000
         * Number of literals      :     12920 =    498936 bytes, avg  38.617
         * Total footprint         :           =    969104 bytes
         * Average bucket size     :     0.646
         * Variance of bucket size :     0.647
         * Std. dev. of bucket size:     0.805
         * Maximum bucket size     :         6
         *
         * StringTable统计信息: hash表
         * StringTable statistics:
         * Number of buckets       :     60013 =    480104 bytes, avg   8.000
         * Number of entries       :       892 =     21408 bytes, avg  24.000
         * Number of literals      :       892 =     60040 bytes, avg  67.309
         * Total footprint         :           =    561552 bytes    总的内存占用
         * Average bucket size     :     0.015
         * Variance of bucket size :     0.015
         * Std. dev. of bucket size:     0.122
         * Maximum bucket size     :         2
         */

    }

    /**
     * 1.8以后, 由于StringTable位于堆中, 以下代码会触发 堆内存不足异常
     * -Xmx10m
     * -XX:-UseGCOverheadLimit
     *
     * java.lang.OutofMemoryError: GC Overhead limit exceeded
     *
     * 当gc使用了98%的时间, 但是仅有2%的内存被恢复,会抛出该异常, 防止应用长时间运行但是没有进度
     */
    private static void testStringTableLocation() {
        final ArrayList<String> list = new ArrayList<>();
        int i = 0;
        String s = "睡觉奥开发";
        try {
            while (true) {
                list.add(s.intern());
                s += s;
                i++;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            System.out.println(i);
        }

    }

    private static void test1(String[] args) {
        int x = args.length;
        System.out.println();
        System.out.println("1");
        System.out.println("2");
        System.out.println("3");
        System.out.println("4");
        System.out.println("5");
        System.out.println("6");
        System.out.println("7");
        System.out.println("8");
        System.out.println("9");
        System.out.println("0");
        System.out.println("1");
        System.out.println("2");
        System.out.println("3");
        System.out.println("4");

        System.out.println("length : " + x);
    }
}
