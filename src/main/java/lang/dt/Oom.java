package lang.dt;

/**
 * Java OOM ({@link OutOfMemoryError}) 情况：
 *    除了程序计数器之外，其他区域都可能发生
 *
 *    1. heap溢出：通过堆转储进行分析，还可以查看泄露对象到GCRoots的引用链
 *    2. 虚拟机栈和本地方法栈溢出： {@link StackOverflowError}, {@link OutOfMemoryError}
 *    3. 常量池溢出： {@link OutOfMemoryError}:PermGenspace
 *       {@link String#intern()}
 *    4. 方法区溢出：
 *
 *    SOF: {@link StackOverflowError}, 程序递归太深，死循环，集合过大
 */
public class Oom {
}
