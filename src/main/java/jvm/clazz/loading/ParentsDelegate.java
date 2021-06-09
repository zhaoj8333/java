package jvm.clazz.loading;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Random;

@SuppressWarnings("all")
public class ParentsDelegate {
    public static void main(String[] args) {
//        loaderOfSystemClass();
//        loaderOfCustomClass();
//        finalLoadingByteCode();
//        test();
//        testSysloaderAndForname();
//        testParentsDelegate();
        testGetThreadContextLoader();
//        testReadClassFile();
//        testClassLoader();
//        classLoaderSrc();

    }

    /**
     * ClassLoader本身是一个对象, 该对象负责类的加载
     * binary names: JLS规范中定义的, 能被ClassLoader说别的字符串: java.lang.String, javax.swing.JSpinner$DefaultEditor
     *
     * 给定一个类的binary name, ClassLoader应该尝试定位和生成(如: 动态代理生成)类定义相关的数据, 典型的方式就是将该名称转化为文件名并从文件系统读取该class文件
     * 每一个Class对象都包含了定义该Class对象的ClassLoader引用
     *
     * >>> 对于数组类的Class对象,并不是由类加载器创建,而是由java运行时根据需要动态创建.
     *
     * 类加载器一般伴随着安全管理器
     *
     * ClassLoader的每一个实例都会有一个与之关联的父ClassLoader, 当被要求寻找资源或类时, ClassLoader实例会将该任务交给父ClassLoader
     * 层层网上递进,jvm内置的loader, 叫启动类加载器, 其本身没有双亲, 但是可以作为加载器的双亲
     *
     * 并发的类加载器: Class Loaders
     *     支持并发的类加载必须在类初始化时期通过调用ClassLoader.registerAsParallelCapable方法注册自己, 注册后该ClassLoader支持并发加载
     *     ClassLoader默认情况下就通过该方法注册, 然而,如果子类需要并行加载, 仍然需要手动注册
     *
     *     jvm从本地文件系统加载类是与平台相关的, unix中是通过CLASSPATH环境变量定义的目录加载
     *
     * 某些类可能会来自于网络或者程序自动构建(比如说: 动态代理)
     *     defineClass会将byte数组转换为一个Class类的实例,通过Class.newInstance()可以创建这种新定义的实例
     *
     * 由类加载器创建的对象的方法和构造方法还可能引用其他的类, 为了确定被引用的类,jvm必须调用创建该类的类加载器的loadClass方法
     *
     *
     */
    private static void classLoaderSrc() {
        // 对于数组来说,其类加载器与其中的元素的类加载器一样的
        String[] strs = new String[5];
        System.out.println(strs.getClass().getClassLoader());           // 此处null的含义为 Bootstrap启动类加载器

        FinalTest[] finalTests = new FinalTest[4];
        System.out.println(finalTests.getClass().getClassLoader());     // sun.misc.Launcher$AppClassLoader

        // 对于原始类型的数组, 该数组没有类加载器
        int[] ints = new int[5];
        System.out.println(ints.getClass().getClassLoader());   // 此处的null表示没有加载器

    }

    private static void testClassLoader() {
//        System.out.println(ParentsDelegate.class.getClassLoader());
//        System.out.println(Integer.class.getClassLoader());

    }

    private static void testReadClassFile() {
        final ClassLoader ccl = Thread.currentThread().getContextClassLoader();
        String path = "$Proxy0.class";
//        path = "/home/allen/sdb1/wallpapers/desktop.jpg";
        try {
            Enumeration<URL> urls = ccl.getResources(path);
            // System.out.println(urls);
            // System.out.println(urls.hasMoreElements());
            while (urls.hasMoreElements()) {
                final URL url = urls.nextElement();
                System.out.println(url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testGetThreadContextLoader() {
        System.out.println("class loader: " + ParentsDelegate.class.getClassLoader());
        // 线程上下文的ClassLoader的提供者为
        ClassLoader ccl = Thread.currentThread().getContextClassLoader();
        System.out.println("thread class loader: " + ccl);
        while (null != ccl) {
            ccl = ccl.getParent();
            System.out.println(ccl);
        }
    }

    private static void testParentsDelegate() {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        System.out.println(classLoader);

        while (null != classLoader) {
            classLoader = classLoader.getParent();
            System.out.println(classLoader);
        }
        // sun.misc.Launcher$AppClassLoader
        // sun.misc.Launcher$ExtClassLoader
        // null
    }

    /**
     * ClassLoader.loadClass
     * Class.forName
     *
     * 两者返回值相同, 都是类的Class对象
     * 但是前者不会导致类的初始化
     * 后者是通过反射获去类的类对象
     */
    private static void testSysloaderAndForname() {
        final ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        try {
            // loadClass并不会导致类的初始化
            final Class<?> parentClass = systemClassLoader.loadClass("jvm.clazz.loading.ChildClass");
            System.out.println(parentClass);

            System.out.println("--------------");
            // 反射会导致类的初始化:
            //     forName会导致类的初始化, 且会导致锁初始化类的父类的初始化
            final Class<?> clazz = Class.forName("jvm.clazz.loading.ChildClass");
            System.out.println(clazz);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void test() {
        ParentClass parentClass;            // 不是对ParentClass的主动使用
        System.out.println("-----------");
        parentClass = new ParentClass();    // 对ParentClass的主动使用
        System.out.println("-----------");
        System.out.println(parentClass.a1);
        System.out.println("-----------");
        System.out.println(ChildClass.b);   // child static block 对类的主动使用
    }

    static {
        System.out.println("static block");
    }

    // 类的初始化顺序: 父类的父类... -> 父类 -> 当前类
    private static void testInitOrder() {
        System.out.println(ChildClass.b);
        // 输出 static block
        // 输出 parent static block
        // 输出 child static block
        // 输出 ChildClass.b
    }

    /**
     * 以上方法的类加载顺序
     *
     * jvm.clazz.loading.ParentsDelegate
     * jvm.clazz.loading.ParentClass
     * jvm.clazz.loading.ChildClass
     */
    private static void testLoadingOrder() {}

    private static void finalLoadingByteCode() {
        System.out.println(FinalTest.x);
    }

    private static void  loaderOfCustomClass() {
        try {
            final Class<?> clazz = Class.forName("jvm.clazz.loading.A");
            final ClassLoader classLoader = clazz.getClassLoader();
            System.out.println(classLoader);
            // sun.misc.Launcher$AppClassLoader@18b4aac2
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void loaderOfSystemClass() {
        try {
            final Class<?> clazz = Class.forName("java.lang.String");
//            final Class<String> stringClass = String.class;

            final ClassLoader classLoader = clazz.getClassLoader();
            System.out.println(classLoader);
            // 某些实现使用null 代表当前类被bootstra加载器加载
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class FinalTest {
//    public static final int x = 3;
//    iconst_3 将3从常量池中推送到栈顶

//    public static int x = 3;

    public static final int x = new Random().nextInt(100);
    // getstatic #3 Field FinalTest.x:I
    // 所以此时FinalTest会被初始化, 静态代码块会被执行

    static {
        System.out.println("FinalTest static block");
    }
}

class ParentClass {
    static int a = 3;
    int a1 = 4;
    static {
        System.out.println("parent static block");
    }
}

class ChildClass extends ParentClass {
    static int b = 4;
    int b1 = 5;
    static {
        System.out.println("child static block");
    }
}

