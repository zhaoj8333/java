package design.factory.fac1;

import design.factory.Benz;
import design.factory.Car;

public class BenzFactory implements Factory {
    @Override
    public Car createCar() {
        return new Benz();
    }
}
