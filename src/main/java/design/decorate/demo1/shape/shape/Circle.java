package design.decorate.demo1.shape.shape;

import design.decorate.demo1.shape.Shape;

public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("shape: circle");
    }
}
