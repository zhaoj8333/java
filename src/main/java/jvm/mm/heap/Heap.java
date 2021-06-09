package jvm.mm.heap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Heap {
    public static void main(String[] args) {
//        test2();
//        testPretenureThresholdSize();
//        detectBigObjects();
//        testTenureThreshold();
        testMaxTenuringThresholdToOld();
    }

    /**
     * MaxTurningThreshold : 在新生代中对象存活次数(经过minor gc)后仍然存活, 就会晋升到老年代
     * TargetSurvivorRatio:  一个计算期望存活大小Desired survivor size的参数, 默认50
     *
     * Desired survivor size = (survivor_capacity * targetSurvivorRatio) / 100 * sizeof(a pointer) : survivor_capacity
     * 如果所有age的survivor space对象的大小如果超过desired survivor size, 则重新计算threshold, 以age和maxTenuringThreshold
     * 的最小值为准, 否则以MaxTenuringThreshold为准
     *
     * jvm会将每个对象的年龄信息, 各个年龄段对象的总大小记录在"age table"表中. 基于"age table", survivor区大小, survivor目标使用率
     * (-XX:TargetSurvivorRatio), 晋升年龄阈值(-XX:MaxTenuringThreshold),jvm会动态计算tenuring threshold的值,
     * 一旦对象打到tenuring threshold就会晋升到老年代
     *
     * 为何要动态计算:
     *     假设很多未达到TenuringThreshold对象依旧停留在survivor区域, 这样不利于新对象从eden从eden晋升到survivor区
     *     当survivor区域使用率达到一定值时重新调整TenuringThreshold值, 让对象尽可能早的晋升
     */
    // -verbose:gc -Xmx200M -Xmn50M
    // -XX:TargetSurvivorRatio=60
    //      百分比, 比如Survivor空间为10m,10m*0.6 = 6m, 当survivor存活对象超过6m, 就会重新计算晋升阈值
    //      而不是所设置的MaxTenuringThreshold
    // -XX:+PrintGCDetails
    // -XX:+PrintCommandLineFlags
    // -XX:SurvivorRatio=8
    // -XX:MaxTenuringThreshold=3
    // -XX:+DoEscapeAnalysis
    // -XX:+UseTLAB
    // -XX:+EliminateAllocations
    // -XX:+PrintGCDateStamps
    // -xx:+UseConcMarkSweepGC      老年代使用cms
    // -XX:+UseParNewGC             新生代使用parnew
    // -XX:MaxTenuringThreshold=3   对象在新生代存活的最大年龄阈值 = 3, 第一次都以该数为准
    private static void testMaxTenuringThresholdToOld() {
        byte[] bytes1 = new byte[512 * 1024];
        byte[] bytes2 = new byte[512 * 1024];
        try {
            allocateBytes();
            TimeUnit.SECONDS.sleep(1);
            System.out.println("1111");
            System.out.println("---------------------------------------------------------");
            allocateBytes();
            TimeUnit.SECONDS.sleep(1);
            System.out.println("2222");
            System.out.println("---------------------------------------------------------");
            allocateBytes();
            TimeUnit.SECONDS.sleep(1);
            System.out.println("3333");
            System.out.println("---------------------------------------------------------");
            allocateBytes();
            TimeUnit.SECONDS.sleep(1);
            System.out.println("---------------------------------------------------------");
            System.out.println();
        } catch (Exception e) {

        }

        final byte[] bytes3 = new byte[1024 * 1024];
        final byte[] bytes4 = new byte[1024 * 1024];
        final byte[] bytes5 = new byte[1024 * 1024];
        /*
        2020-10-10T09:56:54.060+0800: [GC (Allocation Failure) 2020-10-10T09:56:54.060+0800: [ParNew
            Desired survivor size 3145728 bytes, new threshold 1 (max 3)
            - age   1:    3145776 bytes,    3145776 total
            - age   2:         48 bytes,    3145824 total
            - age   3:     335352 bytes,    3481176 total
            此时新生代的所有对象会一次性晋升到老年代
            新生代为空, 下一次有新对象涌入时, 所有参数被重置, threshold也被置为MaxTenuringThreshold
            : 41491K->3487K(46080K), 0.0013755 secs] 42940K->4942K(199680K), 0.0014857 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
         */
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("---------------------------------------------------------");
        System.out.println();
        allocateBytes();
        System.out.println("---------------------------------------------------------");
        allocateBytes();
        System.out.println();
        System.out.println("done");
        System.out.println("---------------------------------------------------------");
    }

    private static void allocateBytes() {
        for (int i = 0; i < 40; i++) {
            final byte[] bytes = new byte[1024 * 1024];
        }
    }

    /**
     * 新生代到老年代的晋升:
     *     减少新生代在gc时复制算法的开销
     *
     * -verbose:gc -Xms20m -Xmx20m -Xmn10m
     * -XX:+PrintGCDetails
     * -XX:+PrintCommandLineFlags
     * -XX:+SurvivorRatio=8
     * -XX:MaxTenuringThreshold=5 设置的是年龄阈值，默认15（对象被复制的次数）, G1中也是默认15
     * -XX:+PrintTenuringDistribution 打印各个年龄段对象的基本分布
     *
     * 经历多次gc后, 存货对象在form与to之间来回存放, 二这前提是这两个空间有足够的大小存放这些数据
     * gc算法中, 会计算每个对象年龄的大小,如果达到某个年龄但是发现剩下的对象空间总大小已经大于survivor空间的50%,
     * 则需要调整阈值, 不能继续等到默认的15次gc后才完成晋升, 因为这样会导致survivor空间不足
     * 所以需要调整阈值, 让这些存活对象尽快完成晋升
     * 否则会造成新创建的对象直接进入老年代
     */
    /**
[GC (Allocation Failure)

Desired survivor size 1048576 bytes, new threshold 5 (max 5) // -XX:MaxTenuringThreshold
                      8:1:1

[PSYoungGen: 7810K->512K(9216K)] 7810K->6664K(19456K), 0.0035007 secs] [Times: user=0.02 sys=0.01, real=0.01 secs]
[Full GC (Ergonomics) [PSYoungGen: 512K->0K(9216K)] [ParOldGen: 6152K->6564K(10240K)] 6664K->6564K(19456K), [Metaspace: 3269K->3269K(1056768K)], 0.0073410 secs] [Times: user=0.02 sys=0.00, real=0.01 secs]
Heap
 PSYoungGen      total 9216K, used 2130K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
  eden space 8192K, 26% used [0x00000000ff600000,0x00000000ff814930,0x00000000ffe00000)
  from space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
  to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
 ParOldGen       total 10240K, used 6564K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
  object space 10240K, 64% used [0x00000000fec00000,0x00000000ff269010,0x00000000ff600000)
 Metaspace       used 3276K, capacity 4500K, committed 4864K, reserved 1056768K
  class space    used 360K, capacity 388K, committed 512K, reserved 1048576K

     */
    private static void testTenureThreshold() {
        int size = 1024 * 1024;
        byte[] array  = new byte[2 * size];
        byte[] array1  = new byte[2 * size];
        byte[] array2  = new byte[2 * size];
        byte[] array3  = new byte[2 * size];
    }

    /**
     * jvisualvm
     * Heap dump
     */
    private static void detectBigObjects() {
        System.out.println("begin ....");
        final ArrayList<Object> list = new ArrayList<>();
        StringBuilder a = new StringBuilder("答案及开发");
        for (int i = 0; i < 20; i++) {
            list.add(a.toString() + i);
            a.append(a);
        }
        try {
            Thread.sleep(10000000000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * -verbose:gc
     * -Xms20m
     * -Xmx20m
     * -Xmn10m 新生代大小
     * -XX:+PrintGCDetails
     * -XX:SurvivorRatio=8
     *
     * [GC (Allocation Failure)
     *     [PSYoungGen: 8192K->592K(9216K)]         新生代大小
     *     8192K->6744K(19456K), 0.0054059 secs]    9M    gc前总大小->(总的堆可用大小) 一部分年轻代对象经过gc后可能进入老年代, 老年代反而增加    gc时间
     *     [Times: user=0.01 sys=0.00, real=0.00 secs]
     *
     * [Full GC (Ergonomics) :
     *     [PSYoungGen: 592K->0K(9216K)]
     *     [ParOldGen: 6152K->6647K(10240K)]    gc后老年代反而变多
     *     6744K->6647K(19456K),
     *     [Metaspace: 3403K->3403K(1056768K)], 0.0079782 secs]
     *     [Times: user=0.03 sys=0.00, real=0.01 secs]
     *
     * /opt/jdk1.8.0_181/bin/java
     *     -verbose:gc -Xms20M -Xmx20M -Xmn10M
     *     -XX:+PrintGCDetails
     *     -XX:SurvivorRatio=8
     *
     * Heap
     *  PSYoungGen      total 9216K, used 888K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
     *      PSYoungGen: Parallel Scavenge(新生代垃圾收集器)
     *
     *   eden space 8192K, 10% used [0x00000000ff600000,0x00000000ff6de298,0x00000000ffe00000)
     *   from space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
     *   to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
     *
     *  ParOldGen       total 10240K, used 6647K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
     *      ParOldGen: Parallel Old(老年代垃圾收集器)
     *
     *   object space 10240K, 64% used [0x00000000fec00000,0x00000000ff27de78,0x00000000ff600000)
     *
     *  Metaspace       used 3771K, capacity 4540K, committed 4864K, reserved 1056768K
     *   class space    used 418K, capacity 428K, committed 512K, reserved 1048576K
     */
    private static void test2() {
        System.out.println();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int s = 3;
        int size = 1024 * 1024;
        // 对于数组来说,不是由ClassLoader创建, 而是由java运行时创建
        // 数组的ClassLoader就是加载该数组元素的ClassLoader,
        // 如果元素类型是基本类型, 则数组就没有ClassLoader
        /*
        byte[] array  = new byte[2 * size];
        byte[] array1 = new byte[2 * size];
        byte[] array2 = new byte[3 * size];
        byte[] array3 = new byte[3 * size];
        占用量大后反而没出现Full GC:
            如果前三个能在Eden空间中分配下, 但是明显第四个无法在Eden中分配,此时array3直接进入老年代
         */


//        byte[] array4 = new byte[5 * size];

        // 当新生代空间容纳不下心对象时, 再次新创建的对象将会直接进入老年代
//        byte[] array4 = new byte[s * size];

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        array = null;
//        System.gc();

        System.out.println();
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * -XX:PretenureThresholdSize=4194304(字节): 新创建的对象大小超过了该值,该对象会直接在老年代分配
     * 默认为0, 意味着任何对象都在新生代分配内存
     *
     * byte[] array  = new byte[8 * size];
     * -verbose:gc -Xms20M -Xmx20M -Xmn10M
     * -XX:+PrintGCDetails
     * -XX:+PrintCommandLineFlags
     * -XX:SurvivorRatio=8
     * -XX:PretenureSizeThreshold=4194304
     * PSYoungGen      total 9216K, used 1830K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
     * eden space 8192K, 22% used [0x00000000ff600000,0x00000000ff7c9af8,0x00000000ffe00000)
     * from space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
     * to   space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
     * ParOldGen       total 10240K, used 8192K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
     * // 对象不会一部分拆分到新生代, 一部分到老年代
     * //
     *
     * ----------------------------------------
     *
     * byte[] array  = new byte[10 * size];
     * [GC (Allocation Failure) [PSYoungGen: 1666K->480K(9216K)] 1666K->488K(19456K), 0.0017756 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
     * [GC (Allocation Failure) [PSYoungGen: 480K->528K(9216K)] 488K->536K(19456K), 0.0038657 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
     * [Full GC (Allocation Failure) [PSYoungGen: 528K->0K(9216K)] [ParOldGen: 8K->419K(10240K)] 536K->419K(19456K), [Metaspace: 3271K->3271K(1056768K)], 0.0072017 secs] [Times: user=0.03 sys=0.00, real=0.01 secs]
     * [GC (Allocation Failure) [PSYoungGen: 0K->0K(9216K)] 419K->419K(19456K), 0.0027910 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
     * [Full GC (Allocation Failure) [PSYoungGen: 0K->0K(9216K)] [ParOldGen: 419K->402K(10240K)] 419K->402K(19456K), [Metaspace: 3271K->3271K(1056768K)], 0.0066542 secs] [Times: user=0.02 sys=0.00, real=0.01 secs]
     *
     * Heap
     *  PSYoungGen      total 9216K, used 246K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
     *   eden space 8192K, 3% used [0x00000000ff600000,0x00000000ff63d890,0x00000000ffe00000)
     *   from space 1024K, 0% used [0x00000000ffe00000,0x00000000ffe00000,0x00000000fff00000)
     *   to   space 1024K, 0% used [0x00000000fff00000,0x00000000fff00000,0x0000000100000000)
     *  ParOldGen       total 10240K, used 402K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
     *   object space 10240K, 3% used [0x00000000fec00000,0x00000000fec64870,0x00000000ff600000)
     *  Metaspace       used 3302K, capacity 4500K, committed 4864K, reserved 1056768K
     *   class space    used 363K, capacity 388K, committed 512K, reserved 1048576K
     *
     * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
     * 	at jvm.mm.heap.Heap.testPretenureThresholdSize(Heap.java:163)
     * 	at jvm.mm.heap.Heap.main(Heap.java:8)
     *
     * ----------------------------------------
     * byte[] array  = new byte[5 * size];
     * System.in.read();
     *
     * -XX:+UseSerialGC
     *
     * jvisualvm
     *     Monitor -> Perform GC(为Full GC)
     */

    /**
     * 应该尽量避免Full GC, Full GC会导致年轻代, 老年代, 元空间都会进行垃圾回收
     */
    private static void testPretenureThresholdSize() {
        System.out.println();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int size = 1024 * 1024;
        byte[] array  = new byte[5 * size];
        /*
        tenured generation   total 10240K, used 5120K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
        the space 10240K,  50% used [0x00000000ff600000, 0x00000000ffb00010, 0x00000000ffb00200, 0x0000000100000000)
         */
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
