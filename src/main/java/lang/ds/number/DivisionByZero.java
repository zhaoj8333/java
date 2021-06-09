package lang.ds.number;

public class DivisionByZero
{
    public static void main(String[] args)
    {
        // check();
        checkAgain();
    }
    
    public static void checkAgain()
    {
        
        // System.out.println(java.lang.Double.POSITIVE_INFINITY);
        // System.out.println(java.lang.Double.NEGATIVE_INFINITY);
        // System.out.println(java.lang.Double.NaN);
        
        // 为何java除以0.0不抛出异常？
        // 
        // 从数学上讲，除以 零 是未定义的，可以用浮点数或双精度表示，NaN不是数字
        // 由于整数必须包含特定的数值，因此在处理他们时必须抛出异常
        //
        // float和double类型都符合 IEEE 754标准，该标准要求除以零以返回特殊的 无穷大 值，
        // 抛出异常会违反该标准
        //
        // 整数运算（由硬件实现为二进制补码表示）是不同的，没有特殊的无穷大或NaN值,
        // 所以抛出异常时异种有用的行为
        //
        System.out.println( 1.0 / 0.0 );
        System.out.println( -1.0 / 0.0 );
        System.out.println( -123 / 0.0 );
        System.out.println( 0.0d / 0.0 );

    }
    

    public static void check()
    {
        // double d = 0;
        // int i = 0;
        // System.out.print(123 / d);  // Infinity
        
        /**
         * Division by zero is caught at hardware level and results in interrupt being called
         * with usually leads OS to stopping the process
         * for java, the OS dowsn't kill anything but the VM commits suicide
         * 
         * in unix env, division-by-zero is signal led via SIGFPE, the JVM will have installed a
         * signal handler which traps the SIGFPE and in turn throw an ArithmeticException 
         *
         * The JVM's handler instead issues the (catchable) RuntimeException so that these exceptions
         * can be handled in a native-seeming way;
        
         * in fact, the SIGFPE is generated from the kernel and is mapped from a special interrupt
         * form the CPU itself, so the pipeline is:
         * 1. CPU error trap interrupt
         * 2. kernel interrupt handler
         * 3. SIGFPE SIG_DFL
         * 4. process death
         *
         * or 
         * 1. CPU error trap interrupt
         * 2. kernel interrupt handler 
         * 3. SIGFPE handler in JVM
         * 4. RuntimeException ArithmeticException in user code
         *
         * java handles the situation like other languge;
         *  processor triggers an interrupt
         *  the interrupt is read by os and forward to the program if a program is registered
         *  since java registers a handler, it receives the error and translate it into ArithmeticException
         *
         */
        int a = 2;
        int b = 0;
        System.out.println(a / b); // Exception in thread "main" java.lang.ArithmeticException: / by zero
        /**
         * strace javac DivisionByZero
         * 
         * futex(0x7fdaf1cbe9d0, FUTEX_WAIT, 11255, NULLException in thread "main" java.lang.ArithmeticException: / by zero)
         * at DivisionByZero.check(DivisionByZero.java:46)
         * at DivisionByZero.main(DivisionByZero.java:5)
         * exit_group(1)
         * +++ exited with 1 +++
         *
         * strace -f javac DivisionByZero
         *
         * unlike c compile program, 
         * ??????? java program runs on a runtime , not on the processor ????????
         * and is not platform dependent and exceptions is throw of reasons such as:
         * 1. an abnormal execution condition was synchronously detected by the jvm, because:
         *    evaluation of an expression violation the normal semantics of the language, such 
         *    as division by zero
         * 2. an error occurs in loading or linking part of the program
         * 3. some limitation on a resource is executed, such as using too much memory
         *
         * Runtime(运行时)
         * 指将数据类型的确定由编译时推迟到了运行时
         * 程序运行过程中，动态的创建类，动态添加，修改类
         * 
         * java字节码在jvm RunTime里面翻译成当前平台的机器码
         *
         * 每个java程序在运行时相当于启动了一个jvm进程，每个jvm进程都对应一个RunTime实例。
         * 该实例时JVM负责实例化，所以用户不能实例化一个RunTime对象，可以通过getRuntime
         * 获去当前Runtime实例的引用。
         * 通过该Runtime对象引用，查看jvm的状态以及控制jvm的行为
         *
         * Although we say Java program runs on a runtime, if finally runs on processor.
         * In addition, use -Xcomp, then,jit must compile every code into local processor
         * instructions. both program run on top of os
         *
         * ??? exceptions are thrown by runtime before executed or whiling executing????
         *
         * Exception is shorthand for phrase "exceptional event"
         *
         * An lang.exception is an event, which occurs during the lang.exception of a program, that
         * disrupts the normal flow of the program's instructions
         *
         * When an error occurs within a method, the method creates an object and hands it
         * off to the runtime system.
         * The object is called Exception object, contains information about the error,
         * including its type and the state of the program when the error occurred.
         *
         * Creating an lang.exception and handling it to the runtime system is called throwing an lang.exception
         * 
         * If you catch that lang.exception then you can do what you want to do, otherwise it is handled
         * by default handler which show the error info and terminate the program
         *
         */

        //
        // System.out.println(123 / i);   // ArithmeticException
    }
}
