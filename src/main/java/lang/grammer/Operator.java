package lang.grammer;

public class Operator
{
    public static void main(String[] args)
    {

        // and();
        // or();
        
        // test();
        // xor();
        // byt();
        // shift();
        // unsignedShift();
        //
        // arithmetic();
        // relation();
        // logic();
        assign();
        // condition();
        // instanceOfOperator();
    }

    public static void instanceOfOperator()
    {
        // for object instance
        String name = "James";
        boolean res = name instanceof String;
        System.out.println("res: " + res);
    }

    public static void condition()
    {
        int a, b;
        a = 10;
        b = (a == 1) ? 20 : 30;
        System.out.println("b : " + b);
        b = (a == 10) ? 20 : 30;
        System.out.println("b : " + b);
    }

    public static void assign()
    {

        int a = 10;
        int b = 20;
        int c = 5;
        int s = 0;
        
        byte a1 = 1;
        int  b1 = 2;
        long b2 = 3;
        
        long res = b2 + b1; // ok
        long r   = a1 + b1; // ok

        // byte e = a1 + b1; // incompatible types
        // int  d = b2 + b1; // incompatible types

        // // c += a 会改变数据类型
        // // c = c + a 不会改变数据类型
        // short d = 2;
        // // d = (d + 1);
        // d += 1;
        // // System.out.println("d + 1 = " + d); // incompatible types 
        // System.out.println("d += 1 = " + d);   // 3
        
        // c |= 3;
        // // 0000 0101
        // // 0000 0011
        // // 0000 0111
        // System.out.println("c |= 3 = " + c);
        // c ^= 3;
        // // 0000 0101
        // // 0000 0011
        // // 0000 0110
        // System.out.println("c ^= 3 = " + c);
        // c &= 3;
        // // 0000 0011
        // // 0000 0101
        // // 0000 0001
        // System.out.println("c &= " + c);
        // c >>= 2;
        // // 0000 0101
        // // 0000 0001
        // System.out.println("c >> 2 = " + c);
        // c <<= 2;
        // // c = 3;
        // // 0000 0011
        // // 0000 1100
        // System.out.println("c << 2 = " + c);
        // c %= a;
        // System.out.println(c);


        // b /= a;
        // System.out.println("a /= a = " + b);
        // b *= a;
        // System.out.println("b *= a = " + b);
        // c -= a;
        // System.out.println("c -= a = " + c);
        // c = a + b;
        // System.out.println("a + b = " + c);
        // c += a;
        // System.out.println("c += a = " + c);
        
    }

    public static void logic()
    {
        boolean a = true;
        boolean b = false;
        
        // System.out.println("a && b = " + (a && b));
        // System.out.println("a || b = " + (a || b));
        // System.out.println("!(a && b) = " + !(a && b));
        
        int c = 5;
        // boolean d = (c < 4) & (++c < 10); // false
        // boolean d = (c < 4) && (++c < 10); // false 短路运算符
        System.out.println("result of | is " + c);
        System.out.println("c: " + c);

    }

    public static void relation()
    {

        // int a = 12;
        // int b = 32;
        // System.out.println(a == b);
        
        // int a = new Integer(123);
        // int b = new Integer(123);
        // System.out.println(a == b);  // true
        

        // char a = 66;
        // char b = 66;
        // System.out.println(a == b); // true
        
        // String a = "abc";
        // String b = "abc";
        // System.out.println(a == b);  // true
        
        // String a = new String("abc");
        // String b = new String("abc");
        // System.out.println(a == b); // false
        
        // String a = "a";
        // String b = "b";
        // // System.out.println(b > a); // bad operand types for binary operator '>'
        // System.out.println(a == b);   // false
        // System.out.println(a != b);   // true

    }

    public static void arithmetic()
    {
        int a = 10;
        int b = 20;
        int c = 25;
        int d = 25;

        // System.out.println("a + b = " + (a + b));
        // System.out.println("a - b = " + (a - b));
        // System.out.println("a * b = " + (a * b));
        // System.out.println("a / b = " + (a / b));
        // System.out.println("b / a = " + (b / a));
        // System.out.println("b % a = " + (b % a));
        // System.out.println("c % a = " + (c % a));
        
        // System.out.println("++a = " + (++a)); // 
        // System.out.println("a++ = " + (a++)); // 
        // System.out.println("a-- = " + (a--)); // 
        // System.out.println("--a = " + (--a)); // 
        
        // int a1 = a++;
        // System.out.println(a1); // 10
        // System.out.println(a1); // 10
        // System.out.println(a); // 11
        // int b1 = ++b;
        // System.out.println(b1);
        
        // int a2 = a * 3;
        // System.out.println(a2);
        
        // int x = 2 * ++a;  // 前++ 优先级高
        // int y = 2 * b++;
        // System.out.println(x); // 22
        // System.out.println(y); // 40
    
    }

    public static void unsignedShift()
    {
        System.out.println(3 >>> 1); // 0000 0011
        System.out.println(3 >>> 2); // 0000 0000
        
        // 位运算中左移跟符号没有关系的, 因为最高位就是符号位
        //
        // 无符号左移 与 左移 是一样的概念
        System.out.println(-3 >>> 1); // 1000 0011 -> 1111 1101 -> MAX_VALUE
    }

