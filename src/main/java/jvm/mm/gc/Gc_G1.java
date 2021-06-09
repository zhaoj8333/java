package jvm.mm.gc;

/**
 * 吞吐量:
 *     吞吐量关注在指定时间内, 最大化一个应用的工作量
 *     对于关注吞吐量的系统, 卡顿是可以接受的, 因为这个系统关注长时间的大量人物的执行能力, 单词快速的响应不值得考虑
 *
 * 响应能力:
 *     程序或者系统能否及时响应
 *     对于响应能力敏感的场景, 长时间的停顿是无法接受的
 *
 * G1收集器:
 *     面向服务端,
 *     适用于多核处理器, 大内存容量
 *
 *     满足短时间gc停顿的同时, 还能达到较高的吞吐量
 *
 * G1设计目标:
 *     与应用线程同时工作, 几乎不需要STW(与CMS类似)
 *     整理剩余时间, 不产生内存碎片(CMS只能在Full gc时, 用STW整理内存碎片)
 *     GC停顿更加可控
 *     不牺牲系统吞吐量
 *     GC不要求额外的内存空间(CMS需要预留空间存储浮动垃圾)
 *
 * G1与CMS:
 *     G1在某些方面是为了替换CMS, 弥补了CMS的某些不足, G1使用copying算法, 不会产生内存碎片
 *     G1提供了更多手段, 以达到对gc停顿时间的可控
 *
 */
public class Gc_G1 {
    public static void main(String[] args) {
        // https://www.oracle.com/technetwork/tutorials/tutorials-1876574.html
        g1Log();
    }

