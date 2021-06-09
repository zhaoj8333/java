package design.decorate.demo1;

import design.decorate.demo1.shape.Shape;
import design.decorate.demo1.shape.decorator.RedShapeDecorator;
import design.decorate.demo1.shape.shape.Circle;
import design.decorate.demo1.shape.shape.Rectagle;

/*
        就增加功能来说,装饰器模式比生成子类更灵活
        使用继承方式实现,子类会很膨胀
        在不想增加子类的情况下扩展现有类

        通过增加新的类来扩展现有类, 不用修改已有的类

    装饰器模式:
        允许向现有对象添加新功能,同时不改变其结构, 属于结构型模式, 是作为现有类的一个包装

 */
@SuppressWarnings("all")
public class Demo1 {
    public static void main(String[] args) {
        Shape circle = new Circle();
        RedShapeDecorator redCircle    = new RedShapeDecorator(new Circle());
        RedShapeDecorator redRectangle = new RedShapeDecorator(new Rectagle());

//        circle.draw();
        redCircle.draw();
        System.out.println();
        redRectangle.draw();
    }
}
