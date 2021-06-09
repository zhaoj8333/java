package design.strategy;

import design.strategy.strgys.OperationAdd;

/*
    策略型模式:
        一个类的行为或算法可以在运行时更改. 属于行为型模式

        定义一系列的算法,并封装起来,且可以互相互换

        解决了在有多种相似的情况下,使用if ... else带来的复杂和难以维护

        优缺点:
            算法自由切换,避免多条件判断,扩展性好
            策略类增多,所有策略类需要堆外暴露, 如果策略多余四个,需要考虑混合模式,解决策略类膨胀的问题
 */
@SuppressWarnings("all")
public class StrategyTest {
    public static void main(String[] args) {
        Context context = new Context(new OperationAdd());
        System.out.println(context.execStrategy(1, 2));
    }
}
