package Strategy;

import java.util.ArrayList;

public class CustomStrategy implements IStrategy {
    public CustomStrategy() {
        super();
    }

    @Override
    public double algorithm(double totalCost) {
        return totalCost;
    }
}
