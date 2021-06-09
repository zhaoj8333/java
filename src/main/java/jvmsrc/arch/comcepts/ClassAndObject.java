package jvmsrc.arch.comcepts;

/**
 * class文件: javac编译后的存放在磁盘上的文件
 * class content: 类加载器将class文件加载到内存中形成的内存中的数据
 * Class对象: 类加载器对class content的按照jvm规范的解析
 * 对象:
 *
 *
 * 区别:
 * 方法区:
 * 永久代: 堆
 * 元空间: 直接内存(native memory)
 *
 * 联系:
 *     方法区可以视为java的interface
 *     元空间和永久代可以视为该interface的实现类
 *
 * 防止内存抖动
 * 元空间大小建议设置为物理内存的 1/32
 *
 * 栈帧:
 *     局部变量表: 其大小由编译时确定
 */
public class ClassAndObject {
    public static void main(String[] args) {

    }
}
