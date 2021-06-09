package jvm.clazz.loading;

import jvm.Car;

/**
 * 类加载子系统:
 *     字节码文件 ->  类加载子系统(加载->链接->初始化)
 *     从文件系统或网络加载class文件, class文件在文件开头有特定标识
 *     ClassLoader只负责class文件加载, 至于是否可以运行,则有Execution Engine决定
 *     加载的类信息存放于方法区. 除了类信息外, 方法区还包含运行时常量池信息,字符串字面量和数字常量, 这部分常量是class文件中常量池部分的内存映射
 */
public class ClassLoading {
    public static void main(String[] args) {
        getClassLoader();
        loading();
    }

    public ClassLoading() {
        a = 100;
    }
    /**
     * 类加载过程:
     *     加载 loading
     *          通过类的全限定名获取此类的二进制字节流
     *              fs,网络,zip压缩包,运行时生成(动态代理),jsp文件生成,专有数据库,加密文件
     *
     *          将该字节流所代表的静态存储结构转化为内存方法区的运行时数据结构
     *          在内存中创建一个代表这个类的java.lang.Class对象,作为方法区这个类的各种数据访问入口
     *
     *     验证,准备,解析也叫链接阶段
     *      验证 verification
     *          确保class文件字节流包含信息符合jvm要求,保证被加载类正确性,不会威胁jvm自身安全
     *          文件格式验证
     *              CAFEBABE
     *          元数据验证
     *          字节码验证
     *          符号引用验证
     *
     *      准备 preparation: 为类变量分配内存并且设置该类变量的默认初始值,即 零值
     *          这里不包含用final修饰的static(即: 常量),因为final在编译的时候就会分配了,准备阶段会显示初始化
     *          这里不会为实例变量分配初始化,类变量会分配在方法区中,而实例变量是随着对象一起分配到java堆中
     *
     *      解析: resolution
     *          将常量池内的符号引用转换为直接引用的过程
     *          事实上,解析操作会伴随jvm执行完初始化后再执行
     *          符号引用就是一组描述所引用的目标. jvm规范中明确有定义. 直接饮用就是 直接指向目标的指针,相对偏移量或一个间接定位到目标的句柄
     *          解析动作主要针对类或接口,字段,类方法,接口方法,方法类型等
     *
     *     初始化: initialization
     *          执行类构造器<clinit>()的方法
     *          此方法不需要定义,是javac编译器自动收集类变量信息和静态代码块中的语句合并而来
     *
     *          clinit:
     */

    /**
     * 在准备阶段, a 的值为 0
     * 在初始化后, a 的值为 1
     */
    // 准备阶段变量赋值为 0
    private static int a = 1;
    static {
        // 初始化时执行
        a = 2;
        // b在prepare阶段已经加载到内存, 在此处初始化时可以进行对该变量进行操作
        b = 20;
        System.out.println("static a: " + a);
        // 2
//         System.out.println(b);  // 非法前向引用, b声明在后, 可赋值, 但不可调用
    }

    // prepare 阶段值为 0
    // initialization 阶段覆盖为 10
    private static int b = 10;

    private static void loading() {
        System.out.println("a: " + a);
        System.out.println("b: " + b);  // 10
    }

    private static void getClassLoader() {
        final ClassLoader classLoader = Car.class.getClassLoader();
        System.out.println(classLoader);
        // sun.misc.Launcher$AppClassLoader@18b4aac2
        // sun.misc.Launcher
    }
}
