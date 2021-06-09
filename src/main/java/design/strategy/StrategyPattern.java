package design.strategy;

import java.math.BigDecimal;

public class StrategyPattern {
    public BigDecimal calculatePrice(String customType) {
        if ("普通会员".equals(customType)) {
            System.out.println("9折");
            return new BigDecimal("90");
        } else if ("vip会员".equals(customType)) {
            System.out.println("8折");
            return new BigDecimal("80");
        } else if ("超级会员".equals(customType)) {
            System.out.println("5折");
            return new BigDecimal("50");
        }
        return new BigDecimal("100");
        // 问题： 不同客户的报价算法 放在了一起，方法很庞大

        // 开闭原则：(OCP) 软件中的对象（类，模块，函数等）对于扩展是开放的，但对于修改是封闭的
        //     对扩展开放：模块行为可扩展
        //     对修改关闭：对模块进行扩展时，不必修改模块源代码或二进制代码

        /**
         * 策略模式：
         *     策略模式定义一系列的算法，将每一个算法封装起来，使得每个算法可以互相替代，使算法本身和算法使用者分隔开
         *
         * 一个类的行为或其算法可以在运行时更改。
         *
         * 创建各种策略的对象和一个行为随着策略对象改变而改变的context对象，策略对象改变context对象的执行算法
         *
         * 意图： 定义一系列的算法并封装起来，使其可以互相替换
         * 主要解决： 多个if else带来的复杂和难以维护
         *
         * 优点： 算法自由切换、 避免多条件判断，扩展性好
         * 缺点： 策略类多，策略类对外暴露
         *
         */
    }



}

