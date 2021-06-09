package lang.api;

import java.io.FileNotFoundException;
import java.util.Date;

class Person {

}
/**
 * private static native void registerNatives();
 * static {
 *     registerNatives();
 * }
 *
 * ???????
 */

/**
 * native修饰符： 关键字->本地方法
 *
 * 1. 本地方法不是抽象方法
 * 2. 本地方法是有方法体的，c语言编写，其方法体没有对用户开源，可理解为java语言的扩展
 *    比如io读写，java本身不具备读写功能，调用的都是os的读写功能，而os的读写功能，用的就是本地方法
 *    windows系统
 * 3. 内存位置：本地方法栈
 * 内存： （堆，栈，方法区，本地方法栈，寄存器）
 * 4. 意义：系统调用
 *
 * Java Native Interface(JNI):
 *     is a foreign lang.function interface: a mechanism by which a program written in one
 *   programming language can call routines or make use of services written in another.
 *
 *     it indicates that the method is implemented in native code using JNI(Written in C/C++).
 *   native is a modifier applicable only for methods and cannot be applied
 *   anywhere else.
 *
 * objectives:
 *    To improve performance of the system.
 *    To achieve machine level/memory level communication.
 *    To use already existing legacy non-java code
 *
 */
public class Demo1 {
    public static void main(String[] args) throws FileNotFoundException {

        // testToString();
        // testEquals();
        // testDate();
        testDateFormat();
    }

    private static void testDateFormat() {

    }

    private static void testDate() {
        Date date = new Date();
        System.out.println(date.getTime());
        date.setTime(3600L);
        System.out.println(date.getTime());
        System.out.println(date);

        Date a = new Date(1000L);
        System.out.println(a.getTime());
    }

    private static void testEquals() {}


    private static void testToString() throws FileNotFoundException {
        Person p = new Person();
        System.out.println(p.toString());

        System.out.println("=====================");

        // new FileOutputStream("").write();
    }


}
