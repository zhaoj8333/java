package design.strategy;

public class OperationSubstract implements IStrategy {
    @Override
    public int doOperation(int a, int b) {
        return a - b;
    }
}