    /**
     * -verbose:gc -Xms10m -Xmx10m -XX:+UseG1GC
     * -XX:+PrintGCDetails
     * -XX:+PrintGCDateStamps
     * -XX:MaxGCPauseMillis=200m
     *
     * G1 日志:
     *     2020-10-13T14:34:23.097+0800: [GC pause (G1 Humongous Allocation) (young) (initial-mark), 0.0042173 secs]
     *    [Parallel Time: 1.4 ms, GC Workers: 8]
     *       [GC Worker Start (ms): Min: 149.1, Avg: 149.2, Max: 149.4, Diff: 0.3]
     *       [Ext Root Scanning (ms): Min: 0.0, Avg: 0.5, Max: 1.0, Diff: 0.9, Sum: 4.0]    // 根扫描
     *       [Update RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]            // 更新RSet, 处理dirty card队列
     *          [Processed Buffers: Min: 0, Avg: 0.0, Max: 0, Diff: 0, Sum: 0]              // 处理RS
     *       [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
     *       [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
     *       [Object Copy (ms): Min: 0.0, Avg: 0.2, Max: 0.3, Diff: 0.3, Sum: 1.5]          // 对象拷贝: 拷贝存活对象到survivor/old
     *       [Termination (ms): Min: 0.0, Avg: 0.4, Max: 0.7, Diff: 0.7, Sum: 3.3]          // 处理引用队列: 软/弱/虚引用处理
     *          [Termination Attempts: Min: 1, Avg: 4.0, Max: 9, Diff: 8, Sum: 32]
     *       [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.1]
     *       [GC Worker Total (ms): Min: 1.0, Avg: 1.1, Max: 1.3, Diff: 0.3, Sum: 8.9]
     *       [GC Worker End (ms): Min: 150.3, Avg: 150.3, Max: 150.4, Diff: 0.1]
     *    [Code Root Fixup: 0.0 ms]
     *    [Code Root Purge: 0.0 ms]
     *    [Clear CT: 0.1 ms]        // CT: 卡表
     *    [Other: 2.8 ms]
     *       [Choose CSet: 0.0 ms]
     *       [Ref Proc: 2.7 ms]
     *       [Ref Enq: 0.0 ms]
     *       [Redirty Cards: 0.1 ms]
     *       [Humongous Register: 0.0 ms]
     *       [Humongous Reclaim: 0.0 ms]
     *       [Free CSet: 0.0 ms]
     *       // 以下是YGC后整个堆的结果
     *    [Eden: 2048.0K(6144.0K)->0.0B(2048.0K) Survivors: 0.0B->1024.0K Heap: 3690.1K(10.0M)->2560.1K(10.0M)]
     *  [Times: user=0.02 sys=0.00, real=0.01 secs]
     * 2020-10-13T14:34:23.102+0800: [GC concurrent-root-region-scan-start]
     * 2020-10-13T14:34:23.102+0800: [GC pause (G1 Humongous Allocation) (young)2020-10-13T14:34:23.103+0800: [GC concurrent-root-region-scan-end, 0.0010239 secs]
     * 2020-10-13T14:34:23.103+0800: [GC concurrent-mark-start]
     * , 0.0064183 secs]
     *    [Root Region Scan Waiting: 0.4 ms]
     *    [Parallel Time: 2.8 ms, GC Workers: 8]
     *       [GC Worker Start (ms): Min: 154.4, Avg: 155.6, Max: 157.0, Diff: 2.6]
     *       [Ext Root Scanning (ms): Min: 0.0, Avg: 0.1, Max: 0.2, Diff: 0.2, Sum: 0.6]
     *       [Update RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
     *          [Processed Buffers: Min: 0, Avg: 0.0, Max: 0, Diff: 0, Sum: 0]
     *       [Scan RS (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
     *       [Code Root Scanning (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
     *       [Object Copy (ms): Min: 0.0, Avg: 0.1, Max: 0.4, Diff: 0.4, Sum: 1.1]
     *       [Termination (ms): Min: 0.0, Avg: 1.3, Max: 2.2, Diff: 2.2, Sum: 10.6]
     *          [Termination Attempts: Min: 1, Avg: 2.0, Max: 5, Diff: 4, Sum: 16]
     *       [GC Worker Other (ms): Min: 0.0, Avg: 0.0, Max: 0.0, Diff: 0.0, Sum: 0.0]
     *       [GC Worker Total (ms): Min: 0.0, Avg: 1.5, Max: 2.8, Diff: 2.8, Sum: 12.3]
     *       [GC Worker End (ms): Min: 157.1, Avg: 157.1, Max: 157.2, Diff: 0.2]
     *    [Code Root Fixup: 0.0 ms]
     *    [Code Root Purge: 0.0 ms]
     *    [Clear CT: 0.1 ms]
     *    [Other: 3.1 ms]
     *       [Choose CSet: 0.0 ms]
     *       [Ref Proc: 2.9 ms]
     *       [Ref Enq: 0.0 ms]
     *       [Redirty Cards: 0.1 ms]
     *       [Humongous Register: 0.0 ms]
     *       [Humongous Reclaim: 0.0 ms]
     *       [Free CSet: 0.0 ms]
     *    [Eden: 1024.0K(2048.0K)->0.0B(1024.0K) Survivors: 1024.0K->1024.0K Heap: 3625.1K(10.0M)->3551.4K(10.0M)]
     *  [Times: user=0.01 sys=0.00, real=0.00 secs]
     * 2020-10-13T14:34:23.109+0800: [GC concurrent-mark-end, 0.0065618 secs]
     * 2020-10-13T14:34:23.109+0800: [GC remark 2020-10-13T14:34:23.109+0800: [Finalize Marking, 0.0037652 secs] 2020-10-13T14:34:23.113+0800: [GC ref-proc, 0.0001852 secs] 2020-10-13T14:34:23.113+0800: [Unloading, 0.0004427 secs], 0.0045117 secs]
     *  [Times: user=0.01 sys=0.00, real=0.00 secs]
     * 2020-10-13T14:34:23.114+0800: [GC cleanup 4652K->4652K(10M), 0.0003877 secs]
     *  [Times: user=0.00 sys=0.00, real=0.00 secs]
     * hello, world
     * Heap
     *  garbage-first heap   total 10240K, used 4575K [0x00000000ff600000, 0x00000000ff700050, 0x0000000100000000)
     *   region size 1024K, 2 young (2048K), 1 survivors (1024K)
     *  Metaspace       used 3278K, capacity 4500K, committed 4864K, reserved 1056768K
     *   class space    used 360K, capacity 388K, committed 512K, reserved 1048576K
     */
    private static void g1Log() {
        int size = 1024 * 1024;
        final byte[] bytes1 = new byte[size];
        final byte[] bytes2 = new byte[size];
        final byte[] bytes3 = new byte[size];
        final byte[] bytes4 = new byte[size];
        System.out.println("hello, world");
    }

