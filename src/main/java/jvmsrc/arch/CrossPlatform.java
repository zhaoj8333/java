package jvmsrc.arch;

public class CrossPlatform {

    /**
     * Intel汇编 vs AT&T汇编 vs arm汇编
     *
     *  +---------+--------------+--------------+
     *  |   派系  |    Intel     |     AT&T     |
     *  +--------+--------------+--------------+
     *  |  编译器 |   windows    |    Unix      |
     *  +--------+--------------+--------------+
     *  |  寄存器 | rax, rbx ... |  %rax, %rbx..|
     *  +--------+--------------+--------------+
     *  |  指令  | mov eax, 8   | movl $8 %eax |
     *  +-------+--------------+--------------+
     */
    private static void assembly() {

    }

    private static void why() {

    }
}
