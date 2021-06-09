package design.factory;

/**
 * 创建对象
 */
public class CarFactory {
    public static Car createCar(String s) {
        if (s.equals("Benz")) {
            return new Benz();
        } else if (s.equals("Bmw")) {
            return new Bmw();
        }
        // 可能会报错 空指针异常
        return null;
    }
    /**
     * 使用工厂方法
     */

}
