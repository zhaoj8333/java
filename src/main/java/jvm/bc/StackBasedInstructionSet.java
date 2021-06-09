package jvm.bc;

/**
 * JVM基于栈的指令集架构
 *
 * 基于寄存器的指令集
 *
 * 基于栈的指令集: 可移植性
 *
 * 关系:
 *     JVM执行指令时所采取的是基于栈的指令集, 主要操作入栈,出栈,
 *     基于栈的指令集优势在于: 可移植性好, 而基于寄存器的指令集与硬件架构紧密相关, 无法做到可移植
 *      缺点在于指令集数量多, 操作都是基于内存操作, 而基于寄存器的指令集直接由CPU执行, 在告诉缓存中执行, 速度快很多
 *      虽然jvm采用了优化手段, 但总体来说基于栈的指令集的执行效率要慢一些
 *
 */
public class StackBasedInstructionSet {
    public static void main(String[] args) {
//        test1();
//        test2();

    }

    /**
     *  iconst_1
     *     将数字入栈, 相当于 bipush 1
     *  istore_1
     *     实例方法中0位置为this
     *     istore_i, 出栈并将局部变量表中的i位置设置为出站后得值, i必须是当前栈帧中 局部变量表中的索引, 栈顶的操作数必须为int
     *  iconst_2
     *  istore_2
     *  iconst_3
     *  istore_3
     *  iconst_4
     *  istore 4 // istore index(局部变量表的索引)
     *
     *  iload_1 // 位于n处的局部变量值被推入栈中, iload_n n必须是当前栈帧局部变量表的索引, n位置的局部变量为int,
     *  iload_2
     *  iadd    // 将栈顶的两个数字相加, 并将结果压回栈顶, 该指令不会抛出运行时异常
     *  iload_3 // 数字3入栈, 源相加结果向下压了一层
     *  isub    // 不会抛出运行时异常
     *  iload 4
     *  imul
     *  istore 5
     *  iload 5 // 将局部变量表中的第5个数入栈顶
     *  ireturn // 返回, 值会被pop出目前的栈帧, 并压入调用者的栈帧的操作数栈, 当前的操作数栈都会被丢弃
     */
    private int test3() {
        int a = 1;
        int b = 2;
        int c = 3;
        int d = 4;
        int result = (a + b - c) * d;

        return result;
    }

    /**
     * iconst_1     从局部变量表中入栈
     * istore_0     将栈顶值出栈保存到第0个变量中(局部变量表)
     * iload_0      将变量从局部变量表中入栈
     * iinc 0 by 1  局部变量表中数字 +1, 不在栈上操作, 此时栈顶的值仍为0, increment local variable by constant
     * istore_0     栈顶出栈保存到局部变量表, 变量表中的值被覆盖
     */
    private static void test1() {
        int i = 0;
        i = i++;
        System.out.println(i);
    }

    /**
     * iconst_1
     * istore_0
     * iinc 0 by 1
     * iload_0
     * istore_0
     */
    private static void test2() {
        int i = 0;
        i = ++i;
        // 2
        System.out.println(i);
        // 1
    }
}
