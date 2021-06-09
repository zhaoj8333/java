package lang.ds.number;

public class Binary {
    public static void main(String[] args) {

        //// 将一个正整数 用二进制字符串表示
        ////
        //String s = java.lang.Integer.toBinaryString(10);
        //System.out.println(s);
//        String s = toBinaryStr(30000000);
//        System.out.println(s);
//        System.out.println(0b100000);
//        System.out.println(02000);
//        binaryToDecimal();
//        radixConversion();
        signedInteger();
    }

    public static void signedInteger() {
//        int i = 0b101010;
//        int j = -0b101010;
//        System.out.println(i);
//        System.out.println(j);
        int i = 1;
        int j = 2;
        /*
            i: 0000 0001
           -j: 1000 0010

           j反 1111 1101
           j补 1111 1110
          i-j: 0000 0001
               1111 1110
            =  1111 1111 -> 1111 1110 -> 1000 0001 -> -1
         */
        System.out.println(i - j);

        int m = -1;
        int n = -2;
        /*
            m: 1000 0001
               1111 1110
               1111 1111
            n: 1000 0010
               1111 1101
               1111 1110
            +  1111 1111
            =  1111 1101
               1000 0010 + 1 = 1000 0011
          */
    }

    public static void radixConversion() {
        // toBinaryString
        // toOctalString
        // toHexString
        // toString
        int i = 12312;
        System.out.println(Integer.toBinaryString(i));
        System.out.println(Integer.toOctalString(i));
        System.out.println(Integer.toHexString(i));
        System.out.println(Integer.toString(i));
        System.out.println();
        System.out.println(Integer.toString(0b11000000011000, 8));

    }

    public static void binaryToDecimal() {
//        128 64 32  16 8 4 2 1
//  88:       1   0  1  1 0 0 0 反推得到

    }

    public static String toBinaryStr(int n) {
        String s = "";
        for (int i = n; i > 0; i /= 2) {
            s = (i % 2) + s;
        }

        return s;
    }
}
