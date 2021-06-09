package jvm.clazz.loading;

import java.lang.reflect.Method;

public class LoaderHierarchy {
    public static void main(String[] args) {
//        testPath();
//        testBootstrapLoader();
        testExtLoader();
//        testTwoCustomLoader();

    }

    private static void testTwoCustomLoader() {
        final MyClassLoader loader1 = new MyClassLoader("loader1");
        loader1.setPath("/tmp/");
        final MyClassLoader loader2 = new MyClassLoader("loader2");
        loader2.setPath("/tmp/");


        final Class<?> a;
        final Class<?> b;
        try {
            a = loader1.loadClass("jvm.clazz.loading.ClassFile");
            b = loader2.loadClass("jvm.clazz.loading.ClassFile");
            System.out.println(a == b);

            // true
            // 尽管由两个不同的加载器, 但是加载时都会委托给同一个父加载器对类进行加载, 所以不会重新加载, true
            //
            // 如果加载/tmp下的类, 因为只能由自定义类加载器加载, 两者之间没有任何关系, jvm内存中形成了两个命名空间, 所以为false
            // 两个命名空间互相独立, 因为每个类加载器都有自己的命名空间, 两个ClassFile并行存在于jvm的内存
            // a b 互相不可见, 无法互相访问

            final Object o  = a.newInstance();
            final Object o1 = b.newInstance();

            System.out.println("==============================");
            // false
            System.out.println(o == o1);
            final Method method = a.getMethod("setClassFile", Object.class);
            method.invoke(o, o1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ExtClassLoader
    private static void testExtLoader() {
//        final AESKeyGenerator aesKeyGenerator = new AESKeyGenerator();
//        final ClassLoader classLoader = aesKeyGenerator.getClass().getClassLoader();
//        System.out.println(classLoader);
        final ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemClassLoader);
        System.out.println("=====================");
        /*
        在方法第一次调用时, 如果java.system.class.loader系统属性被定义
         */
        System.out.println(System.getProperty("java.system.class.loader"));
        // null: 默认下, 该属性没有被定义
        // 自定义系统加载器: java -Djava.system.class.loader=jvm.clazz.loading.MyClassLoader


    }

    // 如何让Bootstrap类加载器 加载MyCat ?
    // 将类文件复制到 sun.boot.class.path属性锁指示的 /opt/jdk1.8.0_181/jre/classes/jvm/clazz/loading/ce目录下即可加载
    private static void testBootstrapLoader() {
        System.out.println(ClassLoader.class.getClassLoader());
        // null
//        System.out.println(Launcher.class.getClassLoader());
        // null Launcher类也是启动类加载, 扩展类和系统类加载类也是由启动类加载器加载的
        System.out.println("========================");

        final MyClassLoader loader1 = new MyClassLoader("loader1");
        loader1.setPath("/tmp/");
        try {
            final Class<?> aClass = loader1.loadClass("jvm.clazz.loading.ce.MyCat");
            System.out.println(aClass.hashCode());
            System.out.println(aClass.getClassLoader());
            // 此时为null,即Bootstrap加载器

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static void testPath() {
        System.out.println(System.getProperty("sun.boot.class.path"));
        // java -Dsun.boot.class.path=./如果修改错误, 会出现java/lang/NoClassDefError jvm启动时出错
        System.out.println(System.getProperty("java.ext.dirs"));
        System.out.println(System.getProperty("java.class.path"));
    }
}
