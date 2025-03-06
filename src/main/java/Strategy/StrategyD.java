package Strategy;

import java.util.ArrayList;

public class StrategyD extends Strategy {
    public StrategyD() {
        super();
    }

    @Override
    public double algorithm(double totalCost) {
        return totalCost;
    }

}
