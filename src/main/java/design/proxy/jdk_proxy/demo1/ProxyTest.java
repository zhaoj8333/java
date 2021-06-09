package design.proxy.jdk_proxy.demo1;

public class ProxyTest {

    public static void main(String[] args) {
        ProxyTest proxyTest = new ProxyTest();
        proxyTest.testJdkProxy();
    }

    public void testJdkProxy() {
        PersonalImpl personal = new PersonalImpl();
        JdkProxyFactory<PersonalImpl> personalJdkProxyFactory = new JdkProxyFactory<>(personal);
        Personal proxy = personalJdkProxyFactory.getProxy();
//        genClass(proxy);
        proxy.introduction();

        System.out.println();

        UserImpl user = new UserImpl();
        JdkProxyFactory<UserImpl> userJdkProxyFactory = new JdkProxyFactory<>(user);
        Personal proxy1 = userJdkProxyFactory.getProxy();
        proxy1.introduction();

    }

}
