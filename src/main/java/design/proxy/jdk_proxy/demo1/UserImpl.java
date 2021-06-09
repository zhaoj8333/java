package design.proxy.jdk_proxy.demo1;

public class UserImpl implements Personal {
    @Override
    public void introduction() {
        System.out.println("我是客户");
    }
}
