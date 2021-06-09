package design.factory.fac2;

public class FacTest {
    public static void main(String[] args) {
        ShapeFactory shapeFactory = new ShapeFactory();
        Shape rectangle = shapeFactory.get("rectangle");
        rectangle.draw();

    }
}
