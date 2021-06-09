package lang.java8.functional;

interface MathOperation {
    int operate(int a, int b);
}

//@SuppressWarnings("all")
public class Demo2 {
    public static void main(String[] args) {
//        test1();
//        innerClass();
//        省略规则();
        peramsTransfer();
    }

    public static void method(MathOperation m, int a, int b) {
        m.operate(a, b);
    }

    private static void peramsTransfer() {
//        method((a, b)->a * b, 1, 2);
        final MathOperation mathOperation = new MathOperation() {
            @Override
            public int operate(int a, int b) {
                System.out.println(a * b);
                return a * b;
            }
        };
        method(mathOperation, 1, 2);
        final MathOperation mathOperation1 = (a, b)->{
            System.out.println(a * b);
            return a * b;
        };
        method(mathOperation1, 1, 2);

        method((a, b)->a * b, 1, 2);
    }

    /**
     * lambda使用条件:
     *     必须有接口, 不能是抽象类, 接口中仅有一个需要被重写的抽象方法
     *     必须支持上下文推导, 能够推导出lambda表达式表达的是那个接口的内容
     * 可以使用接口做参数,然后传递lambda表达式
     */
    private static void 省略规则() {
        final MathOperation add = new MathOperation() {

            @Override
            public int operate(int a, int b) {
                return a + b;
            }
        };
        final int operate = add.operate(1, 2);
        System.out.println(operate);
    }

    private static void innerClass() {
        /*
         * 匿名类内部最关键的内容:
         *     参数
         *     方法
         *     返回值
         * 最好的情况就是只关注匿名内部类最核心的内容, labmda表达式是匿名内部类的简化写法
         *
         * 面向对象思想: 怎么做
         * 函数式思想:   做什么
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("running 0");
            }
        }).start();

        new Thread(()->System.out.println("running 1")).start();
        /*
            匿名内部类: 省去创建java文件的操作
            labmda关注的是:
                ( ... ) 参数类型 参数名, 参数类型 参数名, ...
                ->      指向性动作
                { ... } 方法体与返回值与普通java语法一致
                ( ) -> {
                    方法体
                    return 返回值
                }
         */
    }

    private static void test1() {
        MathOperation add = (a, b) -> a + b;

        MathOperation sub = (a, b) -> a - b;

        MathOperation mul = (a, b) -> a * b;

        MathOperation div = (int a, int b) -> {
            return a / b;
        };

        final int operate = operate(2, 3, (a, b) -> a + b);
        System.out.println(operate);
        final int operate1 = operate(2, 3, sub);
        System.out.println(operate1);
        final int operate2 = operate(2, 3, mul);
        System.out.println(operate2);
        final int operate3 = operate(2, 3, div);
        System.out.println(operate3);
    }

    private static int operate(int a, int b, MathOperation operation) {
        return operation.operate(a, b);
    }
}
