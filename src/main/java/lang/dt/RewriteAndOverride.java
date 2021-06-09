package lang.dt;

/**
 * 重写：
 *     子类与父类之间
 *     方法名，参数列表，返回类型（子类返回类型可以是父类返回类型的子类）必须相同
 *     访问修饰符的限制必须大于被重写的修饰符(public > protected > default > private)
 *     重写方法一定不能抛出新的异常或比被重写方法申明更加宽泛的检查性异常
 *
 * 重载：
 *     同一类中，同名方法有不同的参数列表（包括类型，顺序），重载对返回类型没有要求
 *     重载是 类中多态的表现
 *     无法以
 */
public class RewriteAndOverride {
    public static void main(String[] args) {

    }

    public void say(String name) {
        System.out.println(name);
    }

    public void say() {
        System.out.println("hello");
    }
//
//    无法以返回值类型作为重载函数的区分标准
//
//    compile error:
//    public String say() {
//        return "say";
//    }
}
