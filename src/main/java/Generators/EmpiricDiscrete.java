package Generators;

import java.util.ArrayList;
import java.util.Random;

public class EmpiricDiscrete extends Empiric<Integer>{

    protected EmpiricDiscrete(ArrayList<EmpiricData<Integer>> listOfData, int seed) {
        super(listOfData, seed);
    }

    public EmpiricDiscrete(ArrayList<EmpiricData<Integer>> listOfData) {
        super(listOfData);
    }

    @Override
    public Integer sample() {
        if(checkProbabilities()) throw new IllegalStateException("Sum of probabilities is not 1");
        if(checkOverlapping()) throw new IllegalStateException("Overlapping intervals");
        double probability = this.baseRandom.nextDouble();
        int index = 0;

        double cumulativeProbability = 0.0;
        for (EmpiricData<Integer> data : listOfValues) {
            cumulativeProbability += data.getProbability();

            if (probability < cumulativeProbability) {
                Random random = this.listOfRandoms.get(index);
                int a = listOfValues.get(index).getInterval().first();
                int b = listOfValues.get(index).getInterval().second();
                return (int) (a + (b-a) *  random.nextDouble());
            }
            index++;
        }
        throw new IllegalStateException("Some error happend when returning sample");

    }
}
