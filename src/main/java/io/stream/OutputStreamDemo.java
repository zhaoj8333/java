package io.stream;

import java.io.*;

/**
 * 字节数出流：所有表示字节输出流的超类，将指定的字节信息写出到目的地
 *
 * ParentInterface:
 *  Closeable, Flushable, AutoCloseable
 *
 * subclass:
 *  ByteArrayOutputStream
 *  FileOutputStream
 *  FilterOutputStream
 *  ObjectOutputStream
 *  PipedOutputStream
 */
public class OutputStreamDemo {
    public static void main(String[] args) {
        OutputStreamDemo osd = new OutputStreamDemo();
//        osd.fileOutput1();
//        osd.fileOutput2();
        osd.fileOutput3();

    }

    public void fileOutput3() {
        try {
            File file = new File("io/files/io/files/3.txt");
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            String content = "数据库了的撒酒疯";
            fileOutputStream.write(content.getBytes(), 10, 2);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fileOutput2() {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File("io/files/io/files/2.txt"));
//            fos.write(65);
            byte[] b = new byte[8];
//            b = new byte[] {-49, -48, -48, 68, 69};
//            b = new byte[] {-65, -66, -67, 68, 69};
            String content = "abcdefg\n";
            for (int i = 0; i < content.length(); i++) {
//                StdOut.println(content.charAt(i));
                b[i] = (byte) content.charAt(i);
            }
            fos.write(b);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fileOutput1() {
        String path = "io/files/io/files/1.txt";
        try {
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(80);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
