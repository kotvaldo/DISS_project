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
        BaseGenerator<Double> random = listOfRandoms.get(index);
        return random.sample();
    }

    @Override
    protected void initializeRandoms() {
        maxIndex = listOfValues.size() - 1;
        listOfRandoms = new ArrayList<>();
        for (EmpiricData<Double> listOfValue : listOfValues) {
            if (listOfValue.getSeed() != -1) {
                listOfRandoms.add(new UniformContinuous(listOfValue.getInterval().first(),listOfValue.getInterval().second(),listOfValue.getSeed()));
            } else {
                listOfRandoms.add(new UniformContinuous(listOfValue.getInterval().first(),listOfValue.getInterval().second()));
            }
        }
    }

}
