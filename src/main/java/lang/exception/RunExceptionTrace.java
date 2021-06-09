package lang.exception;

import java.util.Arrays;

public class RunExceptionTrace {
    public static void main(String[] args) {
        getStrace();

    }

    private static void getStrace() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(1);

        RuntimeException runtimeException = new RuntimeException();
        StackTraceElement[] stackTrace = runtimeException.getStackTrace();
        for (int i = 0; i < stackTrace.length; i++) {
//            System.out.println(stackTrace[i].getClassName() + " - " + stackTrace[i].getMethodName());
        }
        for (int i = 0; i < stackTrace.length; i++) {
            if ("main".equals(stackTrace[i].getMethodName())) {
                System.out.println("main");
            }
        }
//        System.out.println(Arrays.toString(stackTrace));
    }


}
