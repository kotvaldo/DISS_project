package Strategy;

import Generators.*;

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

    protected EmpiricContinuous supplier2Before;
    protected EmpiricContinuous supplier2After;
    protected ArrayList<EmpiricData<Double>> empiricData;
    protected ArrayList<EmpiricData<Double>> empiricData2;

    protected UniformContinuous decisionMaker;

    protected Strategy() {
        this.empiricDataArrayList = new ArrayList<>(Arrays.asList(
                new EmpiricData<>(30, 60, 0.2),
                new EmpiricData<>(60, 100, 0.4),
                new EmpiricData<>(100, 140, 0.3),
                new EmpiricData<>(140, 160, 0.1)
        ));
        this.demand1Dist = new UniformDiscrete(50,101);
        this.demand2Dist = new UniformDiscrete(60,251);
        this.demand3Dist = new EmpiricDiscrete(empiricDataArrayList);
        this.decisionMaker = new UniformContinuous(0,1);

        empiricData = new ArrayList<>(Arrays.asList(
                new EmpiricData<>(0.05, 0.1, 0.4),
                new EmpiricData<>(0.1, 0.5, 0.3),
                new EmpiricData<>(0.5, 0.7, 0.2),
                new EmpiricData<>(0.7, 0.8, 0.06),
                new EmpiricData<>(0.8, 0.95, 0.04)
        ));

        empiricData2 = new ArrayList<>(Arrays.asList(
                new EmpiricData<>(0.05, 0.1, 0.2),
                new EmpiricData<>(0.1, 0.5, 0.4),
                new EmpiricData<>(0.5, 0.7, 0.3),
                new EmpiricData<>(0.7, 0.8, 0.06),
                new EmpiricData<>(0.8, 0.95, 0.04)
        ));
        this.supplier2Before = new EmpiricContinuous(empiricData);
        this.supplier2After = new EmpiricContinuous(empiricData2);

    }

    @Override
    public double  algorithm(double totalCost) {
        return totalCost;

    }


}
