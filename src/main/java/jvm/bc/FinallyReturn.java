package jvm.bc;

public class FinallyReturn {
    public static void main(String[] args) {
//        final int test = test1();
//        System.out.println(test);  // 20
        final int i = test2();
        System.out.println(i);      // 10
    }

    // i 被return前会被暂存, 只要finally中没有return, 不会影响结果
    private static int test2() {
        int i = 10;
        try {
            return i;
        } finally {
            i = 20;
        }
    }

    /*
      stack=1, locals=2, args_size=0
         0: bipush        10    10放入栈顶
         2: istore_0            10 -> slot 0 从栈顶移除
         3: bipush        20    20放入栈顶
         5: ireturn             返回栈顶
         6: astore_1            catch any -> slot 1
         7: bipush        20    20 放入栈顶
         9: ireturn             返回栈顶 int 20
      Exception table:
         from    to  target type
             0     3     6   any
      LineNumberTable:
        line 11: 0
        line 13: 3
     */

    /**
     * finally中的ireturn被插入了所有可能的流程, 因此返回结果以finally为准
     * 如果在finally中出现了return, 会吞掉异常, 无法发现可能出现的问题
     */
    public static int test1() {
        try {
            int i = 1 / 0;
            return 10;
        } finally {
            return 20;
        }
    }
}
