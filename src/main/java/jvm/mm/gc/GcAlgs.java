package jvm.mm.gc;

import java.util.ArrayList;

/**
 * 回收算法
 *
 *  1. 标记 + 清除: (Mark Sweep)
 *     垃圾回收时并不会对被清理的内存内存区域进行清零操作, 而是对被清理对象内存起始位置的地址加入到空闲地址列表,
 *     下次分配新对象取空闲地址列表查找有没有足够空间进行内存分配
 *     清除速度较快
 *     但是容易产生内存碎片, 内存碎片过多可能会导致后续使用无法找到连续内存而触发另一次gc
 *
 *     需要扫描所有对象, 堆越大, GC越慢
 *
 *  2. 标记 + 整理 (Mark Compact)
 *     涉及到对象拷贝, 对象引用位置修改的问题
 *     解决 标记 + 清除 内存不足的问题
 *     没有内存碎片, 比Mark-Sweep耗费更多的时间进行compact
 *
 *  3. 复制算法 (Copy)
 *     适用于存货对象数量较少的情况
 *     内存被分为一块较大的eden空间和2块较小的survivor空间,每次使用eden和其中一块survivor,回收时eden与survivor存活的对象一次性拷贝到另一块survivor区域
 *     hotspot默认eden与survivor大小比例为8:1
 *     现代商用jvm将此用于新生代的回收
 *     对象存活率高的时候, 效率会下降, 老年代不能使用该算法
 *
 *     特点:
 *         只扫描需要存活的对象, 效率更高
 *         不产生碎片
 *         需要浪费空间
 *         适合生命周期短的对象,每次回收总能回收大部分对象, 复制开销小
 *         据研究,98%的对象只会存活1个gc周期, 且不需要1:1划分工作区和复制区
 *
 * jvm垃圾回收都会根据实际情况 使用以上三种回收算法
 *
 *  4. 分代垃圾回收机制(Generational)
 *      根据对象生命周期执行不同的垃圾回收策略
 *      某些对象可能会长时间使用, 其会被放入老年代
 *      某些用完即止的放入新生代
 *
 *      老年代的垃圾回收较长一段时间发生一次
 *      新生代的垃圾回收发生较为频繁
 *
 *      Eden与2个survivor默认大小比例为 8:1:1,也就是10%的空间会被浪费, 可以根据GC log信息调整大小比例
 *
 *                      新生代
 *      伊甸园           幸存区From        幸存区 To
 *  +--------------+---------------+-------------------+
 *  |                                                  |
 *  |                                                  |
 *  +--------------+---------------+-------------------+
 *
 *                      老年代
 *  +--------------------------------------------------+
 *  |                                                  |
 *  |                                                  |
 *  +--------------------------------------------------+
 *
 *  对象首先分配在伊甸园区域
 *  第一次MinorGC: 新生代空间不足时, 会触发MinorGC, 使用复制算法, 将伊甸园和FROM存活的对象复制到幸存区TO, 此时被复制的对象寿命+1, FROM与TO交换位置
 *      MinorGC会引发stop the world, 暂停其他用户线程, 由垃圾回收线程执行gc
 *      垃圾回收牵涉到对象复制和移动, 所以必须暂停其他用户线程, gc结束后, 用户线程继续执行
 *      新生代垃圾回收时间较短
 *
 *  第二次MinorGC: 如果此时伊甸园发生了MinorGC, 仍然使用复制算法, 不过此时FROM区域的已有对象年龄会变成2
 *
 *  当age值超过一个阈值(默认15)时, 说明对象经常使用, 该对象会被晋升为老年代
 *
 *  如果老年代新生代几乎全满时, 会触发FullGC, 从新生代到老年代都进行清理, SWT暂停时间会更长
 *  老年代的回收使用标记+清除(或整理)
 *
 *  初始堆大小       -Xms
 *  堆最大大小       -Xmx -XX:MaxHeapSize=size
 *  新生代大小:      -Xmn -XX:NewSize=size + -XX:MaxNewSize=size
 *  幸存区比例(动态): -XX:InitialSurvivorRatio 和 -XX:+UseAdaptiveSizePolicy
 *  幸存区比例:      -XX:SurvivorRatio=ratio, 默认 8, 80%比例划分为Eden区域, 一份为from, 一份为to
 *  晋升阈值:        -XX:MaxTenuringThreshold=threshold
 *  晋升详情:        -XX:+PrintTenuringDistribution
 *  GC详情:         -XX:+PrintGCDetails -verbose:gc
 *  FullGC前MinorGC -XX:+ScavengeBeforeFullGC
 *
 *
 */
public class GcAlgs {

    private static final int _522KB = 512 * 1024;
    private static final int _1MB = 1024 * 1024;
    private static final int _6MB = 6 * 1024 * 1024;
    private static final int _7MB = 7 * 1024 * 1024;
    private static final int _8MB = 8 * 1024 * 1024;


