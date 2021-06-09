package io.part;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

public class FileMerger {
    private List<File> list = new ArrayList<>();
    private String inputMergerFilePath;
    private String outputMergerilePath;
    private int bufferSize;

    public FileMerger(String inputMergerFilePath, String outputMergerilePath, int bufferSize) {
        this.inputMergerFilePath = inputMergerFilePath;
        this.outputMergerilePath = outputMergerilePath;
        this.bufferSize = bufferSize;
    }

    public void beforeMerge() {
        final File fromPath = new File(inputMergerFilePath);
//        System.out.println(fromPath.isDirectory());
        if (! fromPath.isDirectory()) {
            System.err.println("合并文件必须为目录, exiting ...");
            System.exit(1);
        }
        final File[] files = fromPath.listFiles();
        assert files != null;
        list.addAll(Arrays.asList(files));
        sortFileList(list);
    }

    public boolean merge() {
        beforeMerge();
        final File outFile = new File(outputMergerilePath);
        try {
            FileOutputStream fos = new FileOutputStream(outFile, true);
            for (File file : list) {
                System.out.println("merging: " + file.getAbsolutePath());
                long sumByte = 0;
                FileInputStream fis = new FileInputStream(file);
                if (file.length() > bufferSize) {
                    long numReads = file.length() / bufferSize;
                    long remains  = file.length() % bufferSize;
                    for (long i = 0; i < numReads; i++) {
                        sumByte += readWrite(fis, fos, bufferSize);
                    }
                    if (remains > 0) {
                        sumByte += readWrite(fis, fos, remains);
                    }
                } else {
                    sumByte += readWrite(fis, fos, file.length());
                }
                assert(sumByte == file.length());
                fis.close();
            }
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public long readWrite(FileInputStream fis, FileOutputStream fos, long numBytes) {
        try {
            final byte[] buf = new byte[(int) numBytes];
            final int read = fis.read(buf, 0, (int) numBytes);
            if (read != -1) {
                fos.write(buf);
            }
            fos.flush();
            return read;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public void sortFileList(List<File> fileList) {
        fileList.sort((a, b)->{
            final String aName = a.getName();
            final String bName = b.getName();
            return aName.compareTo(bName);
        });
    }

    public static void main(String[] args) {
        final Instant start = Instant.now();
        String from = "/home/allen/sdb3/win/office_slice";
        String to   = "/home/allen/sdb3/win/office_merged.iso";
        final FileMerger merger = new FileMerger(from, to, 1024 * 1024 * 10);
        final boolean merge = merger.merge();

        final Instant end = Instant.now();
        System.out.println("耗时: " + Duration.between(start, end).getSeconds());
    }
}
