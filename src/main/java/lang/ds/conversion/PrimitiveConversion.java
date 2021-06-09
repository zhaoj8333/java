package lang.ds.conversion;

public class PrimitiveConversion
{
    public static void main(String[] args)
    {
        // 整型、常量、字符型可以混合运算，
        // 不同类型可以转化为同一类型，然后进行运算
        //
        // 转换从上到下
        //
        // boolean不参与数据类型转换
        // 对象不能转化成不相关类的对象
        // 容量大的类型转化为容量小的类型时必须使用强制类型转换
        // 转换可能损失精度
        //
        // byte
        // short,char
        // int
        // long
        // float
        // double
        
        // autoConversion();
        // forceConversion();
        // implicitForceConversion();
        // reference();
        // calculatedType();
        
        // encapsulation();
        // equal();
        // string2X();
        // x2String();
//        date();
        demo1();
    }

    public static void demo1() {
        byte b = (byte)140;
        System.out.println(Byte.MAX_VALUE);
        System.out.println(Byte.MIN_VALUE);
        System.out.println(b);
    }

    public static void date()
    {
        java.util.Date date = new java.util.Date();
        // System.out.println(date);
        
        // SimpleDateFormat y = new SimpleDateFormat("yyyy");
        // SimpleDateFormat m = new SimpleDateFormat("MM");
        // SimpleDateFormat d = new SimpleDateFormat("dd");
        
        // String year  = y.format(date);
        // String month = m.format(date);
        // String day   = d.format(date);
        
        // // System.out.println(y);
        // // System.out.println(m);
       // // System.out.println(d);
        // System.out.println(year);
        // System.out.println(month);
        // System.out.println(day);
        
        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        // String ymd = dateFormat.format(date);
        // System.out.println(ymd);

    }

    public static void x2String()
    {
        int   a1 = 65;
        byte  a2 = 65;
        short a3 = 65;
        char  a4 = 'A';

        float    d1 = 65.00f;
        double   d2 = 65.00d;
        boolean  b  = true;
        
        // System.out.println("a1 :" + String.valueOf(a1));
        // System.out.println("a2 :" + String.valueOf(a2));
        // System.out.println("a4 :" + String.valueOf(a4));
        // System.out.println("d1 :" + String.valueOf(d1));
        // System.out.println("b  :" + java.lang.String.valueOf(b));
        
        // getNumericValue
        // Character ch = new Character('a');
        // System.out.println(ch);
    }

    public static void string2X()
    {
        String s = "1";

        byte b   = Byte.parseByte(s);
        short sh = Short.parseShort(s);
        int i    = Integer.parseInt(s);
        long l   = Long.parseLong(s);
        float f  = Float.parseFloat(s);
        double d = Double.parseDouble(s);
        
        System.out.println("b:  " + b);
        System.out.println("sh: " + sh);
        System.out.println("i:  " + i);
        System.out.println("l:  " + l);
        System.out.println("f:  " + f);
        System.out.println("d:  " + d);
    }

    public static void equal()
    {
        int   a1 = 65;
        byte  a2 = 65;
        short a3 = 65;
        char  a4 = 'A';

        float    d1 = 65.00f;
        double   d2 = 65.00d;
        boolean  b  = true;

        // System.out.println(a1 == a2); // true
        // System.out.println(a1 === a2); // java中没有 === 
        // System.out.println(a1 == a3); // true
        // System.out.println(a2 == a3); // true
        // System.out.println(a1 == a2);
        // System.out.println(a1 == d1);
        // System.out.println(a2 == d2);
        // System.out.println(a4 == d2);  // true
        // System.out.println((boolean)d1); // can not be coverted to boolean
    }

