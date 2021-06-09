package lang.reflection.d2;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 反射：
 *     主要指程序可以访问，检测和修改它本身状态或行为的一种能力.
 *
 *     实现了反射的系统都具有开放性
 *     反射会破坏封装性，使得私有变量访问不安全
 *
 *     反射常用于需要在运行时检测程序行为：
 *        反射让开发人员ke可以通过外部的类的路径创建对象，实现扩展功能
 *        让开发人员枚举类的全部成员，属性，方法
 *        测试时使用反射api访问类的私有成员
 *
 * java反射：
 *     使得可以创建灵活的代码，这些代码可以在运行时装配，无需在组件之间进行源代码链接
 *
 * 安全性：
 *     反射经常由框架代码使用，
 *
 * 性能：
 *     反射主要缺点是对性能有影响,反射基本上是一种解释操作，这些操作总是慢于直接执行相同的操作
 */
public class JavaReflection {
    public static Class<Student> sc = Student.class;

    public static void main(String[] args) {
//        testField();
//        testConstructor();
//        testMethod();
//        testSecurity();
        testPerformance();
    }

    /**
     * 通过性能比较，使用反射的执行时间超过直接使用的10倍
     */
    private static void testPerformance() {
        long l = accessSame(100000000);
        long l1 = accessRef(100000000);
        long l2 = accessReflection(100000000);
        System.out.println(l);
        System.out.println(l1);
        System.out.println(l2);
    }

    private static final int ADDITIVE_VALUE = 10;
    private static final int MULTIPLIER_VALUE = 100;

    private static long accessSame(int loops) {
        long l = System.currentTimeMillis();
        int m = 0;
        for (int i = 0; i < loops; i++) {
            m = (m + ADDITIVE_VALUE) * MULTIPLIER_VALUE;
        }
        long e = System.currentTimeMillis();
        return e - l;
    }

    private static long accessRef(int loops) {
        long l = System.currentTimeMillis();
        Dog dog = new Dog();
        for (int i = 0; i < loops; i++) {
            dog.age = (dog.age + ADDITIVE_VALUE) * MULTIPLIER_VALUE;
        }
        long e = System.currentTimeMillis();
        return e - l;
    }

    private static long accessReflection(int loops) {
        long l = System.currentTimeMillis();
        Dog dog = new Dog();
        try {
            Field age = Dog.class.getDeclaredField("age");
            for (int i = 0; i < loops; i++) {
                int val = (age.getInt(dog) + ADDITIVE_VALUE) * MULTIPLIER_VALUE;
                age.setInt(dog, val);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        long e = System.currentTimeMillis();
        return e - l;
    }

    private static void testSecurity() {
        Student aaa = new Student("AAA", 3);
        try {
            Field name = Student.class.getDeclaredField("name");
            name.setAccessible(true);
            String aaaName = "";
            System.out.println(name.getGenericType());
            System.out.println(name.getName());
            System.out.println(name.getDeclaringClass());
            System.out.println(name.getModifiers());
            System.out.println(name.get(aaaName));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void testMethod() {
        Method[] methods = sc.getDeclaredMethods();
        System.out.println(Arrays.toString(methods));
    }

    private static void testConstructor() {
        Constructor<?>[] constructors = sc.getConstructors();
        System.out.println(Arrays.toString(constructors));
    }

    private static void testField() {
        Field[] declaredFields = sc.getDeclaredFields();
        System.out.println(Arrays.toString(declaredFields));
    }
}
