package Strategy;

import java.util.ArrayList;

public class StrategyB extends Strategy {
    public StrategyB() {
        super();
    }
    @Override
    public double algorithm(double totalCost) {
        return totalCost;
    }
}
