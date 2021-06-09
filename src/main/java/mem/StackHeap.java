package mem;

public class StackHeap
{
    /**
     * 栈：
     *
     * 内存区域栈：
     *  栈存储局部变量，也是线程独有区域，每个线程都有自己的的独立的栈区域
     *
     * 先进后出，后进先出
     *  后进方法执行完后，栈被释放
     *
     */

    public static int initData = 111;

    /**
     * main执行前 栈中会分配main方法的栈帧，并存储stack局部变量，然后执行compute，栈
     * 又会分配compute的栈帧区域
     *
     * 方法出口：方法执行完后要出到 哪里
     * 当compute执行完成后，会根据方法出口中存储的相关信息回到main方法的相应位置
     *
     * 局部变量会放在局部变量表中，main中的stack会存入其中，但作为对象的stack
     * 是放在堆中的
     *
     * stack变量 与 对应的对象的区别
     *
     * 局部变量表中的stack变量是stack对象在堆中的内存地址
     */
    public static void main(java.lang.String[] args)
    {
        StackHeap stack = new StackHeap();
        System.out.println(stack); // Stack@4aa298b7
        System.out.println("============");
        stack.compute();
        System.out.println("test");
    }

    /**
     * 方法内部有自己的局部变量
     *
     * jvm为了区分不同方法中局部变量作用域范围的内存区域，每个方法在运行时都会分配独立的
     * 栈内存区域
     *
     */
    public int compute()
    {
        int a = 1;
        int b = 2;

        int c = (a + b) * 10;

        return c;
    }

    /**
     * 栈帧： compute()方法字节码为例
     *
     * 1. 局部变量表: 局部变量
     * 2. 操作数栈:
     * 3. 动态链接:
     * 4. 方法出口:
     *    进入compute方法前, 会将调用者的代码位置记录到compute栈帧中的 方法出口 处
     *
     * javap -c Stack
     *
     * 0: iconst_1 将int类型常量1 压入 操作数栈(操作数栈)
     * 1: istore_1 将int类型值存入 局部变量表 局部变量1
     *
     * 2: iconst_2 将int类型常量2 压入 操作数栈
     * 3: istore_2 将int类型值 存入局部变量表 局部变量2
     *
     * 4: iload_1  从局部变量1中 装载int类型值 a的值装载到操作数栈中
     * 5: iload_2  从局部变量2中 装载int类型值 b的值装载到操作数栈中
     *
     * 6: iadd     执行int类型的加法，会将操作数栈中的1,2 一次性从栈底弹出并相加，然后把结果3压入操作数栈底
     * 7: bipush 10 将一个8位带符号整数压入压入操作数栈
     * 9: imul      执行int类型乘法，将2，10出栈，结果30压入栈
     *
     * 10: istore_3 将int类型值存入局部变量3
     * 11: iload_3  从局部变量3中装载int类型值
     * 12: ireturn  返回int类型值
     *
     *
     * 程序计数器：
     *  程序计数器是线程私有区域，每个线程都会被分配计数器的内存。用来存放当前线程正在执行或即将执行的jvm指令码对应的位置。
     *  或行号位置。
     *  多线程下，多线程切换时，程序被挂起，线程恢复运行时使用程序计数器恢复。
     *
     * 方法区： jdk以后叫元空间, 位于直接内存
     *  常量
     *  静态变量
     *  类元信息
     *
     *      public static int initData = 123;
     *      静态变量存放于方法区
     *
     *      public static User user = new User();
     *      user变量放在方法区，new的user对象在堆中
     *
     * 栈中的局部变量；方法区中的静态变量
     *
     * 本地方法栈: native方法
     *     如果执行到本地方法,则该内存空间存放于本地方法栈中
     */

    /**
     * 对象(HotSpot虚拟机)：
     *
     *  存储结构：
     *
     * 1. 对象头(Header)
     *    MarkWord：存储对象资深运行时数据
     *        HashCode： jvm每new一个对象，都会将这个对象丢到一个hash表中，下次存取时能快速获取
     *        GC分代年龄：年轻代、老年代、永久代
     *        锁状态标志
     *        线程持有的锁
     *        偏向线程ID
     *        偏向时间戳
     *    Klass Pointer：klass类型指针，即对象指向它的类元数据的指针，虚拟机通过该指针来确定这个对象是哪个类的实例
     *        如果同一个类被new出了多个对象，所有对象的Klass Pointer都会指向该类
     *    数组长度（只有数组对象有），记录数组长度
     *
     * 2. 实例数据(Instance Data)： 指对象真正存储的有效信息，也是程序代码所定义的各种类型的字段内容。无论是自身的还是继承，还是子类定义的都会记录下来。
     *
     * 3. 对齐填充(Padding)
     *    没有特别含义，仅仅是占位符
     *    HotSpot要求对象起始地址必须是8bit的整数倍。因此当数据部分没有对齐时需要通过对齐填充来补全。
     *
     *
     */

    /**
     * 堆
     *
     * 年轻代：伊甸园区、survivor区(from，to)，默认占据1/3,
     *
     * 老年代：默认占据2/3
     *
     * GC
     *  Eden区会随着对象的不停实例化被填满，此时会触发gc
     *  比如：main方法结束，其方法栈帧被释放，局部变量被释放，但是局部变量中的堆中的对象依然存在，但是没有指针指向他，此时就是一个垃圾对象，就应该被回收。
     *
     * Minor GC: 年轻代的GC
     *     新对象在Eden区分配，如果Eden区放不下，jvm使用复制算法发生一次Minor GC，清除无用对象
     *     将存活对象移动到Survivor的其中一个区，from(s0)或to(s1)，
     *     如果suvivor放不下时，对象会被移到老年代
     *
     *     jvm会给每个对象一个对象年龄计数器，对象在survivor区每熬过一次GC，年龄+1
     *     待到年龄达到一定岁数（15），jvm将对象移动到老年代
     *
     * Major GC：Full GC
     *     老年代空间不够用，jvm使用“标记-清除”或“标记-整理”算法清理内存空间，分配对象使用
     *
     * OOM: java.lang.OutOfMemoryError
     *
     * 可达性分析：
     *     将gc roots跟对象作为起点，从该起点向下搜索，找到的对象为非垃圾对象，其余为垃圾对象。
     *     gc roots根是判断一个对象是否可以回收的依据
     *
     */
}
