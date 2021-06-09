package perfm;

import java.util.Random;

/**
 * Principle of performance testing:
 *     1. Test a Real Application
 *      (1). Microbenchmark: designed to test a very small unit of performance
 */
public class PerformanceTesting {
    private static int loop = 2;

    public static void main(String[] args) {
        fibTest();
    }

    /**
     * @// TODO: 1/3/21
     * Problems of this code:
     *     1. It never actually changes any program state, Because the result of Fibonacci calculation is never used, so the Fib function can be discarded;
     *     2. Changing the definition of l from local variable to an instance variable will allow the performance of the method to be measured;
     *        The need to use a volatile variable applies even when the microbenchmark is single-threaded
     *
     *     3. Microbenchmarks must not inclde extraneous operations
     *        Compiler can be smart enough to figure the FiB out and execute the loop only once;
     *        A range of input values must be considered, and that has to be done carefully;
     *
     */
    private static void fibTest() {
        double l;
        long then = System.currentTimeMillis();
        System.out.println(new Random().nextInt());
        for (int i = 0; i < loop; i++) {
//            l = fibImpl1(50);
            l = fibImpl1((new Random().nextInt()));
        }
        long now = System.currentTimeMillis();
        System.out.println("Elapsed time: " + (now - then));
    }

    private static double fibImpl1(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("must be > 0");
        }
        if (n == 0) {
            return 0d;
        }
        if (n == 1) {
            return 1d;
        }
        double d = fibImpl1(n - 2) + fibImpl1(n - 1);
        if (Double.isInfinite(d)) {
            throw new ArithmeticException("Overflow");
        }
        return d;
    }
}
