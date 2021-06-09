package jvm.mm.gc;

/**
 * Garbage First
 *
 * JDK-9废弃了CMS
 *
 * 适用场景:
 *     同时注重吞吐量和低延迟, 默认暂停目标位200ms
 *     超大堆内存, 会将划分为多个大小相等的Region
 *     整体上是 标记 + 整理 算法, 两个区域之间是复制算法
 *
 * -XX:+UseG1GC
 *
 * // 设置区域大小, 必须是1 2 4 8 16 ... 大小
 * -XX:G1HeapRegionSize= size
 *
 * // 最大暂停时间
 * -XX:MaxGCPauseMillis=time
 */
/**
 * 1. 回收阶段:
 *     Young Collection
 *         进行GC Root的初始标记
 *         新生代垃圾回收的STW相对较短
 *         内存紧张时,将幸存的对象使用拷贝算法放入幸存区
 *         幸存区内存紧张时, 又会触发垃圾回收一部分晋升到老年代, 年龄不够的再次被拷贝到幸存区(内存整理)
 *
 *     Young Collection + Concurrent Mark
 *         在Young GC时进行GC Root的初始标记
 *         老年代占用堆空间比例达到阈值时, 进行并发标记(不会STW),
 *             -XX:InitiatingHeapOccupancyPercent=percent(默认45%)
 *
 *     Mixed Collection
 *         会对E, S, O进行全面的垃圾回收
 *         > 最终标记(Remark)会STW
 *         > 拷贝存活(Evaluation)会STW
 *
 *         G1会根据-XX:MaxGCPauseMillis = ms
 *             为打到这个目标, 挑选回收价值最高的进行回收, 如果所选择区域回收对象释放的空间最多, 就进行回收
 *             优先回收垃圾最多的区域
 *
 *     发生full gc的场景:
 *         如果垃圾回收速度跟不上垃圾产生的速度时,并发收集失败, 此时退化为串行收集, 执行full gc, 导致更长的STW
 *
 *     跨代引用问题:
 *         老年代对象一般比较多, 如果遍历的话性能较低
 *         将老年代以512KB大小分为多个card, 如果一个对象引用了其他年龄代对象, 这个card被标记为dirty
 *         以后在寻找GC Root时,不用找整个老年代, 只关注dirty card, 减少搜索范围
 *
 *     新生代的卡表与Remembered Set
 *         记录从外部对我的引用, 即 脏卡, 通过这些脏卡遍历 GC Root
 *         在引用变更时通过post-write barrier + dirty card queue
 *         该操作通过异步完成, 由另一线程更新脏卡, concurrent refinement threads 更新Remembered Set
 *
 *     Remark(并发标记时, 对象的处理状态)
 *         pre-write barrier + satb_mark_queue
 *         如果引用关系改变, 被引用的对象会进入satb_mark_queue, 防止被误当成垃圾而被回收
 *
 *     JDK 8u20字符串去重
 *         优点: 节省内存
 *         缺点: 多占用cpu时间,新生代回收时间略微增加
 *         -XX:+UseStringDeduplication
 *         String s1 = new String("hello")
 *         String s2 = new String("hello")
 *         // 首先可以通过intern去重
 *         // G1使用一种新的方式去重:
 *         //   将新分配的字符串入队列
 *         //   垃圾回收时, 并发检查是否有重复
 *         //   如果值一样, 让他们引用一个相同的char[]
 *         //   与intern()不一样, intern关注字符串对象, 字符串去重关注char数组, jvm内部使用了不同的字符串表
 *
 *     并发标记类卸载 8u40
 *         对象经过并发标记后, 就知道了哪些类不被使用, 当一个加载器的所有类都不再使用, 则卸载它所加载到的所有类
 *         -XX:+ClassUnloadingWithConcurrentMark默认启用
 *
 *     巨型对象回收 8u40
 *         对象大于region的一半时,为巨型对象
 *         G1不会对巨型对象进行拷贝, 且回收时会被优先考虑
 *         G1会跟踪老年代所有incoming引用,这样老年代incoming引用为0的巨型对象就可以在新生代垃圾回收时处理掉
 *
 *     JDK9并发标记起始时间调整
 *         并发标记必须在堆空间占满前完成,否则退化为FullGC
 *         JDK9之前需要使用 -XX:InitiatingHeapOccupancyPercent = 45%(默认), 当老年代占据堆内存超过该值时, 垃圾回收开始
 *         JDK9可以动态调整:
 *             -XX:InitiatingHeapOccupancyPercent用来设置初始值
 *             进行数据采样并动态调整
 *             总会添加一个安全的空档空间,存放浮动垃圾, 防止GC退化为FullGC
 *
 *     JDK9更高效的回收
 *         250+增强
 *         180+bug修复
 *
 *     -XX:+PrintFlagsFinal -version | findstr "GC"
 *
 *
 * 以上是一个循环的过程
 */
public class GarbageFirst {

    public static void main(String[] args) {

    }

    /**
     * Eden满了会触发新生代的垃圾回收, 并且引发STW
     */
    private static void testYoungCollec() {

    }
}
