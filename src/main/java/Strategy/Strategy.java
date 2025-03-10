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

    protected final UniformContinuous supplier1Before;
    protected final UniformContinuous supplier1After;
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
        this.supplier1Before = new UniformContinuous(0.1,0.7);
        this.supplier1After = new UniformContinuous(0.3,0.95);
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
    public double algorithm(double totalCost, ArrayList<Double> dailyCostsArrayList) {
        clearAll();

        for (int i = 0; i < 30; i++) { // 30 weeks
            int curr_week = i + 1;

            this.added_count_1 = 0;
            this.added_count_2 = 0;
            this.added_count_3 = 0;

            setUpSupply(curr_week);

            this.suppressors += added_count_1;
            this.breakPlates += added_count_2;
            this.headlights += added_count_3;

            double weeklyCost = 0.0;

            weeklyCost += addStorageCosts(4, dailyCostsArrayList);

            this.suppressors -= this.demand1Dist.sample();
            this.breakPlates -= this.demand2Dist.sample();
            this.headlights -= this.demand3Dist.sample();


            double penalty = calculatePenalty();
            double fridayCost = (this.suppressors * this.SUPPRESSORS_PRICE
                    + this.breakPlates * this.BREAK_PLATES_PRICE
                    + this.headlights * this.HEADLIGHTS_PRICE) + penalty;


            if(dailyCostsArrayList.isEmpty()) {
                dailyCostsArrayList.add(fridayCost);
            } else {
                dailyCostsArrayList.add(fridayCost + dailyCostsArrayList.getLast());
            }
            weeklyCost += fridayCost;

            weeklyCost += addStorageCosts(2, dailyCostsArrayList);

            totalCost += weeklyCost;
        }

        return totalCost;
    }


    private double addStorageCosts(int days, ArrayList<Double> costList) {
        double totalStorageCost = 0.0;
        for (int j = 0; j < days; j++) {
            double dailyCost = this.suppressors * this.SUPPRESSORS_PRICE
                    + this.breakPlates * this.BREAK_PLATES_PRICE
                    + this.headlights * this.HEADLIGHTS_PRICE;

            if(costList.isEmpty()) {
                costList.add(dailyCost);
            } else {
                costList.add(dailyCost + costList.getLast());
            }
            totalStorageCost += dailyCost;
        }
        return totalStorageCost;
    }




    private double calculatePenalty() {
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
        return penalty;
    }


    protected abstract void setUpSupply(int curr_week);

    protected void clearAll() {
        this.suppressors = 0;
        this.breakPlates = 0;
        this.headlights = 0;
    }

}
