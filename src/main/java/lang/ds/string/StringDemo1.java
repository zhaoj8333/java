package lang.ds.string;

public class StringDemo1 {
    public static void main(String[] args) {
//        demo1();
//        demo2();
//        demo3();
//        demo4();
//        demo5();
//        demo6();
//        demo7();
        demo8();
    }

    public static void demo8() {
        String s0 = "kvill";
        String s1 = "kvill";
        System.out.println(s0 == s1); // true
//        System.out.println(s0.equals(s1));
        System.out.println("=================");
        s0.intern();
//        ??????????????????????
    }

    public static void demo7() {
        /*
           在C语言中字符串和字符数组基本上没有区别，都需要结束符
           java中，字符串和字符串数组都是不需要结束符的
           Java里面一切都是对象，是对象的话，字符串肯定就有长度，即然有长度，
           编译器就可以确定要输出的字符个数，当然也就没有必要去浪费那1字节的空间用以标明字符串的结束了
           比如，数组对象里有一个属性length,就是数组的长度，String类里面有方法length()可以确定字符串的长度，
           因此对于输出函数来说，有直接的大小可以判断字符串的边界，编译器就没必要再去浪费一个空间标识字符串的结束。
         */
        char[] chars = {'j', 'a', 'v', 'a', '语', '言'};
        String s1 = new String(chars);
        String s2 = "java语言\0";
        System.out.println(s2);
        // java语言
        int a = chars.length;
        int b = s1.length();
        int c = s2.length();
        System.out.println(a);
        // 6
        System.out.println(b);
        // 6
        System.out.println(c);
        // 6
        /*
            java接收c语言数组时，需要用trim去掉末尾的\0
         */
    }

    public static void demo6() {
        /*
         Strings in Java are Objects that are backed internally by a char array.
            Whenever a String object is created, two objects will be created:
                One in the Heap Area
                One in the String constant pool

                ** The String object reference always points to heap area object
         */
        String s = "GeekforGeeks";
        System.out.println("String s = " + s);

        String s1 = new String("GeekforGeeks");
        System.out.println("String s1 = " + s1);

    }

    public static void demo5() {
        /**
         * String:
         * Objects that are backed internally by a char array
         *
         * Since array are immutable(cannot grow), Strings are immutable as well, Whenever a change to a String is made
         * and entire new String is created.
         *
         * Immutability: a class instance cannot be modified, since All information in an instance has been initialized
         *
         * Why:
         * 1. String Pool(String intern pool):
         *      String Pool is a special storage area in Method Area.
         *      When a String is created and if it already exists in the pool, the reference of the existing string will be returned
         *      Instead of creating a new object.
         */
        String a = "abcd";
        String b = "abcd";
//        b = "a";
        char[] chars = b.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            System.out.println(chars[i]);
        }
        /*
         *  2. Caching Hashcode:
         *      The hashcode is frequently used in Java.
         *      Being immutable guarantees that hashcode will always be the same so it can be cashed without worrying about changes.
         *      No need to calculate hashcode every time it is used.
         * 3. Facilitating the use of other objects
         *      if String is mutable, it can violate the design of set(which contains unduplicated elements)
         */
        //        HashSet<String> set = new HashSet<String>();
        //        set.add(new String("a"));
        //        set.add(new String("b"));
        //        set.add(new String("c"));
        //        for (String v: set) {
        //            System.out.println(v.toString());
        //            v.chars();
        //        }
        /**
         * 4. Security
         *      String is widely used in parameter passing, also network connection, opening io.files, etc. where String is
         *      mutable. a connection changed can lead to serious security threat.
         *      Mutable strings could cause a security problem in Reflections too
         */
        //        boolean connect(String s) {
        //            if (!isSecure(s)) {
        //                throw new SecurityException();
        //            }
        //        }

        /*
         * 5. for Thread-safe
         *      Immutable objects cannot be changed, they can be shared among multiple threads freely. This eliminates
         *      the requirement of doing synchronization
         */

