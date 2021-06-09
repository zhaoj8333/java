package io.bak;

import java.io.*;
import java.util.Arrays;
import java.util.Properties;

public class CharsStream {
    public static void main(String[] args) throws IOException {
//        demo1();
//        demo2();
//        demo03();
//        demo04();
//        demo05();
        demo06();
    }

    /**
     * ResourceBundle
     */
    private static void demo06() {

    }

    private static void demo05() throws IOException {
        FileInputStream fis = new FileInputStream("io/files/prop/db.properties");
        Properties props = new Properties();
        props.load(fis);
        // properties默认为gbk编码
        System.out.println(props);
        System.out.println("host:   " + props.getProperty("host"));
        System.out.println("uname:  " + props.getProperty("username"));
        System.out.println("passwd: " + props.getProperty("password"));
        System.out.println("code:   " + props.getProperty("code"));


    }

    private static void demo04() {
        Properties props = new Properties();
        props.setProperty("a", "AAAAA");
        props.setProperty("b", "BBBBB");

        System.out.println(props);
        System.out.println("propkey: " + props.stringPropertyNames());
    }

    /**
     * FileWriter 自带缓冲区
     */
    private static void demo03() throws IOException {
        FileWriter fw = new FileWriter("io/files/tiny/write.txt", true);
//        fw.write(98);
//        fw.write("aaaaaaa");
        fw.write(new char[]{'a', 'b', 'c', 'd'});
//        fw.flush();
        fw.close();
    }

    /**
     * 字符流操作 多字节文本
     */
    private static void demo2() throws IOException {
        FileReader fr = new FileReader("io/files/tiny/cn.txt");
        int len = 0;
//        while ((len = fr.read()) != -1) {
//            System.out.println((char) fr.read());
//        }

//        fr.reset(); reset不支持
//        System.out.println("reset not supported");
//        while ((len = fr.read()) != -1) {
//            System.out.println((char) fr.read());
//        }
        char[] buff = new char[10];
        while ((len = fr.read(buff)) != -1) {
            System.out.println(Arrays.toString(buff));
        }
    }

    /**
     * @throws IOException
     *
     * 字节流
     */
    private static void demo1() throws IOException {
        FileInputStream fis = new FileInputStream("io/files/tiny/cn.txt");
        byte[] bs = new byte[3];
        int len = 0;
        while ((len = fis.read(bs)) != -1) {
            System.out.println(new String(bs, 0, len));
        }
        fis.close();
    }


}
