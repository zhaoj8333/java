package jvm.bc.dp.subj;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicSubject implements InvocationHandler {
    private Object sub;

    public DynamicSubject(Object sub) {
        this.sub = sub;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before invoke: " + method);

        method.invoke(sub, args);

        System.out.println("after invoke:  " + method);
        return null;
    }
}
