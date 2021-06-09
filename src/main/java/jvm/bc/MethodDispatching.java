package jvm.bc;

class Grandpa {
    public static String getName() {
        return "grand";
    }
}
class Father extends Grandpa {
    public static String getName() {
        return "father";
    }
}
class Child extends Father {
    public static String getName() {
        return "child";
    }
}
class Fruit {
    public void test() {
        System.out.println("Fruit");
    }
}
class Apple extends Fruit {
    @Override
    public void test() {
        System.out.println("apple");
    }
}
class Orange extends Fruit {
    @Override
    public void test() {
        System.out.println("orange");
    }
}

/**
 * 虚方法:
 *  java方法执行时的动态分派 与 静态分派 是java实现多态的本质
 *
 *  java源代码编译后, 方法之间的调用是通过符号引用表示的,
 *  字节码被加载后, 符号引用被替换为对应方法在方法区的真实内存地址.
 *
 *  再被替换前, 由于java的重写, 重载, 就导致符号引用对应的方法可能是一个虚方法, 那么方法的真实实现在运行时可能有多个
 *  在将符号引用替换为真实地址时, 还需要确定符号引用要替换的方法的版本
 *
 * 运行时方法帧:
 *  局部变量表: 编译期确定, 单位为slot, 实例方法会多出一个this
 *  操作数栈:
 *  动态链接: 方法体中调用其他方法时, 会把将要调用的方法在常量池中的符号引用转化为将要在其方法区内存中的开始地址信息, 并存储到动态链接中
 *  方法返回地址: 方法执行完后, 一般是当前方法的程序计数器的值
 *      正常完成出口: 方法正常返回, 如果有返回值, 则返回值会被压入调用方法的操作数栈中
 *      异常完成出口: 发生异常且异常表中没有异常处理流程, 方法不会有返回值
 *
 * 方法调用:
 *  不同于方法执行, 方法调用的唯一任务就是确定被调用方法的版本
 *  invokestatic:
 *  invokespecial: 对象构造或私有方法
 *  invokevirtual:
 *      调用对象的public/protected方法, 可能通过继承复写的方法叫virtual method,
 *      表示要到运行时才能定位到真正的实现. 通过符号引用确定虚方法直接引用的过程叫做动态分派
 *  invokeinterface: 具体的实现类将在调用时确定
 *  invokedynamic: 用户可以决定如何查找目标方法
 *
 * 符号引用 -> 直接引用阶段 : 解析, 分派
 *  解析阶段: 编译期可知,运行期不可变: 构造函数, 私有方法, 静态方法, final方法
 *  分派阶段: 多态实现
 */
public class MethodDispatching {
    public void test(Grandpa grandpa) {
        System.out.println("grandpa");
    }

    public void test(Father father) {
        System.out.println("father");
    }

    public void test(Child child) {
        System.out.println("child");
    }

    public static void main(String[] args) {
//        testStatic();
//        testStaticDispatch();
        testDynamicDispatch();
    }

    /**
     * 方法的动态分派
     *
     * 方法的动态分派涉及到一个重要概念: 方法接收者(方法的调用者)
     *
     * 多态: 是一种运行期行为, 涉及到字节码指令invokevirtual字节码指令的多态查找流程
     *     ① 到操作数栈顶查找元素所指向的对象的实际类型, 实际上就是在runtime期间确定这个方法调用者实际类型到底是什么
     *     ② 如果该类型中寻找到了与常量池当中方法(方法签名)的方法, 且具备响应的访问权限, 如果都允许, 则直接返回目标方法的直接引用,
     *        如果找不到, 从子类往父类依次查找, 直到找到该方法, 然后去执行, 找不到, 则抛出异常
     *
     * 根据变量的 [实际类型] 匹配调用的方法的过程称为动态分派, 发生的场景为方法重写, 当调用子类重写或继承方法时触发
     * 调用方法时, jvm会去当前调用的对象的类查找相同描述符的方法, 存在则将符号引用替换为找到方法的直接引用,
     * 否则取父类查找 ..., 找不到则抛出NoSuchMethod异常
     *
     * 方法重载是静态的, 是编译期行为
     * 方法重写是动态的, 是运行期行为
     * 两者没有关系
     */
    private static void testDynamicDispatch() {
        Fruit apple = new Apple();
        Fruit orange = new Orange();
        apple.test();   // invokevirtual Fruit.test apple
        orange.test();  // invokevirtual Fruit.test orange
        /*
         * 以上两个方法:
         *     字节码中调用的是 Fruit的方法, 但是运行结果却不是
         *     即使是对于字节码中安全相同的两个符号引用, 运行期也会被解析成两个不同的直接引用, 这就是java多态性的重要表现
         */
        apple = new Orange();
        apple.test();   // invokevirtual Fruit.test orange
    }

    /**
     * 静态类型匹配
     *   静态类型: 声明类型, 编译期可知
     *   实际类型: 变量赋值时的实际类型, 编译期不可知, 只能在运行时才知道
     *
     * 静态分派:
     *   发生场景为方法重载
     *
     * 方法重载:
     *     是一种纯粹的静态行为, 在编译期间可以完全确定
     *
     * 静态类型是指变量的声明类型, 而不是真正指向的类型
     *
     * Grandpa parent = new Father();
     *      parent 的静态类型是Grandpa, 而parent的实际类型为Father(真正指向的类型)
     *      (Father) parent 将parent转化成了其子类型,但是对parent本身的类型没有任何改变
     *      所以, 变量的静态类型是不会发生变化的, 而实际类型是可以发生变化的, 这就是多态的一种体现, 实际类型是运行期确定的
     *
     * // 方法重载:
     *      是一种静态行为, jvm根据参数类型判断该去调用那种方法, 而不是根据方法参数的实际类型
     *
     *
     */
    private static void testStaticDispatch() {
        Grandpa parent = new Father();
        Grandpa child  = new Child();

        MethodDispatching extend = new MethodDispatching();
        extend.test(parent);
        // parent
        extend.test(child);
        // parent
        // 此处调用方法时实际上是根据 参数的静态类型到 三个重载方法进行匹配
        // extend.test((Child)child);
        // child
        // 强制转化后
    }

    private static void testStatic() {
        Grandpa.getName();
        Father.getName();
    }
}