        String str = new String();
        str = "hello";
//        System.out.println(str);
        str = "Help";
//        System.out.println(str);
        /*
         * "Hello", "Help" are two distinct String Objects, str is not an object, it's a reference to a Object.
         *  points to a String. You can change what is points to, but not that which it points at.
         */
        String s1 = "Hello";
        String s2 = s1;
        s1 = "help";
        // Setting s1 to "Help!" only changes the reference, while the String object it originally referred to remains unchanged.
//        System.out.println(s2);
        // Hello
    }

    public static void demo4() {
        String a = new String("ABCD");
        String b = new String("ABCD");

        System.out.println(a == b);
        // false
        // 通过new构造器方法创建后，在heap中分别分配了两个内存地址
        // 字符串驻留的操作在运行时刻仍然存在，即使是通过构造器的方式创建的
    }

    public static void demo3() {
        // 动态获取字符出现次数
        // 根据索引获取字符串中指定的字符
        // char charAt
        String s = "itheima";
//        System.out.println(s[1]);
//        char   c = s.charAt(2);
//        System.out.println(c);
        for (int i = 0; i < s.length(); i++) {
            System.out.println(s.charAt(i));
        }

    }

    public static void demo2() {
        String s1 = "abcdefg";
        char[] chars = s1.toCharArray();
        // 相对于demo3，效率可能会更高
        for (int i = 0; i < chars.length; i++) {
            System.out.println(chars[i]);
        }
    }

    public static void demo1() {
        char[] a = {'a', 'b', 'c'};
        String b = new String(a);
        String c = "abc";
        String e = "abc";
        System.out.println("c == e: " + (c == e));
        // c, e 指向同一个内存地址，他们的引用都是指向方法区中的同一个内容，引用地址是一样的
        // 当同一个String字面值无论被创建多少次，始终只有一个内存地址被分配
        // Java中称作“字符串驻留”，所有的字符串常量在编译之后都会自动的驻留。
        // c = "abc"这种方式创建的时候首先会查看字符串池中是否已经存在，
        // 存在就直接返回PermGen中的该String对象，否则就会创建一个新的String对象，之后再放进字符串池中。
        /**
         * 字符串驻留池：
         * 一块与堆区并行的存放字符串对象的内存区，jvm驻留池机制规定：
         * 在池中创建一个String对象，第二行先在池中寻找是否有abc相同的String对象，如果有就直接引用，
         * 没有就在池中新建String对象
         * 字符串池是为了解决字符串重复问题，生命周期长，位于jvm的permgen（永生代）中
         *
         * 编译时，原文件中的双引号字符串都被收纳到常量池中，用CONSTANT_utf8_info存储
         *
         * 运行时，相应的类被加载运行后，常量池对应的映射到jvm的运行时常量池中，每一项CONStant_utf8_info
         * 都会在常量引用解析时，自动生成相应的internal string，记录在字符串池中
         *
         */
//        System.out.println("abc".hashCode());
//        String d = new String("abc");
//        System.out.println("c == d: " + (c == d));
//
//        System.out.println(c.equals(d));
        // false

////        System.out.println(a == b); // incomparable types: char[] and String: Error
//        System.out.println(Objects.equals(a, b));
//        // false
//        System.out.println(b == c);
//        /* System.out.println("b.toString: " + b.toString()); */
//        System.out.println(b.hashCode());
//        // 96354
//        System.out.println(c.hashCode());
//        // 96354
//
//        // false
//        System.out.println(b.equals(c));
//        // 比较内容： true
//        if (b == "a") {
//            c = "a";
//        }
//        System.out.println(c.equals("abc"));
//        System.out.println("abc".equals(c));
        // c是一个 字符串 对象 变量，
        // 变量有可能记录到null，null调用时有可能引发空指针异常
        // "abc"是字符串常量，使用绝对不会引发空指针异常

        // 尽量使用字符串 常量 调用方法
//        System.out.println(Objects.equals(c, "abc"));
    }
}
