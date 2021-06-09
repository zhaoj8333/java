package lang.ds.number;

public class Overflow
{
    /**
     * java int: 4个字节，每个字节8位,字节最高位表示符号正负，0代表正，1代表负数,符号位也参与运算
     *
     * Integer.MAX_VALUE
     *
     *
     */
    public static void main(String[] args)
    {
//        maxOverflow();
        integerOverflow();
    }

    private static void integerOverflow() {
        byte b = (byte)130;
        // 1000 0010 -> 1000 0001 -> (原) 1111 1110
        // 128 64 32 16 8 4 2 1
        //  -  1  1  1  1 1 1 0
        //  - 64 + 32 + 30
        //  - 126
        System.out.println(Integer.toBinaryString(b));

        System.out.println();
        System.out.println("byteMax: " + Byte.MAX_VALUE);
        System.out.println("byteMin: " + Byte.MIN_VALUE);
        System.out.println(b);
        System.out.println();

        byte c = (byte)300;
        System.out.println(c);
    }

    private static void maxOverflow() {
        // System.out.println(Integer.MAX_VALUE);  // 2147483647
        // System.out.println(Integer.toBinaryString(Integer.MAX_VALUE)); // 01111111 11111111 11111111 11111111
        // System.out.println(Integer.MAX_VALUE + 1); // -2147483648
        // System.out.println(Integer.toBinaryString(Integer.MAX_VALUE + 1)); // 10000000 00000000 00000000 00000000
        // // 以上表示-0，没有意义，
        // // 使用补码消除+0，,-0的冗余，充分利用
        // // 所以就用这个结果把补码的表示范围扩大一位，使其表达为最小值，而且这个值能满足运算的结果表示
        // // 不让它表示0，那就让它表示-2147483648（-2147483647 + （-1））

        // // 是想避免这种情况的发生，在程序中就必须加上数值范围的检查功能，
        // // 或者使用较大的表示范围的数据类型，如长整型。
        int x = Integer.MAX_VALUE;
        // System.out.println("x = " + x);
        // System.out.println("x + 1 = " + (x + 1));
        // System.out.println("x + 2 = " + (x + 2));

        // System.out.println("x + 1 = " + (x + 1L));
        // System.out.println("x + 2 = " + (x + 2L));
        // System.out.println("x + 3 = " + ((long)x + 3));

        boolean overFlow = willAdditionOverflow(1, x);

        System.out.println(overFlow);
    }

    public static boolean willAdditionOverflow(int left, int right)
    {
        if (right < 0 && right != Integer.MIN_VALUE) {
            return willSubstractionOverflow(left, -right);
        } else {
            // System.out.println("left : " + java.lang.Integer.toBinaryString(left));
            // System.out.println("right: " + java.lang.Integer.toBinaryString(right));
            System.out.println("left : " + left);
            System.out.println("right: " + right);
            System.out.println();

            /**
             * left:  00000000 00000000 00000000 00000001
             * right: 01111111 11111111 11111111 11111111
             *
             * & : 01111111 11111111 11111111 11111110
             * ~ : 10000000 00000000 00000000 00000001
             *
             */
            System.out.println("^: " + (left ^ right));
            // System.out.println(java.lang.Integer.parseInt("01111111111111111111111111111110", 2));
            // System.out.println(java.lang.Integer.parseInt("10000000000000000000000000000001", 2));
            int byteReverse = ~(left ^ right);
            // System.out.println(java.lang.Integer.toBinaryString(byteReverse));
            // System.out.println(byteReverse);
            
            return (~(left ^ right) & (left ^ (left + right))) < 0;
        }
    }

    public static boolean willSubstractionOverflow(int left, int right)
    {
        if (right < 0) {
            return willAdditionOverflow(left, -right);
        } else {
            return ((left ^ right) & (left ^ (left - right))) < 0;
        }
    }
    
    /*
    public static int addExact(int x, int y)
    {
        int r = x + y;
        if ((x ^ r) & (y ^ r) < 0) {
            throw new ArithmeticException("integer overflow");
        }

        return r;
    }
    */
}
