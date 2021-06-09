package jvm.exector;

public class ExecutionMode {
    public static void main(String[] args) {
        final long s = System.currentTimeMillis();
        test1(100000);
        final long e = System.currentTimeMillis();
        System.out.println((e - s) + " ms");
        while (true);
    }

    public static void test1(int n) {

    }
}
