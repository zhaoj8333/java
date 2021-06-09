package jvm.mm.gc;

/**
 * 调优领域:
 *     内存
 *     锁竞争
 *     cpu占用
 *     io
 *
 * 确定目标:
 *     低延迟/高吞吐量, 选择合适的回收器
 *     CMS / G1 / ZGC
 *     ParallelGC
 *
 * 最快的GC是 不发生GC
 *
 * 考虑因素:
 *     数据量
 *     数据表示否否太臃肿
 *         对象图
 *         对象大小
 *     是否有内存泄漏
 */
public class GcTunning {
    public static void main(String[] args) {
        testTlabAndElliminateAllocation();
    }

    /**
     * 1. full gc和minor gc频繁
     *     空间紧张: 年轻代空间小, 幸存区小, minor gc频繁; 对象晋升阈值变小, 频繁升级到老年代
     *
     * 2. 请求高峰期发生full gc, 单次暂停时间特别长(CMS)
     *     查看gc日志,查看那个阶段时间长: 初始标记(stw) -> 并发标记 -> 重新标记(stw)(可能会比较慢, 扫描整个堆内存) -> 并发清理
     *     -XX:+CMSScavengeBeforeRemark 重新标记前记性一次minor gc
     *
     * 3. 老年代充裕的情况下, 发生full gc(JDK1.7)
     *     1.8之前, 元空间是作为老年代实现的, 永久代空间不足会导致full gc
     *     之后, 元空间回收不是jvm控制的
     */
    private static void examples() {

    }

    /**
     * 老年代调优:
     *     CMS的老年代内存越大越好
     *     先尝试不做调优, 如果没有FullGC, 那么已经..., 否则先尝试调优新生代
     *     观察发生FullGC时老年代内存占用,将老年代内存预设调大 1/4 ~ 1/3
     *     -XX:CMSInitiatingOccupancyFraction=percent 大于percent后老年代会发生GC
     */
    private static void testOldGenTunning() {

    }

