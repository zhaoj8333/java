package io.bak;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.util.ArrayList;

public class FileDemo {

    private static String path = "/home/allen/java-learn/java/io.files";
    private static ArrayList<String> pathes = new ArrayList<>();

    public static void main(String[] args) throws IOException {
//        demo1();
//        demo2();

        listFiles(path);
        for (String path : pathes) {
            System.out.println(path);
        }
    }

    private static void listFiles(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file1.isFile()) {
                pathes.add(file1.getAbsolutePath());
            } else if (file1.isDirectory()) {
                listFiles(file1.getAbsolutePath());
            }
        }
    }

    private static void demo2() throws IOException {
        File file = new File(path + "/1Kints.txt");
        boolean creat = file.createNewFile();
        System.out.println(creat);
        boolean mkd = (new File(path + "/small")).mkdir();
        System.out.println(mkd);
        boolean tiny = (new File(path + "/tiny")).mkdir();
        System.out.println(tiny);
    }

    private static void demo1() {
        //        File file = new File(path + "/1Kints.txt");
//        System.out.println("is File: " + file.isFile());

        /**
         * 名称分隔符
         * 路径分隔符： 环境变量的分隔符
         */
//        System.out.println(File.separator);
//        System.out.println(File.pathSeparator);
//        System.out.println("path: " + file);
//        System.out.println("name: " + file.getName());
//        System.out.println("parent: " + file.getParent());

//        File file = new File("/home/allen/java-learn/java/io.files", "/1Kints.txt");
//
//        System.out.println(file);
//        System.out.println("name: " + file.getName());
//        System.out.println("parent: " + file.getParent());
//        System.out.println(file.getAbsoluteFile());
//        System.out.println(f.getParentFile());

        File file = new File("io/files/1Kints.txt");
        System.out.println(file.isFile());
        System.out.println(FileSystem.class);
    }


}
