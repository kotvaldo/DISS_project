package Strategy;

import java.util.ArrayList;

public class StrategyC extends Strategy {
    public StrategyC() {
        super();
    }
    @Override
    public double algorithm(double totalCost) {
        return totalCost;
    }
}
