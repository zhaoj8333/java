package jvm.thrd;

import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("all")
public class CpuHigh {

    private static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {
//        detectProblem();
        detectDeadLock();
    }

    static String a = new String();
    static String b = new String();

    private static void detectDeadLock() {
        new Thread(() -> {
            synchronized (a) {
//                while (true) {
//                    counter.getAndIncrement();
//                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b) {
                    System.out.println("a b");
                }
            }
        }).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            synchronized (b) {
                synchronized (a) {
                    System.out.println("b a");
                }
            }
        }).start();
    }

    private static void detectProblem() {
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    counter.getAndIncrement();
                }
            }
        };

        final Thread t1 = new Thread(r);
        final Thread t2 = new Thread(r);
        t1.start();
        t2.start();
    }

}
