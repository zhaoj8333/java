package jvm.bc.dp.hello;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class Evening implements Hello, InvocationHandler {
    @Override
    public void say(String name) {
        System.out.println("Good evening , " + name);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        if ("say".equals(method.getName())) {
            this.say((String) args[0]);
        }
        return null;
    }
}
