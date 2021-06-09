package jvm.bc;

/**
 * 栈帧: stack frame
 *     虚拟机用栈帧进行方法的调用,执行和返回的数据结构
 *     栈帧归属于线程, 因此不存在同步和并发的概念
 *     栈帧封装了方法的局部变量表, 动态连接信息, 方法的返回地址和操作数栈
 *
 *     符号引用, 直接引用
 *         静态解析: 有些符号引用是在类加载阶段或是第一次使用时就会转换为直接引用
 *             静态方法,
 *             父类方法
 *             构造方法
 *             私有方法
 *             以上四类方法称为非虚方法, 他们是在类加载阶段就可以将符号引用转换为直接引用
 *         动态解析(链接): 每次在运行期转换为直接引用, 体现为java的多态性
 *
 * 局部变量表: slot
 *
 * 1. invokeinterface:
 *    调用接口方法, 实际上是运行器决定, 动态决定到底调用该接口的哪个对象的特定发方法
 *
 * 2. invokestatic:
 *    调用静态方法
 *
 * 3. invokespecial:
 *    调用自己的私有方法, 或构造方法(<init>)以及父类方法
 *
 * 4. invokevirtual:
 *    调用虚方法, java语言层面上没有虚方法概念, 与多态紧密相关
 *
 * 5. invokedynamic:
 *    动态调用方法, 可以调用其他语言的函数
 *
 */
public class ByteCodeRunning {
    public static void main(String[] args) {
        testInvokeStatic();
    }

    /**
     * main:
     *
     * invokestatic
     * return
     */
    private static void testInvokeStatic() {
        System.out.println("invoke static");
    }

    /**
     * slot复用情况
     */
    private static void testSlotReusing() {
        int a = 3;
        /**
         * 因为if块执行完后, b, c所在的变量slot 可能会被覆盖
         * 具体依赖于具体的jvm实现
         */
        if (a < 4) {
            int b = 3;
            int c = 1;
        }
        int d = 7;
        int e = 8;
    }
}
