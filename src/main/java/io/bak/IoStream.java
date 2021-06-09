package io.bak;

import java.io.*;

public class IoStream {
    private static String file = "/home/allen/java-learn/java/io.files";
    public static void main(String[] args) throws IOException {
//        demo1();
//        demo2();

        // inputstream
//        demo03();
//        demo4();
        demo5();
    }

    private static void demo5() throws IOException {
        FileInputStream fis = getFis(file + "/FileOutputStream.txt");
        FileOutputStream fos = new FileOutputStream(file + "/FileOoutputSteam-Copy.txt", false);
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = fis.read(bytes)) != -1) {
            fos.write(bytes, 0, len);
        }
        System.out.println("done");
    }

    private static void demo4() throws IOException {
        FileInputStream fis = getFis(file + "/FileOutputStream.txt");
        byte[] bs = new byte[2];
        int read = fis.read(bs);
        read = fis.read(bs);
        read = fis.read(bs);
        read = fis.read(bs);
        read = fis.read(bs);
        read = fis.read(bs);
        System.out.println(new String(bs));
        read = fis.read(bs);
        System.out.println(read);
        System.out.println(new String(bs));

        fis.close();
    }

    private static void demo03() throws IOException {
        FileInputStream fis = getFis(file + "/FileOutputStream.txt");
        // 读取一个字符
//        int read = fis.read();
//        read = fis.read();
//        read = fis.read();

//        int read = fis.read();
//        System.out.println((char)read);
        int len = 0;
        while ((len = fis.read()) != -1) {
            System.out.print((char)len + "  ");
            System.out.println(len);
        }

        fis.close();
    }

    private static FileInputStream getFis(String file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

    private static void demo2() throws IOException {
        FileOutputStream fos = getFOS(true);
        // IO流调用的是系统读写，本身没有读写功能
        fos.write("系统读写\n".getBytes());
        fos.write("AAAA\n".getBytes());
    }

    private static void demo1() throws IOException {
        // 保存数据到硬盘: IO流
        FileOutputStream fos = getFOS(true);
        byte[] bs = {97, 98, 99, 100};
        fos.write(bs);

        fos.close();
    }

    private static FileOutputStream getFOS(boolean append) {
        // 自从创建
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("/home/allen/java-learn/java/io.files/FileOutputStream.txt", append);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return fos;
    }
}
