package lang.ds.number;

public class PrimitiveTest
{
    private static boolean bool;
    private static byte bty;
    private static char ch;
    private static float f;
    private static double d;
    private static int i;
    private static long l;
    private static short sh;
    private static String str;
    
    /**
     * 内置数据类型：
     *  二进制补码表示,默认0
     *  byte:  8bit
     *  short: 16bit
     *  int  : 32bit
     *  long : 64bit
     *  
     * IEEE 754标准
     *  float
     *  double
     * 
     * 布尔表示一位的信息
     *  boolean
     *
     * Unicode-16 
     *  char: unsigned 16bit \u0000(0) - \uffff(65535)
     *
     */
    public static void main(String[] args)
    {
        
        /*
         // default values of each type
         System.out.println("boolean: " + bool);
         System.out.println("bty: " + bty);
         System.out.println("char: " + ch); // u000
         System.out.println("float: " + f);
         System.out.println("double: " + d);
         System.out.println("integer: " + i);
         System.out.println("long: " + l);
         System.out.println("short: " + sh);
         System.out.println("String: " + str);
         原始值不与其他原始值共享状态。

         Primitive values do not share state with other primitive values.
         byt();
         System.out.println();
         shorts();
         ints();

         longs();
         floats();
         doubles();
        */

        chars();
    }

    public static void chars()
    {
        char a = 'a';
        char b = 'b';
        System.out.println("a > b: " + (a > b));

        Character c = 'c';
        Character d = 'd';
        System.out.println("d > d: " + (c > d));

        System.out.println(java.lang.Character.SIZE); // 16
        System.out.println((int)java.lang.Character.MIN_VALUE);
        System.out.println((int)java.lang.Character.MAX_VALUE); // 65535
    }

    public static void doubles()
    {
        System.out.println(java.lang.Double.SIZE);
        System.out.println(java.lang.Double.MIN_VALUE);
        System.out.println(java.lang.Double.MAX_VALUE);
    }

    public static void floats()
    {
        System.out.println(java.lang.Float.SIZE);
        System.out.println(java.lang.Float.MIN_VALUE);
        System.out.println(java.lang.Float.MAX_VALUE);
    }

    public static void longs()
    {
        System.out.println(java.lang.Long.SIZE);
        System.out.println(java.lang.Long.MIN_VALUE);
        System.out.println(java.lang.Long.MAX_VALUE);
    }

    public static void ints()
    {
        System.out.println(java.lang.Integer.SIZE);
        System.out.println(java.lang.Integer.MIN_VALUE);
        System.out.println(java.lang.Integer.MAX_VALUE);
    }

    public static void shorts()
    {
        System.out.println(java.lang.Short.SIZE);
        System.out.println(java.lang.Short.MIN_VALUE);
        System.out.println(java.lang.Short.MAX_VALUE);
    }

    public static void byt()
    {

         // byte
         // 取值范围 -128 - 127
         
        System.out.println("min: " + java.lang.Byte.MIN_VALUE);
        System.out.println("max:  " + java.lang.Byte.MAX_VALUE);
        System.out.println();
        
        // 范围： 0000 0000 - 1111 1111
        //
        // 正数
        // 0000 0000 - 0111 1111(原码)
        System.out.println(java.lang.Byte.parseByte("00000000", 2)); // 0
        System.out.println(java.lang.Byte.parseByte("01111111", 2)); // 127
        System.out.println();

        // 负数
        //   1000 0000 - 1111 1111(-0 - -127) 原码
        //   1000 0001 - 1111 1111(-1 - -127) 原码
        //       |           |
        //   1111 1110 - 1000 0000 反码
        //   1111 1111 - 1000 0001 补码 (-1 - -127)
        //
        // -0
        //   1000 0000(原码) - 1111 1111(反码) - [1]0000 0000 
        // -0没有意义，所以补码可以比别的方式多一个数
        // 使用-0表示-128，所以-128补码为1000 0000,没有对应的原码和反码（相对于8bit情况下）
        //
        
        // System.out.println(java.lang.Character.MIN_RADIX);
        // System.out.println(java.lang.Character.MAX_RADIX);
        
        // System.out.println(java.lang.Byte.parseByte("11111110", 2));

        // 也就是说，当创建变量的时候，需要在内存中申请空间。
        // System.out.println(java.lang.Byte.SIZE);
        // 内存管理系统根据变量类型 为变量分配存储空间，分配的空间只能用来存储该类型数据
    
    }

    public static void floatPoint()
    {
        // float: 32bit
        // double: 64bit
    }

    public static void booleanType()
    {
        // true
        // false
    }

}
