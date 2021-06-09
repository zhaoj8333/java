package jvm.clazz.loading.ce;

import jvm.clazz.loading.MyClassLoader;

public class ComplicateSituations {
    public static void main(String[] args) {
        try {
            MyClassLoader classLoader = new MyClassLoader("loader1");
            classLoader.setPath("/tmp/");
            Class<?> clazz = classLoader.loadClass("jvm.clazz.loading.ce.MySample");
            System.out.println(clazz);
            // 以上代码可以正常执行

            Object obj = clazz.newInstance();
            System.out.println();

            // 加载类:
            //     无论有没有static MyCat mycat = new MyCat(), MySample都会被加载, 但是MyCat没有被加载

            /**
             * 删除MyCat, 保留MySample
             * MySample由应用加载器正常加载,MyCat加载时找不到类定义的class文件
             * MyCat由加载了MySample的加载器尝试加载MyCat,应用加载器无法加载MyCat
             *
             * NoClassDefFoundError
             * ClassNotFoundException
             *
             * 删除MySample, 保留MyCat
             * 正常执行, MySample加载器为MyClassLoader
             * MyCat加载器为AppClassLoader: 开始由MyClassLoader加载, 但是委托给了父加载器AppClassLoader,而当前目录中又该类文件
             */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


