package design.factory.fac1;

import design.factory.Bmw;
import design.factory.Car;

public class BmwFactory implements Factory {
    @Override
    public Car createCar() {
        return new Bmw();
    }
}
