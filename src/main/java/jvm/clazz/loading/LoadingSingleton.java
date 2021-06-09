package jvm.clazz.loading;

@SuppressWarnings("all")
public class LoadingSingleton {
    public static void main(String[] args) {
        testSingleton1();
        System.out.println("================");
        testSingleton();
    }

    private static void testSingleton() {
        final Singleton singleton = Singleton.getSingleton();
        System.out.println(Singleton.counter);  //
        System.out.println(Singleton.counter1); // 0
    }

    private static void testSingleton1() {
        final Singleton1 singleton = Singleton1.getSingleton();
        System.out.println(Singleton1.counter);  // 1
        System.out.println(Singleton1.counter1); // 1
    }
}

class Singleton {
    public static int counter = 1;
    // 此处会调用构造方法
    private static Singleton singleton = new Singleton();
    // 是从上往下执行, 此处counter1 值在初始准备阶段后被覆盖掉了
    public static int counter1 = 0;

    /**
     * 以下counter, counter1使用的都是 加载准备阶段 之后的值, 即 0, 而不是初始化之后的值
     */
    private Singleton() {
        counter ++;
        counter1 ++;
        // System.out.println(counter + " -- " + counter1);
    }

    public static Singleton getSingleton() {
        return singleton;
    }
}


class Singleton1 {
    public static int counter;
    public static int counter1 = 0;

    private static Singleton1 singleton1 = new Singleton1();

    private Singleton1() {
        counter ++;
        counter1 ++;
    }

    public static Singleton1 getSingleton() {
        return singleton1;
    }
}
