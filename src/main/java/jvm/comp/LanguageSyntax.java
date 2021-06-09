package jvm.comp;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * 1. 语法糖
 * 2. 自动拆装箱
 * 3. 泛型集合取值: 泛型擦除, 部分泛型信息会在编译后丢失
 * 4. 可变参数
 * 5. foreach循环: 转化为for循环或iterator
 * 6. switch字符串: case str.hashCode(), 或 case int
 * 7. switch枚举
 * 8. Enum: public final class Sex extends Enum<Sex> {
 *     public static final Sex MALE;
 *     public static final Sex FEMALE;
 *     public static final Sex[] $VALUES;
 *     static {
 *         MALE = new Sex("MALE, 0);
 *         FEMALE = new Sex("FEMALE, 1);
 *         $VALUES = new Sex[]{MALE, FEMALE);
 *     }
 *      不能直接被实例化, private构造方法仅仅被编译器使用
 * }
 * 9. try - with - resources: jdk7新增
 *    资源对象 实现AutoClosable接口即可
 *    try (资源变量 = 创建资源变量) {
 *
 *    } catch() {
 *
 *    }
 *
 * 10. 方法重写时的桥接方法
 *     方法重写时对返回值的情况: 父子类返回值完全一致, 子类返回值是父类返回值的子类
 *     对于子类, 编译器 ....
 *
 * 11. 匿名内部类: 会生成额外的类, 如果引用了外部的一个final变量, 匿名类会将这个变量以成员变量的形式存储起来
 *     这就解释了为何匿名内部类引用外部局部变量时,局部变量必须是final的
 */
@SuppressWarnings("all")
public class LanguageSyntax {
    public static void main(String[] args) {
//        boxUnbox();
//        genericErase();
//        switchEnum(Sex.MALE);
//        overrideBridge();
        innerClass(1);
    }

    /**
     * 如果不是final的, 如果被内部类修改, 造成匿名内部类属性与传参值不一致
     * 这是为了使外部与内部只保持一致
     */
    private static void innerClass(final int x) {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
//                x = 4;
//                local varialbes referenced from an inner class must be final or effectively final
                System.out.println("run: " + x);
            }
        };
        runnable.run();
    }

    class P {
        public Number m() {
            return 1;
        }
    }

    class C extends P {
        @Override
        public Integer m() {
            return 2;
        }

        // 此方法才是真正重写了父类m方法的方法, 该方法会间接调用 m()
        // 编译器生成一个合成的桥接方法, 仅限于jvm内部使用, 与原方法重名 参数一致
//        public synthetic bridge Number m() {
//            return m();
//        }
    }

    private static void overrideBridge() {
        for (Method m : C.class.getDeclaredMethods()) {
            System.out.println(m);
        }
    }

    private static enum Sex {
        MALE, FEMALE
    }

    /**
     * Enum 会被便以为合成类(仅jvm可见), 用来映射枚举ordinal与数组元素的关系
     * 枚举的ordinal表示枚举对象序号从0开始, 即MALE 的ordinal() == 0, FEMALE的ordianl() == 1
     * 数组大小为枚举个数 里面存储case用来对比数字
     */
    private static void switchEnum(Sex sex) {
        switch (sex) {
            case MALE:
                System.out.println("男"); break;
            case FEMALE:
                System.out.println("女"); break;
        }
    }

    private static void genericErase() {
        final ArrayList<Integer> list = new ArrayList<>();
        list.add(10);
        final Integer i = list.get(0);
    }

    private static void boxUnbox() {
        Integer x = 1;
        int y = 2;
    }
}