    /**
     * 堆结构
     *     g1并不会对内存作 年轻代,老年代 的大范围的划分, 而是作将heap划分为一个个相等的不连续的内存区域(region)
     *     每个region都有一个分代角色: eden, survivor, old
     *     对每种分代内存的大小, 可以动态的变化
     *
     *     > G1最大特点就是高效执行回收, 优先执行大量对象可回收的区域, 而不是一次性回收所有
     *     > G1使用了gc停顿可预测的模型, 来满足用户设定的gc停顿时间, 根据用户设置的目标时间, G1会自动的选择哪些region要清理, 一次要清理多少region
     *     > G1从多个region中复制存活对象, 然后集中放入一个region中, 同时整理, 清除内存(copying)
     *
     * G1与CMS
     *     相对于cms的mark-sweep算法,G1的copying不会造成内存碎片
     *     对比Parallel Scavenge(基于copying), Parallel Old收集器(基于mark-compact-sweep), parallel会对整个区域做整理导致gc停顿时间过长
     *      而g1只是特定的整理几个region
     *
     *     > G1并非一个实时的收集器, 与parallel scavenge一样, 对gc停顿时间设置并不绝对生效, G1而是有较高几率保证不超过设定的gc停顿时间
     *       G1通过对哪些region进行评估决定是否要进行回收, 今儿满足用户对gc停顿时间的设定
     */
    private static void heapStructure() {}

    /**
     * 重要概念
     *     * 分区: G1采取不同策略解决并行, 串行 和 CMS收集器的碎片, 暂停时间不可控问题,
     *             G1将整个堆分成相同大小的分区(region), 每个区域都是连续的虚拟内存
     *             每个区域都属于固定的Eden, survivor, 老年代, 但是不同代的内存占有大小是不固定的
     *             老年代, 年轻代, survivor概念都存在, 但只是逻辑上的概念, 任何时刻每个分区只能属于某个代
     *             分区的代际角色是可以变化的
     *             分区大小相同, 但是不同代的分区多少是不定的
     *
     *     * region之间物理上不需要连续, 角色之间也不需要连续, 不同分区垃圾对象数量不一致, G1会优先回收垃圾对象特别多的分区
     *             花费较少的时间来回收这些分区的垃圾, 这就是G1名称的来源, 即, 优先回收垃圾最多的区域
     *
     *     * 依然是在新生代满了后, 对整个新生代进行回收新生代中的对象, 要么回收, 要么晋升
     *             新生代与老年代策略是统一的, 方便调整代的大小
     *
     *     * G1具有压缩属性, 回收老年代时, 在存活对象的跨分区拷贝时, 会进行局部压缩
     *
     *     * 收集集合(CSet): 一组可被回收的分区集合, 在CSet中存活的数据会在GC过程中被移动到另一个可用分区, CSet中的分区可来自任何代
     *
     *     * 已记忆集合(RSet): RSet记录了其他region中的对象引用本region中对象的关系, 属于points-into结构(谁引用了我的对象)
     *         RSet的价值在于使得垃圾收集器不需要扫描整个堆找到谁引用了当前分区中的对象, 只需要扫描RSet即可
     *         G1在points-out的card table只上再加了一层结构构成points-into RSet, RSet中, 每个region会记录下到底哪些别的region有指向自己的指针, 而这些指针分别在哪些card的范围内
     *         这个RSet实际上是一个哈希表, key是别的region的起始地址, value是一个集合, 元素是card table的index
     *
     *     * SATB(Snapshot-At-The-Beginning): 是G1 在并发标记阶段使用的增量式标记算法
     *         并发标记是多线程的, 但并发线程在同一时刻只扫描一个分区
     *
     *     * 依然是在新生代慢的时候, 对整个新生代进行回收 -- 新生代对象要么回收,要么晋升, 置于新生代也采取分区机制的原因, 则是因为这样跟老年代策略统一, 方便调整代的大小
     */

