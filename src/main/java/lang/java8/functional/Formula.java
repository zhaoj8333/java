package lang.java8.functional;

/**
 * java 8 函数式编程
 *
 *     default 关键字:
 *         接口默认方法
 */
public interface Formula {
    // 默认为静态的
    String str = "string";

    double calculate(int a);

    default double sqrt(int a) {
        return Math.sqrt(a);
    }
}
