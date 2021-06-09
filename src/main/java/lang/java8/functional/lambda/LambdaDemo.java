package lang.java8.functional.lambda;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * lambda:
 *     函数式编程特性, 把函数当成参数传递
 *     在将函数作为一等公民的语言中, lambda表达式类型为函数,
 *     在java中, lambda表达式是对象, 他们必须依赖于一类特别的对象类型 -- 函数式接口({@link FunctionalInterface})
 *
 */
public class LambdaDemo {
    public static void main(String[] args) {
//        frameTest();

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

//        for (Integer integer : list) {
//            System.out.println(integer);
//        }
//        hello(list);
//        eat();

//        outerLoop();

        final List<String> letterList = Arrays.asList("hello", "world", "asjfkdlsa");
        letterList.forEach(item ->System.out.println(item.toUpperCase()));

    }

    /**
     * 外部迭代: 迭代器
     * 内部迭代: 函数式接口
     */
    private static void outerLoop() {

    }

    private static void eat() {
        final Eatable eat = new Eatable() {
            @Override
            public void eat() {
                System.out.println("eat");
            }
        };
        System.out.println(eat);
        System.out.println("=================");

//        final Fruit fruit = new Fruit();
//        fruit.eat();
//        System.out.println(fruit);

        Eatable eatable = () -> {
            System.out.println("eatable");
        };
        eatable.eat();
        System.out.println(eatable.getClass());
        System.out.println(eatable.getClass().getSuperclass());
        System.out.println(eatable.getClass().getInterfaces()[0]);
    }

    /**
     * 函数式接口:
     *     接口, 拥有一个抽象方法, 也可以包含默认实现方法
     *
     * 函数式接口的实例可以通过lambda表达式创建, 方法引用或构造方法引用
     *
     * 如果一个类型被注解为@{@link FunctionalInterface}, 如果该类型本身是注解, 或enum或class, 编译器会生成错误信息
     *
     * 1. 接口只有一个抽象方法, 该接口就是一个函数式接口
     * 2. 如果某个接口添加了 {@link FunctionalInterface}, 则编译器会按照函数式接口的定义来要求该接口
     * 3. 如果某个接口只有一个抽象方法, 但并没有{@link FunctionalInterface}注解, 编译器依旧会将该接口看做函数式接口
     *
     * @FunctionalInterface 注解好处, 一目了然, 如果被声明方法方法写错了, 编译器会报错
     *
     */
    private static void hello(List<Integer> list) {
        // java 8
        // 匿名内部类, 实现了Consumer接口
        // Consumer: 代表一个单个的操作, 接收一个输入参数, 不返回结果
        list.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                System.out.print(integer);
            }
        });
        System.out.println();
        // 参数推断
        list.forEach((Integer i) -> {
            System.out.print(i);
        });
        System.out.println();
        // 类型推断
        list.forEach(i -> {
            System.out.print(i);
        });
        // 方法引用
        list.forEach(System.out::println);
    }

    private static void frameTest() {
        final JFrame frame = new JFrame("frame");
        final JButton button = new JButton("Button");
        button.addActionListener(event -> {
            System.out.println("button clicked");
            System.out.println("hello");
        });
        frame.add(button);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
