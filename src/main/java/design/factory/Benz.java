package design.factory;

public class Benz implements Car {
    @Override
    public void drive() {
        System.out.println("开奔驰");
    }
}
