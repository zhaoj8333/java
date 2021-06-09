package design.proxy.jdk_proxy.hello;

import design.proxy.Tool;
import design.proxy.jdk_proxy.hello.hello.Hello;
import design.proxy.jdk_proxy.hello.hello.HelloEvening;
import design.proxy.jdk_proxy.hello.hello.HelloMorning;

import java.lang.reflect.Proxy;

@SuppressWarnings("all")
public class ProxyTest {
    public static void main(String[] args) {
        HelloInvocationHandler helloHandler = new HelloInvocationHandler(new HelloMorning());

        Hello o = (Hello) Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                new Class[]{ Hello.class },
                helloHandler
        );
        o.say("Bob");
        Tool.genClass(o);

        HelloInvocationHandler helloHandler1 = new HelloInvocationHandler(new HelloEvening());
        Hello o1 = (Hello) Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                new Class[]{ Hello.class },
                helloHandler1
        );
        o1.say("Bob");

        Tool.genClass(o1);


    }
}