    /** G1并发全局标记:
     *     通过并发全局标记查找堆中所有的对象的存活状态, 当该阶段完成时, G1就得知所有区域对象的存活情况
     *     G1首先会收集通常会产生大量空闲空间的region
     *
     *     被G1识别为作为垃圾可以进行回收的区域, G1将对象从一个或多个region复制到另一个单个region, 在该过程中会进行压缩并释放内存
     *     为了减少停顿时间并提高吞吐量, 这种释放在多处理器上是并行的;
     *     对于每个垃圾收集, G1都会进行持续的工作减少碎片的产生, 并工作在用户所指定的停顿时间之内, 这完全超出了之前的GC收集器
     *     相对于G1, CMS不会做压缩, ParallelOld只会在整个堆上做垃圾收集
     *
     *     G1并非一个实时的收集器, 尽最大努力满足用户设置的停顿目标, 但是不是完全精确
     *     G1基于并发收集阶段的, G1对用户指定时间, 对收集多少区域, 哪些区域会做一个近似的估算
     *
     *     G1使用停顿预测模型以满足用户定义的暂停时间目标并基于该时间选择一定数量的region进行回收
     *         停顿预测模型:
     *              G1是一个响应时间优先的GC算法, 用户可以设定整个GC的期望停顿时间, 由MaxGCPauseMillis控制, 默认200ms, 但不是硬性条件,
     *              该时间表示G1会努力在改时间之内完成垃圾回收工作, 但是不能绝对保证
     *
     *     G1工作既可以是并发的, 也可以是并行的, Full gc依然是单线程的, 但是良好的调优可以避免Full gc
     */

    /**
     * G1的推荐使用场景:
     *     G1关注于运行程序需要大内存且gc延迟小的场景
     *     如果有以下场景, 程序将会在CMS或ParallelOldGC切换到G1中受益
     *       > full gc持续太长或太频繁
     *       > 对象的晋升或分配频率太高
     *       > 过长的gc或压缩时长
     */

    /**
     * CMS专用于老年代
     * 通过与用户线程并发工作, 将占用gc中时间最长的 垃圾收集工作并发执行, 来减少垃圾回收带来的延迟
     * 通常不会拷贝或压缩存活对象, gc不会移动对象, 如果出现碎片问题, 分配一个更大的堆
     *
     * 在年轻代, CMS收集器使用与parallel collector相同的算法
     *
     * CMS:
     *     1. 堆结构划分: Eden, Survivors, Old
     *         老年代是持续的空间, 回收为就地完成, 没有压缩, 除非有full gc
     *     2. 年轻代的GC:？？？
     *         CMS的老年代gc运行一段时间后, 内存碎片会比较多
     *     3. 年轻代的收集:
     *         存活对象从Eden复制到survivor空间, 任何打到晋升阈值的对象会被晋升到老年代
     *     4. 年轻代gc后, Eden和其中一个survivor空间被清空
     *     5. 当老年代打到了一个特定的占用率, CMS将会被触发
     *     6. 并发清理: 没有被标记的对象会就地取消分配(对象消亡), 没有压缩
     */
    private static void compareCms() {}

    /**
     * 1. 堆结构
     *     被分成多个regions
     *     region大小在jvm启动时确定
     * 2. 堆分配
     *     堆逻辑上被分为多个region, region在逻辑上属于不同的代际,存活对象在不同的region之间移动, region被设计为可以并行收集且不导致STW
     *     除了Eden, survivor和old代区域, 还有一种humonguous区, 专门用来存放大小超过50% * 标准区大小 的对象,他们被存放在连续的region
     *     堆被分为大约2000个region, 大小范围为 1MB ~ 32MB,
     *
     *     G1的年轻代收集:
     *      young gc中, 存活对象被复制/移动到一个或多个survivor区域, 如果年龄超过晋升阈值,对象立即进入老年代, 此时会有一个STW,
     *      eden大小与survivor大小会被重新计算, 为下一次gc做准备, 记账信息会被用于大小的计算,
     *      暂停时间目标也被纳入考虑之中, 这种方式使得region的调整更加容易
     *
     *      堆空间是一个单个的内存区域, 并被划分为多个region
     *      年轻代内存由非连续的region组成, 这在大小调整时变得很容易
     *      young gc会有STW, 且以并行方式进行
     *      存活对象被拷贝到新的survivor或老年代区域
     *
     *     G1的老年代收集:
     *      (1). 初始标记: STW, 标记跨代引用, 存活对象的初始标记是在年轻代垃圾收集上进行的
     *      (2). 根region扫描, 如果空region被发现, 会在再次标记时被移除
     *      (3). 并发标记:
     *      (4). 再次标记: STW, 空区域被移除和回收
     *      (5). 清理: STW且并发, G1会选择存活率最低的分区, 这些分区回收最快, 与young gc一样
     *      (6). 复制: STW
     *      清理/复制阶段后,
     *
     * 3. 并发标记阶段, 生存信息被并发的计算出来, 并被用于计算那个区域最被适合用于回收
     *    重新标记阶段, 使用SATB算法, 完全空的区域也会被回收
     *    复制/清理阶段, 新生代/老年代被同时回收, 老年代区域会给予存活情况进行选择
     *
     *
     */
    private static void compareG1() {}

