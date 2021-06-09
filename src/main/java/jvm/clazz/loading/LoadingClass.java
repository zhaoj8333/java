package jvm.clazz.loading;

import java.util.UUID;

@SuppressWarnings("all")
public class LoadingClass {
    public static void main(String[] args) {
//        testActiveUsingOfClass1();
//        testActiveUsingOfClass2();
//        testFinalStatic();
//        testStaticUnknownStr();
//        testInitWhenNew();


    }

    /**
     * new Class时, 会导致该类被初始化
     * 且只会初始化一次
     *
     * 创建引用类型的数组并不会导致对该引用类型类的初始化
     */
    private static void testInitWhenNew() {
//        final Parent parent = new Parent();
//        System.out.println("---------------");
//        final Parent parent1 = new Parent();

        /*
         * 此处并没有导致类的初始化, 这行代码不是主动对类的使用
         * 此处不是Parent实例, 而是数组实例
         *
         * iconst_3
         * anewarray 表示创建一个引用类型的数组,并将其引用值压入栈顶
         */
        Parent[] parents = new Parent[3];
        // 以下这个类型是jvm在运行期生成的
        System.out.println(parents.getClass());
        // [L

        /**
         * iconst_2
         * iconst_2
         * multianewarray
         */
        final Parent[][] parents1 = new Parent[2][2];
        System.out.println(parents1.getClass());
        // [[L
        System.out.println(parents1.getClass().getSuperclass());
        // java.lang.Object
        // 数组实例其类型是jvm在运行器动态生成的表示为[L 的形式, 动态生成的类型, 符类型就是Object
        // 对于数组, javaDoc经常将构成数组的元素为Component 实际上就是将数组降低一个维度后的类型

        System.out.println("=============================");
        // iconst_1
        // newarray 10 (int) 创建原始类型的数组
        int[] ints = new int[1];
        System.out.println(ints.getClass()); // [I(Integer)
        System.out.println(ints.getClass().getSuperclass());    // Object

        System.out.println("----");
        // iconst_2
        // iconst_2
        // multianewarray
        int[][] ints1 = new int[2][2];
        System.out.println(ints1.getClass());

        char[] chars = new char[2];
        System.out.println(chars.getClass());   // [C

        short[] shorts = new short[2];
        System.out.println(shorts.getClass());

        byte[] bytes = new byte[2];
        System.out.println(bytes.getClass());
    }

    /**
     * Parent的静态代码块还是输出了
     */
    private static void testStaticUnknownStr() {
//        System.out.println(Parent.i);
        System.out.println(Parent.uuid);
    }

    /**
     * final本身为常量, 编译阶段常量会被存入到调用该常量的方法所在的类的常量池中
     * 本质上,调用类并没有直接引用到定义该常量的类, 因此并不会触发定义常量的初始化
     * 甚至可以将Parent.class文件删除
     */
    private static void testFinalStatic() {
        System.out.println(Parent.finalStr);
        // ldc #4  // String final str
        System.out.println(Parent.s);
        System.out.println(Parent.i);
        // ldc
        // final str
        // 静态代码块 未执行
        System.gc();
    }

    /**
     * 主动对子类的使用:
     *     初始化一个类的子类会导致优先对其所有父类的使用,
     *
     * 一个类在初始化时, 要求其父类全部都已经初始化完毕
     */
    private static void testActiveUsingOfClass2() {
        System.out.println(Child.str2);
        // 子类的静态代码块被执行
        // static: I am Parent
        // static: I am Child
        // welcome
    }

    /**
     * 对于静态字段来说,静态字段被使用时, 只有直接定义了该字段的类才会被初始化
     *
     * 但是该类仍然会被加载
     * -XX:+TraceClassLoading
     *
     * [Loaded jvm.clazz.loading.LoadingClassUsage from file:/home/allen/java-learn/java/target/classes/]
     * [Loaded sun.nio.cs.US_ASCII$Decoder from /opt/jdk1.8.0_181/jre/lib/rt.jar]
     * [[Loaded jvm.clazz.loading.Parent from file:/home/allen/java-learn/java/target/classes/]
     * [[Loaded jvm.clazz.loading.Child from file:/home/allen/java-learn/java/target/classes/]
     * static: I am Parent init
     * Hello
     * [[Loaded java.lang.Shutdown from /opt/jdk1.8.0_181/jre/lib/rt.jar]
     * [[Loaded java.lang.Shutdown$Lock from /opt/jdk1.8.0_181/jre/lib/rt.jar]
     *
     */
    private static void testActiveUsingOfClass1() {
        System.out.println(Child.str);
        // static: I am Parent
        // Hello
        // 此处并没有主动使用Child, 没有被初始化, 也不会执行static块
        // 子类的静态代码块没有执行
    }
}

class Parent {

    public static String str = "Hello";

    /**
     * 常量编译时会被存入到调用者所在方法所在类的常量池中
     * ldc会将int, float, 或String常量值 从常量池中推送至栈顶
     * bipush 将单字节(-128 ~ 127)的常量值推入栈顶
     * sipush 超过127时表示将short(-32768 ~ 32767)推入栈顶
     * iconst_1 表示int 1推至栈顶
     */
    public static final String finalStr = "final str";
    public static final byte s = 1;
    public static final int i = 10009123;
    // 本质上uuid是一个常量, 从生命角度看, 与i一样, 都是常量
    // 与i相反, uuid的值在编译器是无法确定的
    // 所以程序运行时, 会导致主动使用这个常量所在的类, 显然, 该类会被初始化
    public static final String uuid = UUID.randomUUID().toString();

    static {
        System.out.println("static: I am Parent init");
    }
}

class Child extends Parent {
    public static String str2 = "welcome";

    static {
        System.out.println("static: I am Child init");
    }

}
