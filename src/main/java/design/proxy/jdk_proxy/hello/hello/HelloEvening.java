package design.proxy.jdk_proxy.hello.hello;

public class HelloEvening implements Hello {
    @Override
    public void say(String message) {
        System.out.println("evening, " + message);
    }
}
