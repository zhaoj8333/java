package design.decorate.demo1.shape.decorator;

import design.decorate.demo1.shape.Shape;

public class RedShapeDecorator extends BaseShapeDecorator {

    public RedShapeDecorator(Shape shape) {
        super(shape);
    }

    @Override
    public void draw() {
        decoratedShape.draw();
        setRecBorder(decoratedShape);

    }

    private void setRecBorder(Shape decoratedShape) {
        System.out.println("border color: red");
    }
}
