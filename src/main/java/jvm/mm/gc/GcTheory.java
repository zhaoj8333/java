package jvm.mm.gc;

/**
 * 堆:
 *     堆里存放的是数据实例
 *     是jvm内存管理中内存最大的一块
 *     GC主要的区域, 为了高效GC, 会把堆细分更多区域
 *     线程共享
 *
 * 方法区:
 *     存放了每个Class的结构信息, 包括常量池, 字段描述, 方法描述
 *     GC非主要工作区域
 *
 *     public void me() {
 *         Object obj = new Object();
 *
 *         > 该段代码生成了两个内存区域:
 *             * obj引用变量, 位于jvm栈中
 *             * Object实例对象, 位于堆中
 *
 *         > 上述new语句共消耗20 bytes, 引用占据4 bytes,空对象占据16 bytes
 *
 *         > 方法结束后,Stack中的引用变量马上被回收, 但是Heap中的对象要等到下一次GC来临后才回收
 *     }
 *
 */
public class GcTheory {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
