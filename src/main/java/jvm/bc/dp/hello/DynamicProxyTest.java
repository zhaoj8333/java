package jvm.bc.dp.hello;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * 动态代理实质上是动态创建class字节码并加载的过程
 */
@SuppressWarnings("all")
public class DynamicProxyTest {
    public static void main(String[] args) {
        testHello();
    }

    private static void testHello() {
        // InvocationHandler负责实现接口方法的调用
        InvocationHandler invocationHandler = new Morning();
        String[] names = new String[] {"Bob", "Jack", "Jim"};
//        for (String name : names) {
            // Proxy.newProxyInstance创建interface实例
            Hello hello = (Hello) Proxy.newProxyInstance(
                Hello.class.getClassLoader(),   // ClassLoader
                new Class[]{ Hello.class },     // 传入要实现的接口
                invocationHandler               // InvocationHandler
            );
            hello.say(names[0]);
//        }

        InvocationHandler invocationHandler1 = new Evening();
        final Hello o = (Hello) Proxy.newProxyInstance(
                Evening.class.getClassLoader(),
                new Class[]{Hello.class},
                invocationHandler1
        );
        o.say("Jack");
    }
}
