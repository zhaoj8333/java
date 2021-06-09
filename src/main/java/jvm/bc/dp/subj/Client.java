package jvm.bc.dp.subj;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/*
 * 动态代理:
 *     * 每一个代理对象都关联一个invocation handler, 通过构造方法传入
 *     * 一个interface的方法调用会派遣到invocation handler的invoke方法
 *     * 在代理类中的java.lang.Object类中的hashcode, equals, toString, 这些方法会被编码并派遣到invocation handler的invoke方法
 *       其他的方法将不会被代理类所重写, 调用与java.lang.Object调用完全一致
 */
/**
 * $Proxy0类文件:
 *     class Proxy {
 *         protected Proxy(InvocationHandler h) {
 *            Objects.requireNonNUll(h);
 *            this.h = h;
 *        }
 *
 *        public static Object newProxyInstance(ClassLoader loader, Class<?>[] interface, InvocationHandler h) {
 *            Class<?> cl = getProxyClass0(loader, intfs);
 *            // class com.sun.proxy.$Proxy0
 *            final Constructor<?> cons = cl.getConstructor({InvocationHandler.class})
 *            return cons.newInstance(new Object[] {h});
 *            // 创建$Proxy0的一个真实实例
 *        }
 *     }
 *
 *     public class $Proxy0 extends Proxy implements Proxy0 {
 *         public $Proxy0(InvocationHandler var1) throws {
 *              super(var1)
 *         }
 *
 *         Method m1 = Class.forName("java.lang.Object").getMethod("equals", Class.forName("java.lang.Objeect"));
 *         public final boolean equals() {
 *              return (Boolean) super.h.invoke(this, m1, new Object[]{var1})
 *         }
 *
 *         Method m2 = Class.forName("java.lang.Object").getMethod("toString");
 *         public final String toString() {
 *              return (String) super.h.invoke(this, m2, (Object[]) null)
 *         }
 *
 *         // 需要被代理的方法, 另外的方法都来自Object类
 *         Method m3 = Class.forName("jvm.bc.dp.subj.Client").getMethod("request")
 *         public final void request() {
 *              super.h.invoke(this, m3, (Object[])null);
 *         }
 *
 *         Method m0 = Class.forName("java.lang.Object").getMethod("hashCode");
 *         public final int hashCode() {
 *              return (Integer) super.h.invoke(this, m0, (Object[]) null);
 *         }
 *     }
 *     除了以上三个方法外, 使用动态代理对象调用java.lang.Object的其他方法都不受动态代理的任何影响
 *
 * 用途:
 *     AOP
 *     cglib
 */
public class Client {
    public static void main(String[] args) {
        test();
    }

    private static void test() {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        final RealSubject rs = new RealSubject();
        InvocationHandler ds = new DynamicSubject(rs);
        Class<?> clazz = rs.getClass();

        Subject subject = (Subject) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), ds);
        // subject真正的指向为$Proxy0对象
        subject.request();
        System.out.println(subject.getClass());
        System.out.println(subject.getClass().getSuperclass());
        // com.sun.proxy.$Proxy0
    }
}
