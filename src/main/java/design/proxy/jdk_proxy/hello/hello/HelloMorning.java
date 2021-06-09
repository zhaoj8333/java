package design.proxy.jdk_proxy.hello.hello;

public class HelloMorning implements Hello {
    @Override
    public void say(String message) {
        System.out.println("morning, " + message);
    }
}
