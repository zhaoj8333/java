package jvm.bc;

/**
 * 解释执行 与 编译执行
 *     现代JVM会将两者结合起来进行
 *
 * 解释执行: 通过解释器读取字节码, 遇到响应字节码则执行该指令
 * 编译执行: 通过JIT, 将字节码转换为本地机器码, JVM会根据热点代码来生成相应的本地机器码
 */

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;

/**
 * 异常的字节码
 *
 * Exception Table 异常表
 *
 * from to target  type
 *    2  5      8  Class java/lang/Exception
 *    [2, 5)
 */
public class ByteCodeException {
    public static void main(String[] args) {
        int i = 0;
        try {
//            i = 10;
            final Method test = ByteCodeException.class.getMethod("test");
            test.invoke(null);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
//            i = 20;
            e.printStackTrace();
        } finally {
            // finally 会检测try块中的异常, 也会检测catch块中的异常, 且总会得到执行
            i = 10;
            System.out.println("错误");
        }
    }
}
