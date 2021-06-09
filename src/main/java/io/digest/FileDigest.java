package io.digest;

//import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 文件hash
 */
public class FileDigest {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        String path = "/home/allen/sdb3/win/cn_office_professional_plus_2019_x86_x64_dvd_5e5be643.iso";
        String hash = md5Hash(path);
        System.out.println(path + " hash: " + hash);
        hash = md5Hash32(path);
        System.out.println(path + " hash: " + hash);

        atOnce(path);

//        usingLib(path);
    }

    /**
     *  大文件会造成 OOM
     */
    private static void atOnce(String path) throws IOException, NoSuchAlgorithmException {
        byte[] b = Files.readAllBytes(Paths.get(path));
        byte[] hash = MessageDigest.getInstance("MD5").digest(b);
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            if ((hash[i] & 0xff) < 0x10) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(0xff & hash[i]));
        }
        System.out.println(path + " hash: " + sb.toString());
    }

    private static void usingLib(String path) throws IOException {
        final File file = new File(path);
        final FileInputStream fis;
        try {
            fis = new FileInputStream(file);
//            String hex = DigestUtils.sha1Hex(fis);
//            hex = DigestUtils.md5Hex(fis);
//            System.out.println(hex);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String md5Hash32(String filePath) {
        try {
            final FileInputStream fis = new FileInputStream(filePath);
            final MessageDigest md5 = MessageDigest.getInstance("MD5");
//            System.out.println(md5);
            // 分批次读取文件,防止内存溢出
            final byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes, 0, 1024)) != - 1) {
                md5.update(bytes, 0, length);
            }
            fis.close();
            byte[] md5byte = md5.digest();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < md5byte.length; i++) {
                int val = ((int) md5byte[i]) & 0xff;
                if (val < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(val));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | IOException e) {
        }
        return null;
    }

    public static String md5Hash(String filePath) {
        try {
            final FileInputStream fis = new FileInputStream(filePath);
            final MessageDigest md5 = MessageDigest.getInstance("MD5");
//            System.out.println(md5);
            // 分批次读取文件,防止内存溢出
            final byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes, 0, 1024)) != -1) {
                md5.update(bytes, 0, length);
            }
            fis.close();
            byte[] md5byte = md5.digest();
            final BigInteger bigInteger = new BigInteger(1, md5byte);

            return bigInteger.toString(16);

        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
