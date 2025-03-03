package Generators;

import java.util.ArrayList;

public class EmpiricContinuous extends Empiric<Double>{
    public EmpiricContinuous(ArrayList<EmpiricData<Double>> listOfData, int seed) {
        super(listOfData, seed);
    }

    public EmpiricContinuous(ArrayList<EmpiricData<Double>> listOfData) {
        super(listOfData);
    }

    @Override
    protected Double generateValue(int index) {
        BaseGenerator<Double> random = listOfRandoms.get(index);
        return random.sample();
    }

    @Override
    protected void initializeRandom(Double a, Double b, Integer seed) {
        if (seed != null) {
            listOfRandoms.add(new UniformContinuous(a ,b, seed));
        } else {
            listOfRandoms.add(new UniformContinuous(a,b));
        }
    }

}
