package jvm.clazz.loading;

import java.io.*;

@SuppressWarnings("all")
public class MyClassLoader extends ClassLoader {

    public static void main(String[] args) {
        testClassUnloading();
//        testNamespace();
    }

    /**
     * 命名空间:
     *     每个类的加载器都有自己的命名空间, 命名空间由该加载器及所有父加载器所加载的类组成
     *     同一个命名空间中, 不会出现类的完整名字相同的两个类
     *     不同的命名空间中, 有可能出现类的完整名字相同的两个类
     */
    private static void testNamespace() {}

    // 由jvm自带的类自带的类加载器所加载的类,在jvm的整个生命周期中,始终不会被卸载
    private static void testClassUnloading() {
        try {
            MyClassLoader classLoader1 = new MyClassLoader("loader1");
//            classLoader1 = new MyClassLoader(classLoader1, "loader1");
            classLoader1.setPath("/tmp/");
            Class<?> clazz = classLoader1.loadClass("jvm.clazz.loading.ClassFile");
            System.out.println("load: " + clazz);
            Object obj = clazz.newInstance();
            System.out.println("loaded: " + obj);
            System.out.println();
//            obj = clazz.newInstance();
//            System.out.println("loaded: " + obj);
//            System.out.println(obj.getClass().getClassLoader());
            // AppClassLoader
            // 此处将类加载的任务交给了当前类加载器的父亲

            classLoader1 = null;
            clazz        = null;
            obj          = null;
            System.gc();
            // [Unloading class jvm.clazz.loading.ClassFile 0x00000xxxxxxx(内存地址)]

            Thread.sleep(1000);
            System.out.println();
//            System.in.read();

            classLoader1 = new MyClassLoader("loader1");
            classLoader1.setPath("/home/allen/java-learn/java/target/");
            clazz = classLoader1.loadClass("jvm.clazz.loading.ClassFile");
            System.out.println("load: " + clazz);
            obj = clazz.newInstance();
            System.out.println("loaded: " + obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String classLoaderName;

    private String path = "";

    private final String fileExt = ".class";

    public MyClassLoader(String classLoaderName) {
        super();    // 默认SystemClassLoader
        this.classLoaderName = classLoaderName;
    }

    public MyClassLoader() {
    }

    public MyClassLoader(ClassLoader parent) {
        super(parent);
    }

    public MyClassLoader(ClassLoader parent, String classLoaderName) {
        super(parent);
        this.classLoaderName = classLoaderName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    /**
     * findClass并非被ClassLoader直接调用, 而是被ClassLoader.loadClass()调用
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = this.loadClassData(name);
        /**
         * defineClass:
         *     将字节数组转化为Class的实例, 这个实例被使用之前必须被解析
         *
         * 参数:
         *     name: binary name
         *     b: class数据, 必须符合jvm规范
         *     off:
         *     len:
         *
         * SecurityException:
         *     如果该类的签名与被添加进的package的签名不一致, 或者尝试定义一个以"java."开头的类, 会出现安全异常
         */
        return this.defineClass(name, data, 0, data.length);
    }

    private byte[] loadClassData(String name) {
        InputStream is = null;
        byte[] data = null;
        ByteArrayOutputStream baos = null;

        try {
            name = name.replace(".", "/");
            is = new FileInputStream(new File(this.path + name + this.fileExt));
            baos = new ByteArrayOutputStream();

            int ch = 0;
            while (-1 != (ch = is.read())) {
                baos.write(ch);
            }
            data = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }


}
