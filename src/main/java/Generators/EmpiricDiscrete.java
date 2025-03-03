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
        Random random = listOfRandoms.get(index);
        int a = listOfValues.get(index).getInterval().first();
        int b = listOfValues.get(index).getInterval().second();
        return random.nextInt(a, b + 1);
    }

}