    /**
     * G1在压缩空间方面有优势
     * G1通过内存空间区域的方式避免内存碎片
     * G1 eden,survivor,old区不在固定, 内存使用上更灵活
     * G1通过设置预期停顿时间来控制垃圾收集时间,避免应用雪崩
     * G1在回收内存后会马上做合并空闲内存的工作, 而CMS是在STW做
     * G1同时用于新生代, 老年代
     *
     * 适合场景:
     *     服务端, 多核cpu, jvm内存占用较大
     *     碎片多,需要经常压缩
     *     想要更可控, 可预期的gc
     *
     * 新生代的收集依然采用STW和拷贝的方式
     *
     * Humongous区域:
     *     存放巨型对象, 默认会直接被分配到老年代, 但如果是一个短期存在的对象, 会对gc造成负面影响, 因此使用Humongous区域存放巨型对象
     *     如果humongous region装不下巨型对象, 则g1会寻找连续的分区来存储, 为了找到连续分区, 有时候不得不启动full gc
     *
     */
    private static void cmd2G1() {}

    /**
     * Young GC:(YGC)
     *     选定所有年轻代的region, 控制年轻代region个数
     *     触发: 在eden耗尽时触发, 回收后eden区块全部被回收, 不属于任何代际的内存区域, 变成空白
     *     YGC依然采用STW, 将存活对象拷贝到老年代或survivor
     *     RSet: G1中, 没有使用CMS类似的point-out, 由于分区太小, 分区数量多, 会造成大量扫描浪费, 有些不需要gc的分区也被扫描了
     *           CMS中,RSet记录老年代指向新生代的引用, ygc扫描根时, 只需扫描RSet, 不需扫描整个老年代
     *           于是G1使用了point-in, 记录哪些分区引用了当前分区的对象, 仅仅将这些对象当做根来扫描避免无效扫描
     *           新生代之间的引用无须记录, 因为每次gc所有新生代都被会扫描, 只需记录老年代到新生代之间的引用
     *
     *     赋值器开销:
     *         如果引用对象很多,赋值器需要对每个引用做处理, 赋值器开销很大
     *         卡表:
     *             将一个region在逻辑上划分为固定大小的连续区域, 每个区域为一个Card, Card Table为字节数组, 由Card的索引来标识分区的空间地址
     *             默认下每个Card都未被引用. 当一个地址空间被引用时, 这个地址空间对应的数组索引值被标记为"0"(脏)
     *             此外RSet也将这个数组下标记录下来 {"别的region起始地址" : "集合, 元素为Card Table的index"}
     *
     *     阶段:
     *         根扫描:
     *             静态和本地对象被扫描
     *         更新RS:
     *             处理dirty card队列更新RS
     *         处理RS:
     *             检测从年轻代指向老年代的对象
     *         对象拷贝:
     *         处理引用队列:
     *             软,弱,虚引用处理
     */
    /**
     * Mixed GC:
     *     选定所有年轻代里的region, 外加根据 global concurrent marking 统计得出收集效益高的若干老年代region
     *     在用户指定的开销范围内尽可能选择高收益的老年代region
     *     不是full gc, 只能回收部分老年代的region, 如果mixed gc无法跟上内存分配速度, 会serial old gc(full gc)来收集整个堆, 所以g1无法提供full gc
     *
     *     步骤:
     *         全局并发标记: global concurrent marking
     *         拷贝存活对象: evacuation
     *
     *     触发:
     *         G1HeapWastePercent:
     *             在Global Concurrent Marking之后, 可知老年代regions要被回收的空间, ygc后和mixed gc之前,如果垃圾占比达到此参数则在下一次发生Mixed GC
     *         G1MixedGcLiveThresholdPercent:
     *             老年代region中的存活对象的占比, 只有在此参数之下, 才会被选入CSet
     *         G1MixedGCCountTarget:
     *             一次global concurrent marking之后,最多执行mixed gc的次数
     *         G1OldCSetRegionThresholdPercent:
     *             一次mixed gc中能被选入CSet的最多老年代region的数量
     *         -XX:G1HeapRegionSize=N
     *         -XX:MaxGCPauseMillis
     *         -XX:G1NewSizePercent: 新生代占据heap最小值, 默认5%
     *         -XX:G1MaxNewSizePercent: 新生代占据heap最大值, 60%
     *         -XX:ParallelGCThreads: STW期间,并行gc线程数
     *         -XX:ConcGCThreads=n: 并发标记阶段, 并行执行的线程数
     *         -XX:InitiatingHeapOccupancyPercent: 设置触发标记周期的java堆占用率阈值,默认45%
     *
     *
     * Global Concurrent Marking:
     *     初始标记: STW, 从GC Root开始直接可达的对象
     *     并发标记(Concurrent Marking): 对heap中的对象进行标记, 收集各个region的存活信息
     *     重新标记: STW, 标记在并发阶段发生变化的对象
     *     清理: 清理空region, 加入到free list, 只是回收了没有存活对象的region,无须STW
     *
     * 总结:
     *     YGC: 收集年轻带的region, 此时CSet就是所有年轻代里面的region
     *     Mixed GC: 年轻代的所有region + 全局并发标记阶段选出的收益高的region, CSet是所有年轻代里的region加上全局并发标记阶段标记出的收益高的region
     *     无论是YGC, 还是Mixed GC都只是并发拷贝阶段
     *
     *     G1运行时, 会在YGC与Mixed GC之间切换运行, 同时定期做全局并发标记, 赶不上对象创建速度时使用Serial GC, 此时是重点调优的地方
     *     初始标记是在YGC上执行的, 进行全局并发标记时不会做Mixed GC, 在做Mixed GC时不会启动初始标记
     *
     */
    private static void gcMode() {}

