package design.proxy.statis_proxy;

import design.proxy.statis_proxy.image.Image;
import design.proxy.statis_proxy.proxies.ProxyImage;

/*
    静态代理缺点:
        实现简单,不侵入原代码
        但是只维护一个类,且这个代理类实现多个接口,导致代理类过于庞大,且会产生过多的代理类

    动态代理:
        类的动态: jdk的类加载机制
    类的获取:
        jar,ear,war包格式获取
        动态代理: 计算时生成,由java.lang.reflect.Proxy根据给定字符串获取
 */
@SuppressWarnings("all")
public class ProxyTest {
    /*
        代理模式: 一个类代表另一个类的功能, 属于结构型模式
            代理模式中,创建现有对象的对象,向外界提供功能接口

        为其他对象提供一种能控制该对象的访问

        应用:
            windows快捷方式, 火车票代售点,
        优点: 职责清晰,高扩展,智能化
        缺点: 在客户端与真是主体之间增加了代理,有些代理模式可能造成处理变慢,

        装饰器是为了增强功能, 代理模是为了控制
     */
    public static void main(String[] args) {
        Image image = new ProxyImage("test.jpg");
        image.display();
        System.out.println();
        image.display();
    }
}
