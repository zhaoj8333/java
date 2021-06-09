package design.proxy.jdk_proxy.demo1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@SuppressWarnings("unchecked")
public class JdkProxyFactory<T> implements InvocationHandler {
    private T target;

    public JdkProxyFactory(T target) {
        this.target = target;
    }

    public T getProxy() {
//        System.out.println("classLoader: " + target.getClass().getClassLoader());
//        System.out.println("interfaces : " + target.getClass().getInterfaces());
        return (T) Proxy.newProxyInstance(
            target.getClass().getClassLoader(),
            target.getClass().getInterfaces(),
            this
        );
    }

   @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//       System.out.println("method: " + method);
        System.out.println("before");
        // 调用 introduction
        Object invoke = method.invoke(target, args);
        System.out.println("end");
        return invoke;
    }
}