    /**
     * 三色表计算法: 一种描述追踪式回收器的一种有效方法, 利用它可以推演回收器的正确性
     *     黑色: 根对象, 或者该对象与它的子对象(fields)都被扫描并标记过了
     *     灰色: 对象本身被扫描, 子对象还没用扫描或标记完
     *     白色: 未被扫描对象, 扫描完所有对象后, 白色为最终不可达对象,即 垃圾对象
     *
     * 标记过程:
     *     * 根对象为黑色, 子对象设置为灰色
     *     * 继续由灰色遍历, 将已扫描子对象的对象置为黑色
     *     * 遍历了所有科大的对象后, 所有可达对象都被标记成了黑色, 不可达的对象为白色, 需要被清理
     *
     * 初始标记 -> 并发标记 -> 重新标记 -> 清理
     *     其中, 三色标记法就是基于并发标记会产生的问题而提出的
     *     对象被标记: 表示对象可达
     *
     *     SATB: 就是为了解决三色标记算法并发标记中由于gc线程与应用程序线程导致的一系列问题
     *         漏标: 对象本来可达, 但是被误认为是垃圾, 其后果是致命的, 影响程序的正确性
     *             * 漏标的情况只会发生在白色对象中, 且满足以下任一条件:
     *                 1. 并发标记时, 其他线程给黑色对象(一旦被标记为黑色, 就不会再往下标记了)的引用复制了该白色对象
     *                     对于该情况, 使用post-write barrier记录所有的新增的引用关系,然后根据这些引用关系为根扫描重新扫描一遍
     *                 2. 并发标记时, 其他线程删除了所有灰色对象到该白色对象的引用, 或者又有其他黑色对象执行它
     *                     利用pre-write barrier, 将所有被删除的引用关系的旧引用记录下来, 最后以这些旧引用为根重新扫描一遍
     *             * 情形
     *                 black新引用了一个white对象, 又从gray对象删除了对该white对象的引用
     *                 black新引用了一个white对象, 又从gray对象删除了一个引用该white对象的white对象
     *                 black新引用了一个刚new出来的white对象, 没有其他gray对象引用该white对象
     *
     *         误标: 把本是垃圾对象, 误当成可达对象, 误标不会造成严重问题, 但是产生浮动垃圾
     *
     *     对漏标的解决:
     *         SATB在marking阶段中, 对于从gray对象移除的目标引用对象标记为gray, 对于black引用的新产生的对象标记为black
     *         由于是在新开始的时候进行snapshot, 因而存在 浮动垃圾
     *     对误标的解决:
     *         误标没什么关系, 顶多产生浮动垃圾
     *
     *   SATB执行过程:( gc开始时的一个快照 ), 是通过root tracing得到的, 作用是维持gc的正确性
     *       (1).在SATB是维持并发GC的一种手段, 是G1并发的基础, 开始标记的时候生成一个快照, 标记存活对象(标记出开始时全部存活的对象), 形成一个对象图
     *           GC收集时, 新生代对象也认为是活的对象, 其他不可达的对象都是垃圾对象
     *
     *           如何找到在GC过程中分配的对象?? :::
     *              每个region都记录着两个指针: top-at-mark-start(TAMS), 分别为prevTAMS, nextTAMS
     *              在TAMS以上的对象就是新分配的, 因而被视为隐式marked
     *              通过这种方式, 就找到了gc中新分配的对象, 并把这些对象认为是活的对象
     *
     *       (2). 在并发标记的时候所有被改变的对象入队
     *           写屏障里把所有旧的引用所指向的对象都变成非白的, 因此收集器不会将该对象变成垃圾收集掉
     *
     *       (3). 可能存在浮动垃圾, 将在下次被收集
     *
     *
     */
    private static void satb() {}

