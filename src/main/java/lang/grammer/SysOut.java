package lang.grammer;

public class SysOut
{
    public static void main(String[] args)
    {

        // java.lang.System.out.println(true ? null : 42);
        // java.lang.System.out.println(null); // compile error: reference to println is ambiguous

        /**
         * true ? null : 42, is Integer therefore it is unambiguous that System.out.println(Object) should be called
         *
         * System.out.println(null): there are multiple candidate methods and the compiler can't decide which one to take
         *
         */

        // println();
    }

    public static void println()
    {
        /**
         * System.out.println(args):
         * System:  final class,java package, stdin/stdout/stderr, env variables, loading io.files and libs, array copy
         * 
         * out:     Instance of PrintStream, static member field of System class, get mapped with stdout
         *          gets initialized by jre at startup and can be changed by user
         */

        // try {
        //     System.setOut(new PrintStream(new FileOutputStream("log.txt")));
        //     System.out.println("output redirected");
        // } catch(Exception e) {}
        
        /**
         * when building a logging component, SOP should be avoided
         */

        
        /**
         * println: method of PrintStream class, multiple println methods with different arguments(overloading)
         *
         * println's variability is based on method overloading, multiple methods with the same name,
         * but with different parameters
         *
         * how java knows how to concatenate non-string variables: "foo" + 1 + true + lang.obj, it is mainly
         * responsiblity of the Java compiler, in the bytecode, there is no concatenation instruction,
         * so the + operator is resolved during compilation. The concatenation is translated in to
         * StringBuilder#append chain
         */

        // java.lang.System.out.println(new StringBuilder("foo").append(true).toString());
        /**
         * Performance:
         * There are various factors that drives the performance of println
         * 
         * - kernel time is required to execute this task, kernel time refers to CPU time
         *   println is a synchronized method, so multiple threads are passed could lead to
         *   low-performance issue, it is a slow operation as it causes heavy overhead
         * - call sequence: println -> print -> write -> newLine
         *   write,newLine both contains synchronized block
         */
    }
}
