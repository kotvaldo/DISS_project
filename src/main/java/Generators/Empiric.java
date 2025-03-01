package Generators;

import Utility.Pair;

import java.util.ArrayList;
import java.util.Random;

public abstract class Empiric<T> extends BaseGenerator<T> {
    protected ArrayList<EmpiricData<T>> listOfValues;
    protected ArrayList<Random> listOfRandoms;
    protected int maxIndex;

    protected Empiric(ArrayList<EmpiricData<T>> listOfData, int seed) {
        super(seed);
        listOfValues = listOfData;
        maxIndex = listOfValues.size() - 1;
        listOfRandoms = new ArrayList<>();
        for (EmpiricData<T> listOfValue : listOfValues) {
            if (listOfValue.getSeed() != -1) {
                listOfRandoms.add(new Random(listOfValue.getSeed()));
            } else {
                listOfRandoms.add(new Random(this.nextSeed()));
            }
        }
    }

    protected Empiric(ArrayList<EmpiricData<T>> listOfData) {
        super();
        listOfValues = listOfData;
        maxIndex = listOfValues.size() - 1;
        listOfRandoms = new ArrayList<>();
        for (EmpiricData<T> listOfValue : listOfValues) {
            if (listOfValue.getSeed() != -1) {
                listOfRandoms.add(new Random(listOfValue.getSeed()));
            } else {
                listOfRandoms.add(new Random(this.nextSeed()));
            }
        }

    }
    @Override
    public abstract T sample();

    public boolean checkProbabilities() {
        double sum = listOfValues.stream()
                .mapToDouble(EmpiricData::getProbability)
                .sum();

        return Math.abs(sum - 1.0) < 1e-6;
    }

    public boolean checkOverlapping() {
        return false;
    }


}
