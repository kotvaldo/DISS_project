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
    protected Double generateValue(int index) {
        Random random = listOfRandoms.get(index);
        double a = listOfValues.get(index).getInterval().first();
        double b = listOfValues.get(index).getInterval().second();
        return a + (b - a) * random.nextDouble();
    }

}
