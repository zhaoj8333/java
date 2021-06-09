package lang.reflection.d1;

public class ClassLoaderDemo {
    public static void main(String[] args) {
        // 获取类加载器
        getLoader();
    }

    private static void getLoader() {
        // BootstrapClassLoader: 加载核心类,由c语言编写
        // ExtClassLoader: 加载扩展类
        // AppClassLoader: 加载自定义类

        ClassLoader cl = ClassLoaderDemo.class.getClassLoader();
        System.out.println(cl);
//        System.out.println(cl.getParent());

//        System.out.println();

//        ClassLoader cld = DNSNameService.class.getClassLoader();
//        System.out.println(cld);
//        System.out.println(cld.getParent());

        ClassLoader clc = String.class.getClassLoader();
//        System.out.println(clc); // null

//        System.out.println("".getClass()
//        );

//        System.out.println(int.class);
//        System.out.println(String.class);

//        Class c = ClassLoaderDemo.class;
//        System.out.println(c);

    }
}
