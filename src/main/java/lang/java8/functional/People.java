package lang.java8.functional;

/**
 * 函数式接口: 仅仅包含一个抽象方法,但是可以有多个非抽象方法(默认方法)的接口,
 * 这样的接口可以转换为lambda表达式
 *
 * @FunctionalInterface 该注解并非必须使用,
 * 只要接口包含抽象方法,jvm会自动判断该接口为函数式接口
 */
@FunctionalInterface
public interface People {
    String say(String msg);

    default void play() {
        System.out.println("play");
    }
}
