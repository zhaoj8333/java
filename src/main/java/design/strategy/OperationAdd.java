package design.strategy;

public class OperationAdd implements IStrategy {
    @Override
    public int doOperation(int a, int b) {
        return a + b;
    }
}
