package lang.grammer;

public class Lambda {
    public static void main(String[] args) {
        /**
         * 函数式思想： 强调做什么，而不是以什么形式做
         */
//        demo1();
        demo2();
    }

    private static void demo2() {

    }

    private static void lambda() {
        System.out.println("lambda");
    }

    private static void demo1() {
        System.out.println("main");

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("lambda 1");
            }
        }).start();

        new Thread(() -> {
            System.out.println("lambda 2");
        }).start();

        new Thread(()->System.out.println("lambda 3")).start();
    }
}
