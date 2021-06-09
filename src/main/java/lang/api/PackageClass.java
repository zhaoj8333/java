package lang.api;

import java.util.ArrayList;
import java.util.List;

public class PackageClass {
//    装箱： 基本数据类型转化成引用数据类型
//    拆箱： 引用数据类型转化成基本数据类型

//    Advantages:
//    cleaner code, easier to read
//    do not need to perform typecasting explicitly
//
//    Wrapper classes
//    1. Why:
//      primitive values are passwd by value, but what if we need a object?
//      the java.util package handles only objects
//      Data Structures in the Collection framework, such ArrayList and Vector, only sotre Objects
//      To support synchronization in multithreading

    public static void main(String[] args) {
        // test1();
        // test2();
        // test3();
        // test4();
        // test5();
        test6();
    }

    private static void test6() {
//        Boolean b1 = "TRUE";
        // compile error: String cannot be converted to java.lang.Boolean
//        Boolean b2 = "FALSE";
//        boolean res = b1 && b2;
//        System.out.println(res);
    }

    private static void test5() {
        Boolean b1 = true;
        Boolean b2 = false;
        System.out.println((b1 == b2));
        System.out.println(b1.equals(b2));

//        System.out.println(true.equals(false)); // compile error
    }

    private static void test4() {
        Double x1, y1, z1;
        double x2, y2, z2;

        x1 = 10.0;
        y1 = 4.0;
        z1 = x1 * x1 + y1 * y1;

        x2 = 10.0;
        y2 = 4.0;
        z2 = x2 * x2 + y2 * y2;
        System.out.println(z1 + " ");
        System.out.println(z2);
    }

    private static void test3() {
//        Autoboxing: Converting a primitive value into an object of the
//        corresponding wrapper class.
//        The java compiler applies autoboxing:
//        1. Passed as a parameter to a method that expects an object of the wrapper class.
//        2. Assigned to a variable of the corresponding wrapper class
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
            // it does not generate a compile time error, because the compiler creates Integer classes
            // from primitive int i and add it to the list
        }

//        Unboxing:  Converting an object of a wrapper type to its corresponding
//        primitive value
//        The java compiler applies unboxing:
//        1. Passed as a parameter to a method that expects a value of the corresponding primitive type
//        2. Assigned to a variable of the corresponding primitive type.
        int sum = 0;
        for (Integer i : list) {
            // operators remainder(%), += operator do not apply to Integer objects
            // but still compiled successfully because of unboxing of Integer objects to primitive types
            if ((i & 1) != 1) {
                // unboxing of i is automatically using Integer.intValue() method
                sum += i;
            }
        }
        System.out.println("sum: " + sum);
        /*
        Integer i = new Integer(10);
        int i1 = i;
        System.out.println("i  : " + i);
        System.out.println("i1 : " + i1);

        Character a = 'a';
        char a1 = a;
        System.out.println("a:  " + a);
        System.out.println("a1: " + a1);
        */
    }

    private static void test2() {
        Integer a = Integer.valueOf(12);
        System.out.println(a);

        Integer b = Integer.valueOf("123");
        System.out.println(b);

        Integer c = Integer.valueOf("21312389");
        System.out.println(c);
    }

    private static void test1() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
//        自动装箱
        int i = list.get(0);
//        自动拆箱
        System.out.println(i + 10);

        Integer j = new Integer(1000);
        System.out.println(j);
    }
}
