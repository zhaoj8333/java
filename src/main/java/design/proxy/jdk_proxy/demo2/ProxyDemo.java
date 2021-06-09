package design.proxy.jdk_proxy.demo2;

import java.lang.reflect.Method;

interface InvokeHandler {
    Object invoke(Object obj, Method method, Object ... arg);
}

interface Test {
    void say();
}

public class ProxyDemo {
    public static void main(String[] args) {

    }

    public Test create(final InvokeHandler handler, final Class<?> clazz) {
        return new Test() {
            @Override
            public void say() {
                try {
                    Method sayMethod = clazz.getMethod("say");
                    Object invoke = handler.invoke(this, sayMethod);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}