    /**
     * 内存栈上分配:
     *      程序很多对象作用域都不会逃逸出方法外, 该对象生命周期会随着方法的调用开始而开始, 方法的调用结束而结束
     *      针对作用域不会逃出方法的对象,在分配内存时将对象栈中(线程私有), 不会给gc额外负担
     *      局限性在于: 占空间小, 对于大对象无法实现栈上分配, 需要进行逃逸分析, 判断对象是否逃逸出函数体
     *
     * -server -Xmx15m -Xms15m
     * -XX:+DoEscapeAnalysis
     * -XX:+PrintGCDetails
     * -XX:-UseTLAB
     * -XX:+EliminateAllocations
     * 不会触发GC
     *
     * -------------------------
     * -XX:-DoEscapeAnalysis 关闭逃逸分析, 则会触发大量gc, 因gc程序性能急剧下降
     * -------------------------
     * -XX:-EliminateAllocations 开启逃逸分析, 关闭标量替换, 也会导致大量gc
     *
     * 标量替换:
     *     java原始类型无法再分解, 所以叫标量
     *     对象则是聚合量, 可以包含任意个数的标量
     *
     * 标量替换: 将一个java对象拆散,其成员变量恢复为分散的变量
     *          拆散后的变量可以被单独分析与优化, 可以各自分别在(栈帧或寄存器)上分配空间, 原本对象无须整体分配空间
     *          简单就是, 标量替换聚合量, 将一个对象拆散, 如果创建的对象未用到其中的全部变量,则可以节省内存, 且无须寻找对象引用
     *
     *          * 如果经逃逸分析发现一个对象不被外部访问, 且该对象可以被拆散, 经过优化后, 并不直接生成对象, 而是在栈上创建若干个成员变量
     *
     * --------------- 栈上分配依赖于 逃逸分析 和 标量替换
     *
     * TLAB分配:
     *     jvm在新生代Eden中开辟了一块线程私有区域, 称为TLAB,不存在线程共享适合快速gc, 所以小对象都优先分配与于TLAB, 且没有锁开销
     *     TLAB结合bump-the-pointer实现对象快速分配, 对象分配时不用锁住整个堆, 只需在自己的缓冲区分配
     *     (每一次的对象分配都必须进行线程同步,使分配效率下降)
     *
     *     Bump-the-pointer:
     *         跟踪Eden最后创建的对象, 放置在Eden顶部, 之后需要创建对象,只需检查Eden剩余空间即可, 但是多线程下需要加锁
     *     TLAB为每个线程在Eden分配一块独享空间,结合bump-the-pointer可以在不加锁的情况下分配内存
     *
     * +-------------+-------------------+
     * |   栈上分配   |       TLAB        |
     * +-------------+-------------------+
     * |  避免gc     |  加速堆上对象的分配 |
     * +-------------+------------------+
     * * java对象分配过程:
     *     逃逸分析, 确定对象是在栈上分配还是堆上分配
     *     如果tlab_top + size <= tlab_end, 在TLAB直接分配对象并tlab_top + size, 不足则重新申请TLAB
     *     如果现有TLAB不足存放对象, 在Elden区加锁(线程共享的), 如果eden_top + size <= eden_end, 存放该对象在Eden, eden_top += size
     *     如果Eden不足以存放,则执行Minor GC, 如果gc后还是无法存放, 则直接分配到老年代
     *
     *  堆上分配对象时有所开销,
     *
     *  分配方法:
     *      指针碰撞: 内存绝对规整, 用过与没用过的内存放两边
     *      空闲列表: 已使用与未使用内存交错, 必须查找该列表, 找到一块足够大的空间划分给该对象实例, 并更新列表记录
     *
     *  分配优先级:
     *      栈上分配 -> TLAB分配  -> eden区分配 -> 老年代分配
     *
     */
    private static void testTlabAndElliminateAllocation() {
        final long s = System.currentTimeMillis();
        for (int i = 0; i < 100000000; i++) {
            alloc();
        }
        final long e = System.currentTimeMillis();
        System.out.println(e - s);
    }

    private static void alloc() {
        final GcTheory s = new GcTheory();
        s.setId(1);
        s.setName("gc");
    }

    /**
     * 新生代调优:
     *
     *  新生代特点:
     *      new操作内存分配非常廉价: TLAB thread-local allocation buffer(线程局部分配缓冲区)
     *          每个线程都会在Eden区域分配一块私有区域, 叫TLAB,new对象时,会首先检查TLAB缓冲区是否有可用内存, 有则在该区域进行内存分配
     *          每个线程分配内存是不会造成内存分配的干扰
     *
     *      新生代死亡对象回收代价是 零
     *           新生代gc皆使用复制算法
     *
     *      大部分对象用过即死
     *           新生代绝大部分对象只有少数会留下
     *
     *      Minor GC时间远远低于FullGC
     *
     *  新生代内存是否越大越好?
     *      -Xmn: 设置新生代堆大小(bytes数)
     *      Oracle官方推荐为 新生代堆大小占据总堆内存大小的 25% ~ 50%
     *
     *      新生代太小, MinorGC会很频繁
     *      新生代太大, 老年代的大小就变小了, FullGC的频率会增加
     *
     *      新生代大小与吞吐量关系:
     *          一开始正比关系, 随后到了一定大小以后有一个下降
     *
     *      标记 + 复制:
     *          主要耗费时间在复制上
     *
     *  1. 新生代能容纳所有的数据: 并发量 * (请求 - 响应)
     *  2. 幸存区 大小: 当前活跃对象 + 需要晋升对象, 某些过小对象可能提前被晋升到老年代, 待到full gc时才会被回收
     *  3. 晋升阈值配置得当, 让长时间存货对象尽快晋升
     *      -XX:MaxTenuringThreshold=threshold
     *      -XX:+PrintTenuringDistribution
     */
    private static void testYoungGenTunning() {}

}
