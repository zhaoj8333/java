package jvm.exector;

// -Xms500m -Xmx500m -XX:+DoEscapeAnalysis
public class StackAllocate {
    public static void main(String[] args) {
        final long s = System.currentTimeMillis();

        for (int i = 0; i < 1000000; i++) {
            alloc();
        }

        final long e = System.currentTimeMillis();
        System.out.println(e - s);
    }

    private static void alloc() {
        final StackAllocate obj = new StackAllocate();
    }
}
