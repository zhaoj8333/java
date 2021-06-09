package lang.dt;

/**
 * 即时 try catch finally, try中有return，finally块仍然会执行，且finally中的代码执行早于try中的return
 *
 * 无论有没有异常，finally块都会执行
 * try catch中有return时，finally也会执行
 * finally是在return右边的表达式后执行的，此时仅仅是将返回值保存起来，不管finally中的代码怎样，返回值都不会变，所以函数返回值是在finally执行前确定的
 * finally中最好不要有return，否则程序提前退出，就不会返回try catch中的返回值了
 *
 * 分类：
 *     运行时异常：编译器不会检查，不会检查是否有try-catch捕获，如 {@link ArithmeticException}, {@link IndexOutOfBoundsException},
 *
 *      {@link java.util.ConcurrentModificationException}:并发修改异常
 *         fast-fail机制 java.util下的所有集合类都是快速失败的，是java集合的一种错误监测机制，多线程操作时有可能发生
 *         juc下所有的包的类都是安全失败的，迭代中如果发生修改，则会反映到迭代器中，这就是juc迭代器弱一致的表现 {@link java.util.concurrent.ConcurrentHashMap}
 *      {@link ClassCastException}
 *      {@link IndexOutOfBoundsException}
 *      {@link NullPointerException}
 *      {@link ArrayStoreException}
 *
 *     被检查异常：除运行时异常以外的其他的 {@link Exception}子类异常
 *        编译器会检查
 *        {@link CloneNotSupportedException}
 *        {@link java.io.IOException}
 *        {@link java.io.FileNotFoundException}
 *        {@link java.sql.SQLException}
 *
 *        这些异常适用于那些不是因程序引起的错误情况，文件不存在，然而，不被检查的异常通常都是糟糕的编程引起的
 *
 *     错误：{@link Error}及其子类，编译器不会做检查
 *        当资源不足，约束失败，或其他条件导致程序不能运行，产生错误，程序本身无法修复
 *        {@link VirtualMachineError}
 *        {@link OutOfMemoryError}
 *        {@link ThreadDeath}
 *
 *
 */
public class JavaException {
    public static void main(String[] args) {

    }

    private void tryReturn() {

    }
}
