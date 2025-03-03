package Generators;

import java.util.ArrayList;
import java.util.Random;

public class EmpiricDiscrete extends Empiric<Integer>{

    public EmpiricDiscrete(ArrayList<EmpiricData<Integer>> listOfData, int seed) {
        super(listOfData, seed);
    }

    public EmpiricDiscrete(ArrayList<EmpiricData<Integer>> listOfData) {
        super(listOfData);
    }

    @Override
    public Integer sample() {
        double probability = this.baseRandom.nextDouble();
        return algorithm(probability);

    }

    @Override
    public Integer sampleWithProb(double probability) {
        return algorithm(probability);
    }

    private int algorithm(double probability) {
        int index = 0;

        double cumulativeProbability = 0.0;
        for (EmpiricData<Integer> data : listOfValues) {
            cumulativeProbability += data.getProbability();

            if (probability < cumulativeProbability) {
                Random random = this.listOfRandoms.get(index);
                int a = listOfValues.get(index).getInterval().first();
                int b = listOfValues.get(index).getInterval().second();
                return random.nextInt(a, b + 1);
            }
            index++;
        }
        throw new IllegalStateException("Some error happend when returning sample");
    }
}
