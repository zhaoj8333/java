package io;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

/**
 * java把文件和目录封装成了一个File类
 * File类与os无关
 */
public class FileDemo {

    public static void main(String[] args) {
        FileDemo fd = new FileDemo();
//        fd.construct();
        fd.search(new File("/home/allen/sdb2/book/db/MySQL"), "nw");
    }

   public void search(File dir, String filtered) {
//        StdOut.println(dir);
        File[] files = dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().contains("Performance");
            }
        });
        files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return true;
            }
        });
        for (File f : files) {
            if (f.isDirectory()) {
                search(f, filtered);
            } else {
//                StdOut.println(f);
            }
        }
    }

    public void construct() {
        File f1 = new File("io/files/large/largeT.txt");
//        StdOut.println(f1);
//        StdOut.println("absolute path: " + f1.getAbsolutePath());
//        StdOut.println("path         : " + f1.getPath());
//        StdOut.println("name         : " + f1.getName());
//        StdOut.println("length       : " + f1.length());

        File f2 = new File("/");
//        StdOut.println(f2);

        File f3 = new File("/", "home/allen");
//        StdOut.println(f3);

        File f4 = new File(f3, "sdb2");
//        StdOut.println(f4);

//        String link = "https://m1-1253159997.image.myqcloud.com/imageDir/03ff5d3d2c851e28b0b0f2ce310b4b8e.jpg";
//        URI uri = URI.create(link);
//        File f5 = new File(uri);
//        StdOut.println(f5);

    }

}
