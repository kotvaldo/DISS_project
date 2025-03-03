package Generators;

import java.util.ArrayList;

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
    protected void initializeRandom(Integer a, Integer b, Integer seed) {
        if (seed != null) {
            listOfRandoms.add(new UniformDiscrete(a ,b, seed));
        } else {
            listOfRandoms.add(new UniformDiscrete(a,b));
        }
    }
}
