package Strategy;

import Generators.EmpiricData;
import Generators.EmpiricDiscrete;
import Generators.UniformContinuous;
import Generators.UniformDiscrete;

import java.util.ArrayList;
import java.util.Arrays;

public class Strategy implements IStrategy {
    protected final int SUPRESSORS_BUY_COUNT = 100;
    protected final int BREAK_PLATES_BUY_COUNT = 200;
    protected final int HEADLIGHTS_BUY_COUNT = 150;

    protected int suppressors;
    protected int breakPlates;
    protected int headlights;

    protected final double SUPPRESSORS_PRICE = 0.2;
    protected final double BREAK_PLATES_PRICE = 0.3;
    protected final double HEADLIGHTS_PRICE = 0.25;

    protected final double FINE_FOR_ONE = 0.3;

    protected ArrayList<EmpiricData<Integer>> empiricDataArrayList;


    protected UniformDiscrete demand1Dist;
    protected UniformDiscrete demand2Dist;
    protected EmpiricDiscrete demand3Dist;

    protected UniformContinuous decisionMaker;

    protected Strategy() {
        this.empiricDataArrayList = new ArrayList<>(Arrays.asList(
                new EmpiricData<>(30, 60, 0.2),
                new EmpiricData<>(60, 100, 0.4),
                new EmpiricData<>(100, 140, 0.3),
                new EmpiricData<>(140, 160, 0.1)
        ));
        this.demand1Dist = new UniformDiscrete(50,100);
        this.demand2Dist = new UniformDiscrete(60,250);
        this.demand3Dist = new EmpiricDiscrete(empiricDataArrayList);
        this.decisionMaker = new UniformContinuous(0,1);
    }

    @Override
    public double  algorithm(double totalCost) {
        return totalCost;

    }


}
