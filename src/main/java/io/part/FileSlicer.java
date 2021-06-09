package io.part;

import java.io.*;

/**
 * 文件分段
 */
public class FileSlicer {
    private String inputFilePath;
    private String outputFilePathPrefix;
    private int bufferSize;

    public FileSlicer(String inputFilePath, String outputFilePathPrefix, int bufferSize) {
        this.inputFilePath = inputFilePath;
        this.outputFilePathPrefix = outputFilePathPrefix;
        this.bufferSize = bufferSize;
    }

    public boolean split(int numOfSplits) {
        try {
            final File inputFile = new File(inputFilePath);
            final RandomAccessFile raf = new RandomAccessFile(inputFile, "r");
            long inputFileSize = raf.length();
            long perFileSize = inputFileSize / numOfSplits;
            long remainBytes = inputFileSize % numOfSplits;
            for (int i = 0; i < numOfSplits; i++) {
                String dstFilePath = this.getFileSliceName(inputFile, i);
                final BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dstFilePath));
                if (perFileSize > bufferSize) {
                    long numReads   = perFileSize / bufferSize;
                    long remainRead = perFileSize % bufferSize;
                    for (long l = 0; l < numReads; l++) {
                        readWrite(raf, bos, bufferSize);
                    }
                    if (remainRead > 0) {
                        readWrite(raf, bos, remainRead);
                    }
                } else {
                    readWrite(raf, bos, perFileSize);
                }
                bos.flush();
                bos.close();
            }
            if (remainBytes > 0) {
                final BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(this.getFileSliceName(inputFile, numOfSplits)));
                readWrite(raf, bos, remainBytes);
                bos.flush();
                bos.close();
            }
            raf.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getFileSliceName(File file, int i) {
        final String s = file.getParent() + "/" + this.outputFilePathPrefix + "part_" + (i + 1);
        System.out.println(s);
        return s;
    }

    public void readWrite(RandomAccessFile raf, BufferedOutputStream bos, long numBytes) {
        try {
            final byte[] buf = new byte[(int) numBytes];
            final int read = raf.read(buf);
            if (read != -1) {
                bos.write(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String file = "/home/allen/sdb3/win/office2019.iso";
        final FileSlicer slicer = new FileSlicer(file, "cn_office_", 1024 * 1024 * 100);
        final long s = System.currentTimeMillis();
        System.out.println("分割: " + file);
        final boolean split = slicer.split(7);
        final long e = System.currentTimeMillis();
        System.out.println("用时: " + ((e - s) / 1000) + " 秒");
    }
}
