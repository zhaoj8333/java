package design.strategy.strgys;

public class OperationMulti implements Strategy {
    @Override
    public int doOperation(int a, int b) {
        return a * b;
    }
}
