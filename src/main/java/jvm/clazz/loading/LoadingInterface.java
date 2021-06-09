package jvm.clazz.loading;

import java.util.Random;

/**
 * 类初始化时, 要求其父类必须被初始化
 * 接口初始化时, 并不要求其接口被初始化
 */
@SuppressWarnings("all")
public class LoadingInterface {
    public static void main(String[] args) {
//        testInterfaceStatic();
//        testClassStatic();
        testInterfaceThread();
    }

    private static void testInterfaceThread() {
//        System.out.println(Descendent.b);
//        System.out.println(Descendent.c);
//        System.out.println(Descendent.d);
//        System.out.println(Descendent.thread);

    }

    private static void testClassStatic() {
        new C();
        System.out.println();
        new C();
    }

    /**
     * 当一个接口在初始化时, 并不要求其父接口完成了初始化
     * 将父接口class文件删除,依然可运行用
     */
    private static void testInterfaceStatic() {
//        System.out.println(Ancestor.a);
//        System.out.println(Descendent.b);
        // 光有以上代码, 不会对Ancestor, Descendent进行加载
        // can not assign a value to final variable a
        // Ancestor.a = 10; // 不能对final赋值

        System.out.println(Ancestor.c);
        System.out.println(Descendent.c);
        // 以上代码会加载两个接口
    }
}

/**
 * 接口本身中的成员都是final的
 *
 * 当一个接口在初始化时, 并不要求其父接口都完成了初始化
 * 只有在真正用到父接口的时候(如引用接口中的定义的常量时), 才会初始化
 *
 * 而class则相反, 一个类初始化时,其所有父类必须都进行了初始化
 */
@SuppressWarnings("all")
interface Ancestor {
    public static final int a = 1;

    // 此处 c在运行期才能确定具体的值
    public static final int c = new Random().nextInt(1000);

    public static Thread thread = new Thread() {
        {
            System.out.println("Ancestor another thread");
        }
    };
}

@SuppressWarnings("all")
class Descendent implements Ancestor {
    public static final int b = 1;

    // 此处 b在运行期才能确定具体的值
    public static final int c = new Random().nextInt(1000);

    public static int d = 3;

    public static Thread thread = new Thread() {
        {
            System.out.println("Descendent another thread");
        }
    };
}

@SuppressWarnings("all")
class C {
    // 非static代码块, C的实例每次被创建时,都会被执行
    // 实例代码块
    {
        System.out.println("loading");
    }

    public C() {
        System.out.println("constructing");
    }
}
