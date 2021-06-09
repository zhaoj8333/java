package lang.exception;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ExceptionDemo2 {
    private static BufferedReader fi = null;
    public static void main(String[] args) {
        demo1();
        demo2();
        demo3();
    }

    private static void demo2() {
        //   1.2 Unchecked Exception: not checked at compiled time, such as Runtime or NullPointerException
        //       in c++,all exceptions are unchecked
        //       ***java exceptions under Error and RuntimeException classes are unchecked, everything else under
        //       throwable is checked
        // 2. Error:
        //   2.1 Virtual Machine Error
        //   2.2 Assertion Error

        // should we make our exceptions checked or unchecked?
        // If a client can reasonably be expected to recover from an lang.exception, make it a checked lang.exception.
        // If a client cannot do anything to recover from the lang.exception, make it an unchecked lang.exception

        // java doesn't require methods to catch or to specify unchecked exceptions
    }

    private static void demo3() {
        // default lang.exception handling:
        // if an lang.exception occurred, the method creates an object and handles it to the jvm.
        // the lang.exception object contains the name and description of the lang.exception
        // procedure:
        // 1. the run-time system searches the call stack to find the method that contains block of code that can
        //    handle the occurred lang.exception. the block of the code is called Exception Handler
        // 2. The run-time system starts searching from the method in which lang.exception occurred, proceeds through
        //    call stack in the reverse code in which methods were called.
        // 3. if get the appropriate handler then it passes the occurred lang.exception to it.
        // 4. If doesn't, the Exception object will be handled to the default lang.exception handler, which is part of
        //    run-time system. this handler will prints the lang.exception information and terminates the program abnormally.
    }

    private static void demo1() {
        // Error: An error indicates serious problem that a reasonable application should not try to catch
        // Exception: Exception indicates conditions that a reasonable application might try to catch

        // throwable
        // 1. Exceptions:
        //   1.1 Checked Exception: checked at compile time, IO or Compile time Exception
        try {
            FileReader fr = new FileReader("/home/allen/java-learn/java/io.files/index.ph");
            fi = new BufferedReader(fr);
            for (int i = 0; i < 3; i++) {
                System.out.println(fi.readLine());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            fi.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