    public static void shift()
    {
        
        // int a = 60; // 0011 1100
        // // System.out.println(Integer.toBinaryString(a));
        // int b = 13; // 0000 1101
        // // System.out.println(Integer.toBinaryString(b));
        // int c = 0;  // 0000 1100 -> 12
        // c = a & b;
        // System.out.println("a & b = " + c);
        // c = a | b;  // 0011 1101 -> 61
        // System.out.println("a | b = " + c);
        // c = a ^ b;  // 0011 0001 -> 49
        // System.out.println("a ^ b = " + c);
        // c = ~a;     // 1100 0011 -> -61
        // // System.out.println(Integer.toBinaryString(-61)); // 11000011
        // System.out.println("~a = " + c);
        // c = a << 2; // 1111 0000
        // // System.out.println(Integer.toBinaryString(240));
        // System.out.println("a << 2 = " + c);
        // c = a >> 2; // 0000 1111
        // // System.out.println(Integer.toBinaryString(15));
        // System.out.println("a >> 2 = " + c);
        // c = a >>> 2;
        // System.out.println("a >>> 2 = " + c);
        
        // System.out.println(3 << 1); // 0000 0011 -> 0000 0110
        // System.out.println(3 << 2); // 0000 0011 -> 0000 1100

        // System.out.println(3 >> 1); // 0000 0011 -> 0000 0001
        // System.out.println(3 >> 2); // 0000 0011 -> 0000 0000 右移时，操作数为正数时，左边空缺补0

        // 右位移，负数则用补1
        // 移位：
        //  符号位不变、左边补上符号位
        // System.out.println(Integer.toBinaryString(10)); // 1010
        // System.out.println(Integer.toBinaryString(-10));
        // System.out.println(-10 >> 1);// -5 
        /**
        * -10 >> 1
         *
         * 1000 1010
         * 1111 0101
         * 1111 0110
         *
         * >> 1
         * 1111 1011
         * 1111 1010
         * 1000 0101  -> -5
         */
        // System.out.println(-10 >> 2);// -3 
        
        /**
         * -5 >> 1
         *
         * true form:                   1000 0101
         * radix-minus-one complement:  1111 1010
         * complement form:             1111 1011
         *
         * >> 1
         * complement form:             1111 1101
         * radix-minus-one complement:  1111 1100
         * true form:                   1000 0011 -3
         *
         *
         */
        int a1 = 60;   // 0000 0000 0011 1100 -> 60
        // >> 2           0000 0000 0000 1111 -> 
        // System.out.println(Integer.toBinaryString(a1));
        System.out.println("a1 >> 2 = " + (a1 >>  2) + " -> " + Integer.toBinaryString(a1 >> 2));
        System.out.println("a1 >>>2 = " + (a1 >>> 2) + " -> " + Integer.toBinaryString(a1 >>> 2));
        System.out.println();
        
        int a2 = -100; // 1111 1111 1111 1111 1111 1111 1001 1100
                // >> 2   1111 1111 1111 1111 1111 1111 1110 0111 补码右移
                //        1000 0000 0000 0000 0000 0000 0001 1001 -> -25  补码求原码
                // >>> 2  0011 1111 1111 1111 1111 1111 1110 0111 变成正数        
        // System.out.println(Integer.toBinaryString(a2));
        System.out.println("a2 >> 2 = " + (a2 >> 2) + " -> " + Integer.toBinaryString(a2 >> 2));
        System.out.println("a2 >>> 2 = " + (a2 >>> 2) + " -> " + Integer.toBinaryString(a2 >>> 2));
    
    }

    public static void byt()
    {
        /**
         * Complement of negative numbers:
         *
         * -7: 
         *  0000 0111(true form)
         *  1111 1000(radix-minus-one complement)
         *  
         *  + 1
         *
         *  1111 1001(complement form)
         *
         */
        
        // System.out.println(6 & 3); // 2 
        /**
         * 6 & 3
         *true 
         * 0000 0110 6
         * 0000 0011 3
         * (1 true , 0 false)
         * 0000 0010 -> 2
         *
         */

        // System.out.println(6 | 3); // 7
        /**
         * 6 | 3
         * 0000 0110 6
         * 0000 0011 3
         * 
         * 0000 0111 -> 7
         */

        // System.out.println(6 ^ 3); // 5
        /**
         * 0000 0110 6
         * 0000 0011 3
         *
         * 0000 0101
         * 相同为0
         * 不同为1
         *
         */
        
        // System.out.println(~7); // -8
        /**
         * 位非运算符
         * 0000 0111
         *
         * 1111 1000 -> -8
         * 
         * 负数时（最高位为1)则视为补码
         * -1 再 取反
         *
         */
        
        int a = 60; // 0011 1100
        int b = 13; // 0000 1101
        // a ^ b       0011 0001
        System.out.println(a ^ b);
        /**
         * ^ 对应位值相同，结果为0，否则为1
         *
         */
    }
    
    public static void xor()
    {
        System.out.println(true ^ true);
        System.out.println(true ^ false);
    }

    /**
     * && 短路与
     * &  与
     */
    public static void and()
    {
        // 单个逻辑运算符会将左右两个表达式都算出布尔值在进行运算
        //
        // 短路与：
        // 若左边表达式为false则不会对右边表达式进行判断，因为结果必然为false
        System.out.println(true & false); // false
        System.out.println(true && false);// false
        System.out.println(true && true);// true
        System.out.println(false && false);// true
    }
    
    /**
     * ||短路或
     * | 或
     */
    public static void or()
    {
        // 短路或：
        // 若左边表达式结果为true，则不对右边表达式进行判断，因为结果必然为true
        //
        // 短路运算符效率更高
        System.out.println(true | false); // true
        System.out.println(true || false);// true
        System.out.println(true || true);// true
        System.out.println(false || false);// false
    }

    public static void test()
    {
        int num = 1;
        System.out.println(false && num ++== 1);
        System.out.println(num);
        System.out.println("===================");
        num = 1;
        System.out.println(false & num ++== 1);
        System.out.println(num);
    }
}
