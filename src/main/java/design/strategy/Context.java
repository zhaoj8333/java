package design.strategy;

import design.strategy.strgys.Strategy;

public class Context {
    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public int execStrategy(int a, int b) {
        return strategy.doOperation(a, b);
    }
}
