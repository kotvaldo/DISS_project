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
    protected Integer generateValue(int index) {
        BaseGenerator<Integer> random = listOfRandoms.get(index);
        return random.sample();
    }

    @Override
    protected void initializeRandoms() {
        maxIndex = listOfValues.size() - 1;
        listOfRandoms = new ArrayList<>();
        for (EmpiricData<Integer> listOfValue : listOfValues) {
            if (listOfValue.getSeed() != -1) {
                listOfRandoms.add(new UniformDiscrete(listOfValue.getInterval().first(),listOfValue.getInterval().second(),listOfValue.getSeed()));
            } else {
                listOfRandoms.add(new UniformDiscrete(listOfValue.getInterval().first(),listOfValue.getInterval().second()));
            }
        }
    }
}
