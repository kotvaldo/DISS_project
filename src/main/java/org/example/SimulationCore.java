package org.example;

import java.util.ArrayList;

public abstract class SimulationCore {
    double sum = 0.0;
    ArrayList<Double> partialResults = new ArrayList<>();
    public double runSimulation(int repCount) {
        this.sum = 0.0;
        this.partialResults.clear();
        for(int i = 0; i < repCount; i++) {
            experiment();
            partialResults.add(calculateResult(i));
        }
        return calculateResult(repCount);
    }
    protected abstract void experiment();
    protected abstract double calculateResult(int repCount);

}
