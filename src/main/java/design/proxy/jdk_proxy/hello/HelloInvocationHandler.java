package design.proxy.jdk_proxy.hello;

import design.proxy.jdk_proxy.hello.hello.Hello;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class HelloInvocationHandler implements InvocationHandler {
    Hello hello;

    public HelloInvocationHandler(Hello hello) {
        this.hello = hello;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("say".equals(method.getName())) {
            System.out.println("method: " + method);
            Object invoke = method.invoke(hello, args);
            return invoke;
        }
        return null;
    }
}
