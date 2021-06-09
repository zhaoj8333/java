package lang.obj.hierarchy;

public class HelloTest implements HelloWorld {
    @Override
    public void hello() {
        System.out.println("hello");
    }

    @Override
    public void say() {
        System.out.println("say test");
    }

    public static void main(String[] args) {
        HelloTest hello = new HelloTest();

        hello.hello();
    }
}
