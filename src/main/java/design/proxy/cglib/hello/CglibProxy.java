package design.proxy.cglib.hello;

//import net.sf.cglib.proxy.Enhancer;
//import net.sf.cglib.proxy.MethodInterceptor;
//import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

//public class CglibProxy implements MethodInterceptor {
//    private Object target;
//
//    public CglibProxy(Object target) {
//        this.target = target;
//    }
//
//    @Override
//    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
//        System.out.println("before invocation");
//        System.out.println();
//        Object invoke = method.invoke(target, objects);
//        System.out.println();
//        System.out.println("end invocation");
//        return invoke;
//    }
//
//    public static Object getProxy(Object target) {
//        System.out.println(target);
//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(target.getClass());
//        enhancer.setCallback(new CglibProxy(target));
//
//        return enhancer.create();
//    }
//}
