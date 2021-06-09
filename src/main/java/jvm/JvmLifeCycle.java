package jvm;

/**
 * jvm的启动:
 *     jvm启动是通过引导类加载器(bootstrap class loader)创建一个初始类(initial class)来完成的, 这个类是由jvm具体实现指定的
 *
 * jvm的执行:
 *     jvm任务: 执行java程序
 *     程序开始执行时jvm才运行,程序结束时就停止
 *     java程序执行时, 真正执行的是一个jvm进程
 *
 * jvm 的退出:
 *     程序执行正常结束
 *     异常或错误而异常终止
 *     os错误导致jvm进程终止
 *     调用RunTime或system类的exit, halt
 *     JNI描述了jvm退出的情况
 */
public class JvmLifeCycle {
    public static void main(String[] args) {

    }
}
