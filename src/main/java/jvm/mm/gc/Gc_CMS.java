package jvm.mm.gc;

/**
 * CMS收集器
 *
 * 枚举根节点: GC Roots
 *     当执行系统停顿下来后, 并不需要一个不漏的检查完所有上下文和全局的引用位置
 *     jvm应该是有办法直接得知哪些地方存放着对象引用
 *     1. 遍历方法区和栈区查找
 *     2. OopMap记录gc roots
 *     hotspot使用了oopMap的数据结构来达到目的
 *
 * STW: 可达性分析算法必须是确保一致性的内存快照中进行, 如果分析中引用关系不断变化, 分析的准确定无法保证
 *
 * 安全点:
 *     在OopMap的协助下, hotspot可以快速准确的完成gc roots枚举
 *     但是如果引用关系变化, 或者为每条指令生成oopmap, gc成本将会变得异常高昂
 *
 *     实际上, hotspot并没有为每条指令生成oopmap,而是在特定位置记录了这些信息, 这些位置称为 - 安全点
 *     程序执行时并非在所有地方都能停顿下来开始gc, 只有打到安全点时才会暂停
 *
 *     安全点的选定:
 *         既不能太少以至于让gc等待时间过长, 也不能过于频繁以至于过分增大运行时负载, 所以选定标准为:
 *         ** 是否具有让程序程序长时间执行的特征 **
 *         指令不太可能因为指令流长度太长而过长时间运行, 因为每条指令执行时间很短暂
 *         指令长时间执行的特征就是指令序列复用, 如: 方法调用, 循环跳转, 异常跳转, 这些指令才会产生安全点
 *         为避免程序长时间无法进入安全点
 *         1. 循环末尾
 *         2. 方法临返回前
 *         3. 调用方法之后
 *         4. 抛异常的位置
 *         同时, 还需考虑如何在gc时让所有线程都跑到最近的安全点上在停顿下来
 *             抢占式中断: gc发生时把所有线程中断, 如果线程未到达安全点, 等待其跑到安全点
 *             主动式中断(几乎所有虚拟机采用): gc需要中断线程时, 设置标志让线程轮询该标志, 发现中断标志为真时自己将会挂起
 *
 * 安全区域:
 *     安全点保证了程序执行时, 在不太长的时间内就会遇到可gc的点, 但如果程序没有分配cpu时间, 处于sleep或blocked状态, 程序无法响应中断, 此时需要安全区域来解决
 *
 *     * 安全区域就是在一段代码片段中, 引用关系不会发生变化, 在这个区域的任意地方进行gc都是安全的
 *     * 当线程执行到安全区的代码时, 首先标记自己进入了安全区, 此时要gc时就不用标识自己为安全区域状态的线程了
 *     线程离开安全区域时, 要检查系统是否完成了gc roots枚举, 如果完成, 继续执行, 否则就等待直到收到可以离开安全区域的信号为止
 *
 */

/** CMS: 并发标记清除收集器
 *
 *     目标: 以获取最短回收停顿时间为目标, 多用于互联网应用
 *     工作步骤:
 *         1. Initial Mark(初始标记):
 *             STW,
 *             标记gc roots能直接关联到的对象, 或者被年轻代存货对象所引用的所有对象
 *             速度快
 *
 *         2. Concurrent Mark(并发标记):
 *             进行GC Roots Tracing的过程, 寻找被gc roots关联到的对象, 会遍历老年代, 标记所有对象, 根据上一阶段的GC Roots遍历查找
 *             不会阻碍业务线程的执行, 并发执行, 分出若干gc线程进行
 *             并不是所有老年代存活对象都会别标记, 标记那些gc roots最终可达的对象
 *             但是原本运行在标记线程上的cpu核心上的的原用户线程无法运行
 *
 *             具体做法:
 *                 推出标记栈里面的对象, 然后递归标记其直接引用的子对象, 同样的把子对象压到标记栈中,重复推出,压入, 直至清空标记栈
 *
 *         3. Concurrent Preclean(并发预先清除)
 *             与应用线程并发执行
 *             没有STW
 *
 *             跨代引用的处理:
 *                 jvm gc时,都是先找出Root对象,然后按照引用关系做出引用关系图, 得到live对象, 剩下的都是dead对象, 继而回收掉dead对象
 *                 分代回收时, 首先找出root对象, 对于从老年代到年轻代的引用, 可以通过以下方式发现:
 *                     > 从root开始找从老年代到年轻代的引用关系, 比较耗时, 相当于整个heap都做引用关系图
 *                     > 忽略root对象,扫描所有老年代对象, 然后找到从老年代到年轻代的引用
 *                     openjdk使用第二种方式, 对老年代heap设立一个dirty card表, 如对每256k映射到dirty card的一个bit, 如果老年代有对年轻代的引用, 该bit标记为dirty
 *                         卡表: 将堆空间划分为一系列2次幂大小的卡页, hotspot为512字节
 *
 *                         对一个对象引用进行写操作(对象引用改变), 写屏障逻辑将会标记对象所在的卡页为dirty
 *                         minor gc时, gc把老年代dirty card的bit位对应的内存部分的老年对象加入到root, 避免扫描全部
 *
 *             Card Marking: 并发运行时, 一些对象引用会变化, 但是jvm会将包含这个对象的区域(Card)标记为Dirty
 *             从Dirty对象到达的对象也会被标记, 这个标记做完之后, dirty card标记就会被清除了
 *
 *         4. Concurrent Abortable Preclean(并发可放弃的预先清理)
 *             承担STW中最终标记阶段的工作, 由于该阶段是重复做很多相同的工作, 直接满足一些条件
 *
 *         5. Final remark(最终重新标记):
 *             第二个STW阶段
 *             目标是标记老年代所有存活对象
 *
 *             通常该阶段会在年轻代尽可能干净的时候运行, 目的是为了减少连续STW发生的可能性(年轻代存活过多,也会导致老年代涉及的存活对象很多)
 *
 *             修正并发标记期间因用户现场继续执行而导致标记产生变动的的对象的标记记录,
 *             这个阶段停顿时间比初始标记阶段稍长一点, 但远比并发标记时间短
 *
 *        -------------------------- 标记阶段完成 ----------------------------
 *
 *         6. Concurrent Sweep(并发清除)
 *             并发运行, 执行清理
 *             被清理的对象被加入到空闲列表空间, 以用于下次对象分配使用
 *             死对象的合并也发生在此阶段
 *             但是原本运行在清除线程上的cpu核心上的的原用户现场无法运行
 *
 *         7. Concurrent Reset(并发重置): STW
 *             清理gc数据结构, 以备下次gc
 *
 *         整个过程耗时最长的并发标记与并发清除过程, 收集器线程与用户线程同时工作
 *         因此从整体上看, cms回收与用户现线程一起并发执行
 *
 * CMS优点:
 *     并发收集
 *     低停顿
 *     CMS通过将大部分工作分散到并发处理阶段来减少STW时间, 这一块非常优秀
 *
 * 缺点:
 *    > CMS对cpu敏感
 *
 *    > CMS无法处理浮动垃圾, 可能出现concurrent mode failure失败而导致另一次full gc的产生,
 *      Concurrent Mode Failure: 没有在并发期间完成垃圾回收工作
 *      如果老年代增加不是太快, 可以适当调高参数-XX:CMSInitiatingOccupancyFraction,提高触发百分比, 降低内存回收次数
 *      -XX:CMSInitiatingOccupancyFraction: 在cms收集器情况下, 如果CMS预留内存无法满足程序需要时, 启动serial old触发full gc, 这样停顿时间会很长
 *      该参数设置太高很容易导致Concurrent mode failure失败, 新更能反而降低
 *
 *    > 收集结束后可能有大量碎片, 出现老年代有很多空间剩余, 但是无法找到足够大的空间分配新对象, 不得不提前进行full gc
 *      -XX:+UseCMSCompactAtFullCollection: 进行full gc时开启内存碎片的合理整理过程, 内存整理是无法并发的, 碎片没有了, 但停顿时间不得不延长
 *
 *    > 对于堆比较大的应用, GC时间难以预估
 */