    public static void encapsulation()
    {
        // 简单类型变量 转换为 响应的封装类，可以利用封装类的构造函数
        // 基本类型 与 封装类型
        // Primitive   Wrapper
        //
        // 基本类型：
        //  值传递
        //  int 默认值为0
        //  位于栈内存
        //  处理速度快，不涉及对象的构造和回收
        //
        // 封装类型：
        //  引用传递，传递对象地址
        //  有方法和属性，以此处理数据
        //  Integer默认值为null
        //  引用类型的引用在栈内存中，实际的对象值在堆内存中
        //  更好的处理数据转换
        //
        //
        // Boolean(booean)
        // Character(char)
        // Integer(int)
        // Long(long)
        // Float(float)
        // Double(double)
        // 通过xxxValue方法获取其简单型数据和变量转换
        
        // float f1  = 100.123f;
        // Float f2  = new Float(f1);
        // double d1 = f2.doubleValue();
        // System.out.println(f1);
        // System.out.println(f2);
        // System.out.println(d1);
        
        // boolean b1 = true;
        // Boolean b2 = new Boolean(b1);
        // System.out.println("booleanValue:" + b2.booleanValue());
        
        // char c = 'b';
        // Character ch = new Character('b');
        // System.out.println("char value: " + ch.charValue());
        // // System.out.println("int value:  " + ch.integerValue());
        
        // int d = 'c';
        // System.out.println(d);
        
        // int i = 6990;
        // Integer in = new Integer(i);
        // System.out.println("in int value:   " + in.intValue());
        // System.out.println("in float value: " + in.floatValue());
        // System.out.println("in double value:  " + in.doubleValue());
        // System.out.println("in long value:  " + in.longValue());
        // System.out.println("in byte value:  " + in.byteValue());
        // System.out.println("in short value:  " + in.shortValue());
    
        // double d1 = 190.1123;
        // Double d2 = new Double(d1);
        // System.out.println(d2.toString());
        // System.out.println(String.valueOf(d2));
        // // System.out.println("d2 int value: " + d2.intValue());
        // // System.out.println("d2 float value: " + d2.floatValue());
        // // System.out.println("d2 long value: " + d2.longValue());
        // // System.out.println("d2 byte value: " + d2.byteValue());

    }

    public static void calculatedType()
    {
        // java中比int小的类型做运算，在java编译时会被强制转化为int类型
        // 比int类型大时，会自动转化成最大类型的那个
        short a = 1;
        short b = 123;
        // short c = a + b; // incompatible types: possible lossy conversion from int to short
        int c = a + b;

        // System.out.println("a + b:" + c);
        // The type int is a primitive – not an object – so it cannot be dereferenced
        // dereferencing is the process of accessing the value refered to by a reference
        // int, char, float,boolean is a value, not reference, so they can not be dereferenced
        // System.out.println("a + b:" + c.getClass().toString()); // int cannot be dereferenced
        
        char d = 's';
        String e = "I am fine";
        System.out.println(e.getClass().toString()); // java.lang.String

    }
    
    public static void reference()
    {

    }

    public static void autoConversion()
    {
        // int i = 128;
        // byte b = (byte)i;
        // System.out.println("i:" + i);
        // System.out.println("b:" + b); // byte最大为127， -128，溢出

        // float到integer通过舍弃小数
        
        // double a = 123.123;
        // double b = 32.89;
        // System.out.println((int)(a));
        // System.out.println((int)(b));
        
        // int i = 65;
        // System.out.println((char)i); // A
        
        char c1 = 'a';
        int i1  = c1;
        System.out.println("char->int: " + i1);
        
        char c2 = 'A';
        int i2  = c2;
        System.out.println("char->int: " + i2);
    }

    public static void forceConversion()
    {
        int i1 = 123;
        byte b = (byte)i1;

        
        //System.out.println("int->byte:" + b);
    
        // boolean b2 = true;
        // System.out.println("bool->int: " + (int)b2); // boolean cannot be converted to int
        
        // float f2 = 123.21f;
        // System.out.println("float->int: " + (int)f2);

    }

    public static void implicitForceConversion()
    {
        // 整数类型 默认是int
        // 浮点型在定义float时必须在数字后面加f，F
        //
        // long类型必须要在数值后加L
        // long a = 9223372036854775807; // integer number to large
        long a = 9223372036854775807L;
        long b = -9223372036854775808L;
        System.out.println("a: " + a);
        System.out.println("b:" + b);
    }
}
