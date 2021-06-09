package design.strategy;

public class OperationMultiply implements IStrategy {
    @Override
    public int doOperation(int a, int b) {
        return a * b;
    }
}
