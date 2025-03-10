package Strategy;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface IStrategy {
    double algorithm(double totalCost, ArrayList<Double> weeklyCosts);

}
