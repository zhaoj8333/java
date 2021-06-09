package design.decorate.demo1.shape.decorator;

import design.decorate.demo1.shape.Shape;

public abstract class BaseShapeDecorator implements Shape {
    protected Shape decoratedShape;

    public BaseShapeDecorator(Shape decoratedShape) {
        this.decoratedShape = decoratedShape;
    }

    @Override
    public void draw() {
        decoratedShape.draw();
    }
}
