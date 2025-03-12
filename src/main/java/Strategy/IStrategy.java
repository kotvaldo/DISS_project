package Strategy;

import java.util.ArrayList;

public interface IStrategy {
    double algorithm(double totalCost, ArrayList<Double> weeklyCosts, ArrayList<Double> dailyFineCosts);

}