    /**
     * 未运行任何对象时的内存
     *
     * Heap
     *  def new generation   total 9216K, used 1830K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
     *      新生代             总大小
     *
     *   eden space 8192K,  22% used [0x00000000fec00000, 0x00000000fedc9b00, 0x00000000ff400000)
     *      伊甸园     8M
     *      任何java程序运行时,都要提前加载类到伊甸园
     *
     *   from space 1024K,   0% used [0x00000000ff400000, 0x00000000ff400000, 0x00000000ff500000)
     *      from      1M
     *
     *   to   space 1024K,   0% used [0x00000000ff500000, 0x00000000ff500000, 0x00000000ff600000)
     *      to        1M   幸存区空间的1m不能用
     *
     *  tenured generation   total 10240K, used 0K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
     *      老年代
     *    the space 10240K,   0% used [0x00000000ff600000, 0x00000000ff600000, 0x00000000ff600200, 0x0000000100000000)
     *
     *  Metaspace       used 3262K, capacity 4496K, committed 4864K, reserved 1056768K
     *      元空间
     *
     *   class space    used 358K, capacity 388K, committed 512K, reserved 1048576K
     *      类空间
     */

    // Xms20M -Xmx20M -Xmn10M -XX:+UseSerialGC -XX:+PrintGCDetails -verbose:gc
    public static void main(String[] args) {
//        test1();
        test2();

    }

    private static void test2() {
        // 大对象直接晋升到老年代
        final ArrayList<byte[]> list = new ArrayList<>();
        list.add(new byte[_8MB]);
        // 新生代容纳不下, 会直接进入老年代
        /**
         * Heap
         *  def new generation   total 9216K, used 1830K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
         *   eden space 8192K,  22% used [0x00000000fec00000, 0x00000000fedc9b00, 0x00000000ff400000)
         *   from space 1024K,   0% used [0x00000000ff400000, 0x00000000ff400000, 0x00000000ff500000)
         *   to   space 1024K,   0% used [0x00000000ff500000, 0x00000000ff500000, 0x00000000ff600000)
         *  tenured generation   total 10240K, used 8192K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
         *    the space 10240K,  80% used [0x00000000ff600000, 0x00000000ffe00010, 0x00000000ffe00200, 0x0000000100000000)
         *  Metaspace       used 3277K, capacity 4496K, committed 4864K, reserved 1056768K
         *   class space    used 360K, capacity 388K, committed 512K, reserved 1048576K
         */

        list.add(new byte[_8MB]);
        // OOM
        // 某个线程发生OOM, 并不会导致其他线程OOM
    }

    private static void test1() {
        // gc root会引用list, 不会被垃圾回收
        final ArrayList<byte[]> list = new ArrayList<>();
        list.add(new byte[_7MB]);
        /**
         * [GC (Allocation Failure) [DefNew: 1666K->420K(9216K), 0.0011056 secs] 1666K->420K(19456K), 0.0011240 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
         *                              回收前的 -> 回收后的        回收时间         整个堆的内存占用
         *
         * eden space 8192K,  92% used [0x00000000fec00000, 0x00000000ff366830, 0x00000000ff400000)
         *   from space 1024K,  38% used [0x00000000ff500000, 0x00000000ff562aa0, 0x00000000ff600000)
         */
        list.add(new byte[_522KB]);
        /**
         *  def new generation   total 9216K, used 8346K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
         *   eden space 8192K,  96% used [0x00000000fec00000, 0x00000000ff3bd8d0, 0x00000000ff400000)
         *   from space 1024K,  41% used [0x00000000ff500000, 0x00000000ff569188, 0x00000000ff600000)
         */
        list.add(new byte[_522KB]);
        /**
         * [GC (Allocation Failure) [DefNew: 1666K->420K(9216K), 0.0013007 secs] 1666K->420K(19456K), 0.0013252 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
         * [GC (Allocation Failure) [DefNew: 8264K->915K(9216K), 0.0047489 secs] 8264K->8083K(19456K), 0.0047842 secs] [Times: user=0.00 sys=0.01, real=0.00 secs]
         *
         *  def new generation   total 9216K, used 1755K [0x00000000fec00000, 0x00000000ff600000, 0x00000000ff600000)
         *   eden space 8192K,  10% used [0x00000000fec00000, 0x00000000fecd2050, 0x00000000ff400000)
         *   from space 1024K,  89% used [0x00000000ff400000, 0x00000000ff4e4ef8, 0x00000000ff500000)
         *     to   space 1024K,   0% used [0x00000000ff500000, 0x00000000ff500000, 0x00000000ff600000)
         *  tenured generation   total 10240K, used 7168K [0x00000000ff600000, 0x0000000100000000, 0x0000000100000000)
         *    the space 10240K,  70% used [0x00000000ff600000, 0x00000000ffd00010, 0x00000000ffd00200, 0x0000000100000000)
         *  Metaspace       used 3276K, capacity 4496K, committed 4864K, reserved 1056768K
         *   class space    used 360K, capacity 388K, committed 512K, reserved 1048576K
         */}
}
