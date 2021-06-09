package design.rc;

import design.rc.chain.AbstractLogger;
import design.rc.chain.ConsoleLogger;
import design.rc.chain.ErrorLogger;
import design.rc.chain.FileLogger;

/*
    为了避免请求发送者与接受者耦合,然多个对象都有可能接收请求,这些对象形成一条链,沿着这条链请求,知道有对象处理它为止
    主要解决:
        职责链上的处理者负责处理请求,客户只需将请求发送到职责链上即可,无须关心处理步骤

    拦截的类都实现统一接口

    优势:
        降低耦合,简化对象,增强职责指派灵活性,可以动态增删责任,
    劣势:
        不能保证请求一定被接受,性能会受到影响,循环调用可能出现,有碍于除错
 */
@SuppressWarnings("all")
public class ReponsiblityChainTest {

    public static void main(String[] args) {
        AbstractLogger chainLogger = getChain();
        chainLogger.logMessage(AbstractLogger.INFO, "info");
        System.out.println();
        chainLogger.logMessage(AbstractLogger.DEBUG, "debug");
        System.out.println();
        chainLogger.logMessage(AbstractLogger.ERROR, "error");

    }

    private static AbstractLogger getChain() {
        AbstractLogger errorLogger   = new ErrorLogger(AbstractLogger.ERROR);
        AbstractLogger fileLogger    = new FileLogger(AbstractLogger.DEBUG);
        AbstractLogger consoleLogger = new ConsoleLogger(AbstractLogger.INFO);

        errorLogger.setNextLogger(fileLogger);
        fileLogger.setNextLogger(consoleLogger);

        return errorLogger;
    }
}
