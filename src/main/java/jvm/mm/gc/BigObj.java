package jvm.mm.gc;

import java.util.ArrayList;

// -Xms20M -Xmx20M -Xmn10M -XX:+UseSerialGC -XX:+PrintGCDetails -verbose:gc
@SuppressWarnings("all")
public class BigObj {
    public static void main(String[] args) {
        bigobjOld();
    }

    /**
     * 一个线程导致OOM, 并不一定会导致另一线程oom
     */
    private static void bigobjOld() {
        new Thread(() -> {
            final ArrayList<byte[]> list = new ArrayList<>();
            list.add(new byte[1024 * 1024 * 8]);
            list.add(new byte[1024 * 1024 * 8]);
        }).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main over");
    }
}
