package jvm.mm.gc;

/**
 * 垃圾回收器分类:
 *
 * 1. 串行的垃圾回收器
 *     * 单线程
 *     * 堆内存较小, 适合个人电脑
 *
 * 2. 吞吐量优先
 *     * 多线程
 *     * 堆内存较大, 多核cpu
 *     * 尽可能让单位时间内STW的时间最短, 垃圾回收时间占程序运行时间很低
 *
 * 3. 响应时间优先
 *     * 多线程
 *     * 堆内存较大, 多核cpu
 *     * 尽可能让单次GC的STW尽可能的短
 */
public class Gcer {
    public static void main(String[] args) {
        testCms();
    }

    /**
     * SerialGC
     *     新生代内存不足发生的垃圾收集 -- minor gc
     *     老年代内存不足发生的垃圾收集 -- full gc
     * ParallelGC
     *     新生代内存不足发生的垃圾收集 -- minor gc
     *     老年代内存不足发生的垃圾收集 -- full gc
     *
     * CMS
     *     新生代内存不足发生的垃圾收集 -- minor gc
     *     老年代内存不足
     *
     * G1
     *     新生代内存不足发生的垃圾收集 -- minor gc
     *     老年代内存不足
     *     当垃圾回收的速度跟不上垃圾产生的速度时,并发收集就会失败,退化为串行收集, 进行full gc
     */
    private static void testGc() { }


    /**
     * CMS回收器(并发标记清除), 工作在老年代
     * -XX:+UseConcMarkSweepGC
     * 响应时间优先:
     *     用户线程与回收线程并发执行, 垃圾回收时用户现场不用暂停, 减少STW时间
     *
     *     减少了响应时间, 但是对整体的吞吐量有影响
     *     并且如果线程产生了新的垃圾,必须在下一次清理
     *
     * 执行流程:
     *     初始标记: 执行STW, 初始标记非常快,只会标记跟对象
     *     并发标记: 同时用户线程继续执行, 用户线程与gc线程同时执行
     *     重新标记: 会执行STW, 标记完了, 用户线程重新运行
     *     并发清理: 用户线程与gc线程同时运行
     *
     * -XX:+UseParNewGC
     *     与CMS配合的新生代基于复制的收集器
     *     ParNew就是Serial收集器的多线程版本, 除使用多线程外, 其余可控算法,收集算法, 停止工作线程,对象分配原则,回收策略与serial完全一致
     *     除了多线程外,没有任何创新之处,
     *     但是他却是server模式下的新生代首选收集器
     *
     * SerialOld
     *     如果CMS并发失败, 则退化到串行的垃圾回收器
     *
     * // 并行线程数
     *      -XX:ParallelGCThreads=n~
     * // 并发的gc线程数: 一般为并行线程数 / 4
     *      -XX:ConcGCThreads=threads
     * // 一个cpu内核执行垃圾回收, CPU占用率相比ParallelGC小, 仅有一个cpu执行垃圾回收,所以系统吞吐量会下降
     *
     * 浮动垃圾:
     * // CMS执行并发清理时, 其他用户线程由于继续运行, 会产生新的垃圾, 所以必须等到下一次垃圾回收时清理这些垃圾(浮动垃圾)
     * // 所以CMS不能像其他垃圾回收器一样, 等到内存到某个阈值时再作垃圾回收
     * // percent表示老年代内存占用达到percent时,才进行垃圾回收, 预留一部分空间给浮动垃圾, 这部分空间占据堆内存的比例
     * -XX:CMSInitiatingOccupancyFraction=percent
     *
     * 跨年龄段引用:
     * // 有可能新生代对象会引用老年代对象, 重新标记时必须重新扫描整个堆,
     * // 如果通过新生代找老年代的话, 会做很多无用查找(即使找到了, 新生代的很多对象也会被回收)
     * // 在重新标记之前先对新生代做一次垃圾回收, 之后扫描对象时减少重新标记的数量
     * -XX:+CMSScavengeBeforeRemark 开启/关闭
     *
     * 内存碎片比较多时, 会造成将来分配对象时失败, 会造成并发失败, CMS不能正常工作,
     * 此时CMS退化为SerialOld(做一次单线程的串行的垃圾回收), 此时cpu占用会立即飙升
     */
    private static void testCms() {

    }

    /**
     * Parallel Scavenge
     *     采用复制算法, 又是并行的收集器, 目标是达到一个可控制的吞吐量
     *     吞吐量 = 用户线程工作时间 / (用户线程工作时间 + 垃圾回收时间)

     * -XX:+UseParallelGC -XX:+UseParallelOldGC
     * -XX:+UseAdaptiveSizePolicy   // 自适应大小调整策略, 用于调整Eden与Servivor的大小, 晋升阈值的大小
     *
     * -XX:GCTimeRatio = ratio
     *      ratio = 垃圾回收时间 / 总时间
     *      默认 99, 堆增大后,gc频率会降低, 这样总时间下降, 增大吞吐量
     *
     *  停顿时间越短越适合交互性程序, 提高用户体验
     *  高吞吐量可以高效率利用cpu时间, 尽快完成运算任务, 适合交互性程序
     *
     * -XX:MaxGCPauseMillis = ms
     *      最大暂停毫秒数(最大垃圾收集时间), 默认200ms
     *      减少暂停时间, 意味着减小堆大小
     *
     *   ParallelGC根据以上两个参数, 调整堆的大小,
     *   但这两个目标是相反的, 要取一个折中
     *
     * -XX:ParallelGCThreads=n  // GC线程数
     */
    private static void testParallel() { }

    /**
     * 串行垃圾回收器: 新生代收集器
     *  jdk1.3.1之前是新生代垃圾回收的唯一选择, 该收集器是单线程的
     *  单线程: 只会使用一个cpu或一条收集线程完成收集工作, 且它进行垃圾回收时,其他工作线程暂停, 直到收集结束
     *
     *  SerialGC仍然是Client模式下新生代默认的垃圾收集器
     *  优势:
     *      没有线程切换开销, 最高的线程利用率
     *      桌面应用下停顿时间很短
     *
     *  +XX:+UseSerialGC =      Serial       +      SerialOld
     *                    工作在新生代(复制)          工作在老年代(标记+整理)
     *                      Minor GC                    Full GC
     */
    private static void testSerial() { }
}
