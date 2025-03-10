package Strategy;

import Generators.*;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Strategy implements IStrategy {
    protected final int SUPRESSORS_BUY_COUNT = 100;
    protected final int BREAK_PLATES_BUY_COUNT = 200;
    protected final int HEADLIGHTS_BUY_COUNT = 150;

    protected int suppressors;
    protected int breakPlates;
    protected int headlights;

    protected final double SUPPRESSORS_PRICE = 0.2;
    protected final double BREAK_PLATES_PRICE = 0.3;
    protected final double HEADLIGHTS_PRICE = 0.25;

    int added_count_1 = 0, added_count_2 = 0, added_count_3 = 0;

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
        this.demand1Dist = new UniformDiscrete(50,101);
        this.demand2Dist = new UniformDiscrete(60,251);
        this.demand3Dist = new EmpiricDiscrete(empiricDataArrayList);
        this.decisionMaker = new UniformContinuous(0,1);




    }

    @Override
    public double  algorithm(double totalCost) {
        clearAll();
        for (int i = 0; i < 30; i++) {
            int curr_week = i + 1;
            this.added_count_1 = 0;
            this.added_count_2 = 0;
            this.added_count_3 = 0;


            setUpSupply(curr_week);

            this.suppressors += added_count_1;
            this.breakPlates += added_count_2;
            this.headlights += added_count_3;

            totalCost += this.suppressors * 4 * this.SUPPRESSORS_PRICE;
            totalCost += this.breakPlates * 4 * this.BREAK_PLATES_PRICE;
            totalCost += this.headlights * 4 * this.HEADLIGHTS_PRICE;

            int current_demand1 = this.demand1Dist.sample();
            int current_demand2 = this.demand2Dist.sample();
            int current_demand3 = this.demand3Dist.sample();

            this.suppressors -= current_demand1;
            this.breakPlates -= current_demand2;
            this.headlights -= current_demand3;

            double penalty = 0.0;
            if (this.suppressors < 0) {
                penalty += Math.abs(this.suppressors) * FINE_FOR_ONE;
                this.suppressors = 0;
            }
            if (this.breakPlates < 0) {
                penalty += Math.abs(this.breakPlates) * FINE_FOR_ONE;
                this.breakPlates = 0;
            }
            if (this.headlights < 0) {
                penalty += Math.abs(this.headlights) * FINE_FOR_ONE;
                this.headlights = 0;
            }
            totalCost += penalty;

            totalCost += this.suppressors * 3 * this.SUPPRESSORS_PRICE;
            totalCost += this.breakPlates * 3 * this.BREAK_PLATES_PRICE;
            totalCost += this.headlights * 3 * this.HEADLIGHTS_PRICE;

        }
        return totalCost;
    }

    protected abstract void setUpSupply(int curr_week);

    protected void clearAll() {
        this.suppressors = 0;
        this.breakPlates = 0;
        this.headlights = 0;
    }

}