    /**
     * 根节点枚举
     *     gc roots中一般为全局性引用
     *     gc roots一般为全局性引用(常量, 类静态属性)和执行上下文(栈帧本地变量表)
     *     枚举gc roots时需要考虑分析中产生结果的准确性以及枚举效率
     *
     * 准确性保障:
     *     分析期间整个执行系统不可以出现引用关系变化的情况, 所以必须停顿所有线程, jvm采用了安全点的方式
     *     * 安全点的选择: 标准: "程序是否具有让程序长时间执行的特征", 指令复用
     *     * 安全点的中断方式:
     *         抢先式中断: 中断全部线程, 又会等待线程
     *         主动式中断: 线程轮询是否到达安全点, 避免了 中断 -> 启动 -> 又中断 的过程
     *     * 安全区域:
     *         程序在安全区域时, 引用关系不会发生改变
     *         线程执行到安全区域时, 首先表示自己进入安全区域, 此时jvm发起gc, 就不用标识自己为安全区域状态的线程了, 该线程等待根节点枚举或这个gc完成之后, 继续运行
     *
     * 快速性保障:
     *     执行系统停顿后, 不需要检查全部执行上下文和全局引用,
     *     hotspot使用了oopMap达到目的
     *         类加载完成时, hotspot就把对象内什么偏移量上是什么类型的数据计算出来,
     *         JIT编译时, 在特定位置记录下栈和寄存器哪些位置是引用,
     *         GC扫描时,可以根据oopMap上记录信息准确定位到哪个区域中有对象的引用, 减少通过逐个遍历查找引用的消耗
     *
     */
    private static void gcRoots() {}

    /**
     * 并发
     */
    private static void concAccessibilityAnalysis() {}

    /**
     * 停顿预测模型:
     *     G1收集器突出表现出来的一点就是通过一个停顿预测模型根据用户配置的停顿时间来选择CSet的大小, 从而达到用户期待的应用程序暂停时间
     *
     * G1调优:
     * -XX:MaxGCPauseMills参数:
     *    > 停顿时间并不是越短越好, 时间越短意味着每次收集的CSet越小,导致垃圾逐步增多, 可能导致退化为Serial GC
     *        100ms到200ms都比较合理, 50ms不太合理, 太短导致gc赶不上垃圾产生速度, 退化为full gc
     *    > 停顿时间设置越长, 影响程序响应时间
     *
     * 不要设置新生代和老年代大小:
     *     G1会根据对象的生成动态调整新生代和老年代的大小, 通过改变代的大小调整对象晋升的速度以及晋升年龄
     *     设置新生代大小相当于放弃了g1所做的自动调优, 所做的则是分配整个堆的大小就可以了
     *
     * 关注Evacuation Failure:
     *     Evacuation Failure类似于CMS的晋升失败, 堆空间垃圾太多导致无法完成region之间的拷贝,于是不得不退化为full gc来做一次全局gc
     */
    private static void optimize() {}

    /**
     * G1运行模式:
     *     YGC: Eden满时触发, 回收后Eden区域全部变白,不属于任何代际
     *     并发阶段
     *     Mixed GC:
     *     full gc(G1出问题时发生)
     */
    private static void runningMode() {}
}
