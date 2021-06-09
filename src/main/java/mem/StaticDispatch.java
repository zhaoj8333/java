package mem;

public class StaticDispatch
{

    private static abstract class Human {}

    private static class Man extends Human {}

    private static class Woman extends Human{}

    private void sayHello(Human guy)
    {
        System.out.println("hello, guy");
    }

    private void sayHello(Man guy)
    {
        System.out.println("Hello, man");
    }

    private void sayHello(Woman woman)
    {
        System.out.println("Hello, woman");
    }

    public static void main(String[] args)
    {
        Human man   = new Man();
        Human woman = new Woman();

        StaticDispatch dispatch = new StaticDispatch();
        
        // 传递给 sayHello() 方法的实际参数类型是 Man 和 Woman
        // 虚拟机在执行程序时选择的却是 Human 的重载呢
        dispatch.sayHello(man);
        dispatch.sayHello(woman);
    }


}
