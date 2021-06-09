package jvm.bc;

/**
 * 方法运行流程:
 *  1. 原始java代码
 *  2. 编译后的字节码文件
 *  3. jvm类加载器 加载类
 *      运行时常量池载入字节码常量池数据, 运行时常量池为方法区的一部分
 *      方法字节码载入方法区
 *  4. 执行引擎执行字节码
 *      栈帧, 操作数栈
 */
@SuppressWarnings("all")
public class MethodRunning {

    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
//        invoke();
    }

    /**
     * 静态方法为invokestatic, 构造方法, 私有方法都是 invokespecial, 编译时确定
     * 实例方法调用为 invokevirtual 动态绑定: 需要在运行时确定调用, 因为有方法重写的可能, 多态
     */
    private static void invoke() {
    }

    private static void test3() {
        int i = 0;
        int x = 0;
        while (i < 10) {
            x = x++;    // iload_1, iinc1 1, 后又做了复制操作, 相当于把局部变量表中的 x有覆盖掉了
//            x++;
            i++;
        }
        System.out.println(x);  // 0
    }

    private static void test2() {
        int a = 10;
        int b = (a++) + (++a) + (a--);
//        int a1 = a++;
//        int a2 = ++a;
//        int a3 = a--;
//        System.out.println(a1 + " " + a2 + " " + a3);
        //      12       12      10
        /*
         3: bipush        10
         5: istore_1

         a++:
         6: iload_1
         7: iinc          1, 1      直接在局部变量表的slot上做运算, 操作数栈没有影响, 栈上的a == 10

         ++a:
        10: iinc          1, 1
        13: iload_1                 栈上的 a == 12

        14: iadd                    栈上的 a == 22

         a--:
        15: iload_1
        16: iinc          1, -1     栈上的 a == 12

        19: iadd
        20: istore_2
        35: return
         */
        System.out.println(a);  // 11
        System.out.println(b);  // 34
    }

    private static void test1() {
        int a = 10;
        int b = Short.MAX_VALUE + 1;
        int c = a + b;
        System.out.println(c);
    }
}
