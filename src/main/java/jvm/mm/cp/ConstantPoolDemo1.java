package jvm.mm.cp;

public class ConstantPoolDemo1 {
    public static void main(String[] args) {
//        System.out.println("hello");
        testStringTable();
//        testIntern();
//        testInter1();

    }

    private static void testInter1() {
        String x = "ab";
        // x 位于串池中
        String s = new String("a") + new String("b");
        // s位于堆中
        final String intern = s.intern();

        System.out.println(intern == x);
        // true
        System.out.println(s == x);
        // false
    }

    private static void testIntern() {
        // 常量a被放入串中
        // 常量b被放入串中
        // new String被放入堆中, 但是对象不是同一个
        // new String("ab") 仅仅存在于堆中, 不在串池中, 因为是动态拼接的字符串
        // 串池中只存放 静态的字符串
        // intern就是将堆中的字符串对象放入串池, 如果串池中有,则不放入, 没有..., 并返回串池
        String s = new String("a") + new String("b");
        System.out.println(s);
        System.out.println(s == "ab");  // false
        final String intern = s.intern();
        System.out.println(intern);

        System.out.println(intern == "ab"); // true, 此处ab用的就是串池中的ab
        System.out.println(s == "ab");  // false
    }

    /**
     * 常量池中的信息, 都会被加载到运行时常量池中,
     * 此时 a b c都是常量池中的符号,还没有成为 java字符串对象
     *
     * StringTable[] 刚开始为空, 称为字符串对象后, 形成以字符串为key的哈希表, 不能扩容
     *
     * 创建字符串时,首先查找串池中是否已经存在, 不存在才创建, 有了就使用串池中的对象
     *
     * LocalVariableTable:
     *         Start  Length  Slot  Name   Signature
     *             3      90     0    s1   Ljava/lang/String;
     *             6      87     1    s2   Ljava/lang/String;
     *             9      84     2    s3   Ljava/lang/String;
     *            28      65     3    s4   Ljava/lang/String;
     *            32      61     4    s5   Ljava/lang/String;
     *            38      55     5    s6   Ljava/lang/String;
     *            42      51     6    s7   Ljava/lang/String;
     *
     *
     *          0: ldc           #3                  // String a
     *          2: astore_0
     *          3: ldc           #4                  // String b
     *          5: astore_1
     *          6: ldc           #5                  // String ab
     *          8: astore_2
     *         28: ldc           #5                  // String ab
     *         30: astore        4
     *         32: aload_3
     *         33: invokevirtual #10                 // Method java/lang/String.intern:()Ljava/lang/String;
     *         36: astore        5
     *         38: ldc           #11                 // String abc
     *         40: astore        6
     */
    public static void testStringTable() {
        /**
         * ldc  #3
         * astore_0
         */
        String s1 = "a";
        /**
         * ldc  #4
         * astore_1
         */
        String s2 = "b";
        /**
         * ldc #5
         * astore_2
         */
        String s3 = "ab";   // "ab"位于串池中
        /**
         * aload_0
         * new StringBuilder().append("a").toString
         * 创建了一个新的String对象, 位于堆中
         *
         * new String("ab")
         */
        String s4 = s1 + s2;    // 变量 相加 : stringBuilder.append("a").append("b").toString()
        String s5 = "a" + "b";  // 两个常量 相加: ldc "ab", 在常量池中加载已经存在的"ab", 这是编译期优化
        String s6 = s4.intern();
        String s7 = "abc";

        // s4: new String("ab") 堆中
        // s3: "ab"在串池中
        // 两个不同的对象
        System.out.println("s3 == s4: " + (s3 == s4));
        // false

        // javac编译期优化: 编译期 s5 就已经确定为 "ab"
        // 已经确定的结果, 编译器就可以优化
        System.out.println(s3 == s5);
        // true
        System.out.println(s3 == s6);
        // true

        System.out.println("------------");

        String x2 = new String("c") + new String("d");
        // x2仅存在于堆中, new String("c"), new String("d")存在于堆中, 但"c", "d"位于串池中
        x2.intern(); // x2入池
        String x1 = "cd";
//        x2.intern(); // x2入池, 但是池中已经有了

        System.out.println(x1 == x2);
        // intern()在后: x2入池失败, false
        // intern()在前: x2入池成功, x1在声明时直接去串池查找"cd", 因此为true
    }
}
