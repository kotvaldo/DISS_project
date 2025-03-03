package Generators;

import java.util.ArrayList;
import java.util.Random;

public abstract class Empiric<T extends Number> extends BaseGenerator<T> {
    protected ArrayList<EmpiricData<T>> listOfValues;
    protected ArrayList<BaseGenerator<T>> listOfRandoms;
    protected int maxIndex;
    protected double cumulativeProbability;

    protected Empiric(ArrayList<EmpiricData<T>> listOfData, int seed) {
        super(seed);
        listOfValues = listOfData;
        if(!checkProbabilities()) {
            throw new IllegalArgumentException("Probabilities are not correct, not Equals to 1.");
        } else {
            initializeRandoms();
        }
    }

    protected Empiric(ArrayList<EmpiricData<T>> listOfData) {
        super();
        listOfValues = listOfData;
        if(!checkProbabilities()) {
            throw new IllegalArgumentException("Probabilities are not correct, not Equals to 1.");
        } else {
            initializeRandoms();
        }
    }
    
    @Override
    public T sample() {
        double probability = Math.random();
        cumulativeProbability = 0.0;

        for (int i = 0; i < listOfValues.size(); i++) {
            cumulativeProbability += listOfValues.get(i).getProbability();

            if (probability < cumulativeProbability) {
                return generateValue(i);
            }
        }

        throw new IllegalStateException("Error in probability distribution");
    }

    public T sampleWithProb(double probability) {
        cumulativeProbability = 0.0;

        for (int i = 0; i < listOfValues.size(); i++) {
            cumulativeProbability += listOfValues.get(i).getProbability();

            if (probability < cumulativeProbability) {
                return generateValue(i);
            }
        }

        throw new IllegalStateException("Error in probability distribution");
    }

    protected abstract T generateValue(int index);


    private boolean checkProbabilities() {
        double sum = listOfValues.stream()
                .mapToDouble(EmpiricData::getProbability)
                .sum();

        return Math.abs(sum - 1.0) == 0.0;
    }
    protected abstract void initializeRandoms();



}
