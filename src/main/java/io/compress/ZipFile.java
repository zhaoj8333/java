package io.compress;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 文件压缩
 */
public class ZipFile {
    private String fromPath;
    private String toPath;

    public ZipFile(String fromPath, String toPath) {
        this.fromPath = fromPath;
        this.toPath = toPath;
    }

    public void compressFile() {
        final File srcFile = new File(fromPath);
        if (! srcFile.isFile()) {
            System.err.println("源文件不存在");
            System.exit(1);
        }
        try {
            final FileInputStream fis = new FileInputStream(fromPath);
            final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(toPath));
            zos.putNextEntry(new ZipEntry(srcFile.getName()));
            int tmp;
            while ((tmp = fis.read()) != -1) {
                zos.write(tmp);
            }
            fis.close();
            zos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unCompressFile() {
        try {
            final File from = new File(fromPath);
            final byte[] bytes = new byte[1024];
            final ZipInputStream zis = new ZipInputStream(new FileInputStream(fromPath));
            final ZipEntry nextEntry = zis.getNextEntry();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void compressDir() {
        final File srcPath = new File(fromPath);
        if (! srcPath.isDirectory()) {
            System.out.println("源目录不存在");
            System.exit(1);
        }
        final File toFile = new File(toPath);
        InputStream input = null;
        try {
            final ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(toFile));
            final File[] files = srcPath.listFiles();
            assert files != null;
            for (File file : files) {
                input = new FileInputStream(file);
                String tmp = srcPath.getName() + File.separator + file.getName();
                System.out.println(tmp);
                zos.putNextEntry(new ZipEntry(tmp));
                int count = 0;
                while ((count = input.read()) != -1) {
                    zos.write(count);
                }
                input.close();
            }
            zos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        testCompress();
        testUnCompress();
    }

    private static void testUnCompress() {
//        String from = "/home/allen/sdb3/win/wallpapers.zip";
//        String to   = "/home/allen/sdb3/win/wallpapers";
        String from = "/home/allen/sdb3/win/pan.zip";
        String to   = "/home/allen/sdb3/win/pan.exe";
        final ZipFile zipFile = new ZipFile(from, to);
        zipFile.unCompressFile();

    }

    private static void testCompress() {
        String from = "/home/allen/sdb3/win/app/BaiduNetdisk_7.0.3.2.exe";
//        from = "/home/allen/sdb1/wallpapers";
        String to   = "/home/allen/sdb3/win/pan.zip";
        final ZipFile zipFile = new ZipFile(from, to);
        zipFile.compressFile();
//        zipFile.compressDir();
    }

}
