package design.facade;

import design.facade.sub_systems.ILetterProcess;
import design.facade.sub_systems.LetterProcessImpl;

/**
 * 门面模式:
 *     外观模式, 一个子系统的外部与其内部的通信必须通过一个统一的对象进行. 门面模式提供一个高层次的接口, 使得子系统更易于使用
 *
 *     Facade: 门面角色, 是一个委托类,客户端调用这个此角色的方法,此角色会将客户端的请求委派给子系统, 客户端不在意子系统的实现细节
 *     SubSystem: 子系统角色, 一个门面可以有多个子系统, 一个子系统是多个类的集合, 子系统并不知道门面的存在
 *
 * 优点:
 *     减少系统的相互依赖
 *     不管子系统如何变化, 只要们面对象不受到影响, 客户端就不需要改动
 *     安全
 *
 * 适用于需要为复杂系统, 子系统提供同一个外界访问接口
 */
public class Facade {

    private ILetterProcess letterp = new LetterProcessImpl();

    public static void main(String[] args) {
        final Facade facade = new Facade();
        facade.sendLetter("xxx", "asfdsjk");
    }

    public void sendLetter(String context, String address) {
        letterp.writeContext("A");
        letterp.fillEnvelop("B");
        letterp.sendLetter();
    }
}
