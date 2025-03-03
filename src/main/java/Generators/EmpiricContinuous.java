package Generators;

import java.util.ArrayList;
import java.util.Random;

public class EmpiricContinuous extends Empiric<Double>{
    public EmpiricContinuous(ArrayList<EmpiricData<Double>> listOfData, int seed) {
        super(listOfData, seed);
    }

    public EmpiricContinuous(ArrayList<EmpiricData<Double>> listOfData) {
        super(listOfData);
    }
    @Override
    public Double sample() {
        double probability = this.baseRandom.nextDouble();
        return algorithm(probability);

    }

    @Override
    public Double sampleWithProb(double probability) {
        return algorithm(probability);
    }

    private double algorithm(double probability) {
        int index = 0;

        double cumulativeProbability = 0.0;
        for (EmpiricData<Double> data : listOfValues) {
            cumulativeProbability += data.getProbability();

            if (probability < cumulativeProbability) {
                Random random = this.listOfRandoms.get(index);
                double a = listOfValues.get(index).getInterval().first();
                double b = listOfValues.get(index).getInterval().second();
                return a + (b - a) * random.nextDouble();
            }
            index++;
        }
        throw new IllegalStateException("Some error happend when returning sample");
    }
}
