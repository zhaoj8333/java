package lang.exception;

import java.io.FileNotFoundException;

/**
 * 异常处理：
 *  在进行运算时，出现例外的情况对应的处理，这种情况经常会破坏程序的正常流程。
 *  异常通常由特殊的编程语言结构，计算机硬件机制所构成。具体实现由硬件和软件自身自定义决定。一些异常，尤其是硬件，将会被中断后进行恢复。
 *
 *  1. 硬件领域：
 *      硬件异常由cpu处理。发生错误后将程序流程跳到专门的错误处理过程中。发生异常前的状态存户在栈上。
 *  2. os领域：
 *      针对程序异常，os通过进程间通信提供处理设施。
 *      进程执行中的中断由os提供的终端服务子程序处理，os可以借此向进程发送信号。
 *      进程通过该信号自行处理信号，或让os终止该程序。
 *  3. 软件领域：
 *      编程领域中的异常是一种数据结构，用以存储异常信息。
 *      异常处理的常见机制是移交控制权抛异常(throw) 和 接异常(catch)
 */

public class ExceptionDemo1 {
    public static void main(String[] args) /*throws Exception*/ {
//        demo1()
//        Errors:
//        demo1();
//        demo2();
//        demo3();
//        demo4();
//        demo5();
//        demo6();
        // main方法的throws，异常会交给jvm
        // 不知道抛什么异常时，可以抛出异常父类Exception
//        demo7();
//        demo8();
        demo9();
    }

    private static void demo9() {
        // 父类方法抛异常，子类可抛可不抛出，抛出异常必须与父类异常相同或父类异常的子类
        // finally中的代码一定会执行
        try {
            System.out.println(1 / 0);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        } finally {
            // 用于处理关闭资源
            System.out.println("finally");
        }
        // 如果异常未能捕获到，最终只有jvm捕获到该异常，以下代码不会执行
        System.out.println("end");
    }

    private static void demo8() {
        // try catch
        String s = null;
        s = "a.txt";
        // try中有啥异常，catch就得捕获什么异常
        try {
            System.out.println(1 / 0);
            trycatch(s);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println(e.getMessage());
//            System.exit(1);
        }

        System.out.println("end end");
    }

    private static void trycatch(String s) throws FileNotFoundException {
        if (s == null) {
            throw new NullPointerException("为空");
        }
        if (s.endsWith("txt")) {
            throw new FileNotFoundException("不存在");
        }

        System.out.println("end");
    }

    private static void demo7() throws Exception {
        String s = null;
        exp(s);
    }

    private static void exp(String s) throws Exception {
        if (s == null) {
            throw new NullPointerException("指针为空");
        }

        if (s.endsWith(".txt")) {
            throw new FileNotFoundException("文件不存在");
        }
    }

    private static void demo6() throws FileNotFoundException {
        String s = "a.txt";
        file(s);
    }

    private static void file(String s) throws FileNotFoundException {
         if (!s.endsWith("txt")) {
            throw new FileNotFoundException("文件不存在");
         }

        System.out.println("xxxxxx");
    }

    private static void demo5() {
        /**
         * 代替jvm进行异常处理
         * throw 后面的异常必须是Exception或RunTimeException及其子类
         */
        String s = null;
        print(s);
        System.out.println("就送达了开发");
    }

    private static void print(String s) {
//        System.out.println("throw before");
        if (s.equals(null)) {
            // 代替虚拟机创建异常对象,但是没有处理,jvm会自己抛出
            throw new NullPointerException("字符串不能为null");
        }
        System.out.println("adsklfj");
    }

    private static void demo4() {
        // Exception执行过程
        int[] arr = new int[3];
        print(arr);
        System.out.println("demo4");
    }

    private static void print(int[] arr) {
        System.out.println(arr[4]);
        // 程序运行过程中，虚拟机检查到了代码有异常出现
        // 此处的异常其实是java提前预定义好的ArrayOutOfBoundsException
        // jvm会检查此处有没有处理异常的动作
        // 如果没有处理，jvm就会自己处理异常：
        //      创建异常对象；
        //      抛出异常
        //      按照默认的方式处理方式：将异常抛给调用者，再次查看调用者有没有异常处理方式，没有则会递归该流程
        //      如果没有找到，则异常的处理方式交给jvm
        //      jvm终止程序，打印异常信息到控制台
        //      此处的异常信息是jvm输出的
        System.out.println("print");
    }

    private static void demo3() {
        String s = null;
        System.out.println(s.length());
        /**
         * 运行时异常：编译时不报错，但运行时报错
         */
    }

    private static void demo2() {
        int[] arr = new int[1024 * 1000 * 1024];
        // OutOfMemoryError: Java heap space: 堆内存空间溢出
        // 如果不以Exception结尾，则为错误
        System.out.println(arr.length);
    }

    private static void demo1() {
        // 程序执行过程中，出现的非正常情况，最终会导致jvm非正常停止
        // java处理异常的方式是 中断处理

//        Throable
//          Exception（异常）:除了RunTimeException及子类，其余都是编译时异常
//            代码级的错误，可以修复，修正后后面的代码是可以运行的
//              编译时异常：
//              运行时异常：
//          Error（错误）: 代码无法运行
        try {
            int[] arr = new int[3];
            System.out.println(arr[4]);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        System.out.println("爱是减肥快拉倒撒");

    }


}
