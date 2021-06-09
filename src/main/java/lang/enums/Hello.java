package lang.enums;

/**
 * 枚举定义的枚举类默认继承了java.lang.Enum类,并实现了 java.lang.Serializable和java.lang.Comparable
 * 枚举不能继承其他类, 也被继承,但可以实现一个或多个接口
 */

@SuppressWarnings("all")
public class Hello {

    private static Week Week;

    public static void main(String[] args) {
//        test1();
        getValue();
//        Color.GREEN.colorInfo();
    }

    private static void getValue() {
//        Color[] values = Color.values();
//        for (Color value : values) {
//            System.out.println(value);
//        }

//        System.out.println(Color.valueOf("RED"));
//        System.out.println(Color.RED.ordinal());  // ordinal找到每个枚举常量的索引
//        System.out.println(Color.GREEN.ordinal());
        final lang.enums.Week[] values1 = lang.enums.Week.values();
        for (Week value : values1) {
            System.out.println(value);
        }
//        System.out.println(Week.valueOf("MONDAY"));
//        System.out.println(Week.FRIDAY.ordinal());
        lang.enums.Week.FRIDAY.reSchedule();
    }

    private static void test1() {
        Color c = Color.RED;
        switch (c) {
            case RED:
                System.out.println("红色");
            case GREEN:
                System.out.println("绿色");
            case BLUE:
                System.out.println("蓝色");
        }
        System.out.println(c);
    }
}

