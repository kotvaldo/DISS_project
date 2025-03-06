package Strategy;

import SimulationCore.MonteCarlo;

import java.util.ArrayList;

public interface IStrategy {
    void algorithm(double totalCost, ArrayList<Integer> totalProducts);

}
