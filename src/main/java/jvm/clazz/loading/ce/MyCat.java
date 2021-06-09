package jvm.clazz.loading.ce;

public class MyCat {
    public MyCat() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        System.out.println("MyCat is loaded by    : " + classLoader);
        // 命名空间由该加载器以及所有父加载器所加载的类组成
        // 如果MySample加载器为MyClassLoader, 而MyCat加载器为AppClassLoader, 则NoClassDefFoundError, ClassNotFoundException

        // 父加载器的类中看不到子加载器所加载的类
        // 而子加载器所加载的类可以看到父加载器锁加载的类
        // System.out.println(MySample.class);
    }
}