import java.io.IOException;

/** 空间分配担保:
 *     minor gc之前, jvm会先检查老年代最大连续空间是否大于新生代所有对象总空间
 *     如果成立, 则确保minor gc是安全的
 *     当大量对象在minor gc后仍然存活, 就需要老年代进行空间分配担保, 把survivor无法容纳的对象直接进入老年代
 *     如果老年代判断到剩余空间不足(根据以往的回收晋升到老年代对象容量的平均值), 则进行一次full gc
 *
 *
 */
public class Gc_CMS {
    public static void main(String[] args) {
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        test();
    }

    /**
     * -verbose:gc -Xms20M -Xmx20M -Xmn10M  老年代大小为 mx - mn = 10M
     * -XX:+PrintGCDetails
     * -XX:SurvivorRatio=8
     * -XX:+UseConcMarkSweepGC
     */
    private static void test() {
        int size = 1024 * 1024;
        final byte[] bytes1 = new byte[4 * size];
        System.out.println("11111");
        // [GC (Allocation Failure)
        // [ParNew: 5762K->439K(9216K), 0.0043605 secs]
        // 5762K->4537K(19456K), 0.0043925 secs] [Times: user=0.01 sys=0.00, real=0.01 secs]

        final byte[] bytes2 = new byte[4 * size];
        System.out.println("22222");
        //　新生代: [GC (Allocation Failure)
        //      [ParNew: 4691K->571K(9216K), 0.0052499 secs]
        //      8790K->8767K(19456K), 0.0052953 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]

        // 老年代: [GC (CMS Initial Mark)
        //      [1 CMS-initial-mark: 8196K(10240K)]   老年代对象占用大小(老年代总大小)
        //      12863K(19456K), 0.0003176 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
        //      对象占用堆大小(整个堆的大小, 减去了1M大小的survivor空间)

        final byte[] bytes3 = new byte[4 * size];
        System.out.println("33333");
        // [CMS-concurrent-mark-start]
        //      [CMS-concurrent-mark: 0.000/0.000 secs]
        //      [Times: user=0.00 sys=0.00, real=0.00 secs]
        // [CMS-concurrent-preclean-start]
        //      [CMS-concurrent-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
        // [CMS-concurrent-abortable-preclean-start]
        //      [CMS-concurrent-abortable-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
        // [GC (CMS Final Remark) [YG occupancy: 6874 K (9216 K)]
        //      [Rescan (parallel) , 0.0016977 secs]    重新扫描
        //      [weak refs processing, 0.0000083 secs]  弱引用处理
        //      [class unloading, 0.0002363 secs]       类卸载
        //      [scrub symbol table, 0.0003514 secs]    符号表
        //      [scrub string table, 0.0000659 secs]    串池
        //      [1 CMS-remark: 8196K(10240K)]
        //      15070K(19456K), 0.0024133 secs]
        //      [Times: user=0.00 sys=0.00, real=0.01 secs]
        //
        // [CMS-concurrent-sweep-start]
        //      [CMS-concurrent-sweep: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]

        // [CMS-concurrent-reset: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]

        final byte[] bytes4 = new byte[2 * size];
        System.out.println("44444");

        System.out.println("------------------------------------------------------\n");
        System.out.println(bytes1);
    }

}
