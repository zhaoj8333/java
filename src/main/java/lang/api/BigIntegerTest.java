package lang.api;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BigIntegerTest {
    public static void main(String[] args) {
        // testBigInteger();
        // testBigDecimal1();
        testBigDecimal2();

    }

    private static void testBigDecimal2() {
        double a = 3.125;
        double b = 1.582;
        double c = a / b;
        System.out.println(c);

        Double a1 = new Double(3.125);
        Double b1 = new Double(1.582);
        Double c1 = a1 / b1;
        System.out.println(c1);

        BigDecimal a2 = new BigDecimal(3.125);
        BigDecimal b2 = new BigDecimal(1.582);
        BigDecimal c2 = a2.divide(b2, 20, BigDecimal.ROUND_DOWN);
        c2 = a2.divide(b2, 20, BigDecimal.ROUND_CEILING);
        System.out.println(c2);
        c2 = a2.divide(b2, 20, BigDecimal.ROUND_UP);
        System.out.println(c2);
    }

    private static void testBigDecimal1() {
        BigDecimal i = new BigDecimal("218433719832137891");
        System.out.println(i);
        BigDecimal j = new BigDecimal("212312.123153");
        System.out.println("add: " + i.add(j));
        System.out.println("multiply: " + i.multiply(j));
        System.out.println("substraction: " + i.subtract(j));

        System.out.println("quotient: " + i.divide(j, 2));
        // 除不尽，出现无限循环小数,可能会报错
        // java.lang.ArithmeticException: Non-terminating decimal expansion;
        // no exact representable decimal result
    }

    private static void testBigInteger() {
        Integer i = new Integer(10000);
        /**
         * java中特别大的数，不叫数字，叫对象
         */
        String s = "sdjkflasjfkljklsajflkjads";
        // number format lang.exception
        s = "182937189231283721893781293";
        BigInteger j = new BigInteger(s);
        BigInteger k = new BigInteger(s);

        System.out.println(i);
        System.out.println(j);

        BigInteger sum = j.add(k);
        System.out.println("sum: " + sum);

        BigInteger substraction = j.subtract(k);
        System.out.println("sum: " + substraction);

        BigInteger product = j.multiply(k);
        System.out.println("product:" + product);

        BigInteger quotient = j.divide(k);
        System.out.println("quotient: " + quotient);
    }
}
