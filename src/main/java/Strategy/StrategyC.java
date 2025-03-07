package Strategy;

import Generators.EmpiricContinuous;
import Generators.EmpiricData;
import Generators.UniformContinuous;

import java.util.ArrayList;
import java.util.Arrays;

public class StrategyC extends Strategy {
    private EmpiricContinuous supplier2Before;
    private  EmpiricContinuous supplier2After;
    private ArrayList<EmpiricData<Double>> empiricData;
    private ArrayList<EmpiricData<Double>> empiricData2;
    private UniformContinuous supplier1Before11;
    private  UniformContinuous supplier1After11;
    public StrategyC() {
        super();
        this.supplier1Before11 = new UniformContinuous(0.1,0.7);
        this.supplier1After11 = new UniformContinuous(0.3,0.95);
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
    public double algorithm(double totalCost) {
        suppressors = 0;
        headlights = 0;
        breakPlates = 0;
        for (int i = 0; i < 30; i++) {
            int curr_day = i + 1;
            int added_count_1 = 0, added_count_2 = 0, added_count_3 = 0;


            if (curr_day % 2 == 0) {
                if(curr_day <= 10) {
                    if (decisionMaker.sample() < supplier1Before11.sample()) {
                        added_count_1 = this.SUPRESSORS_BUY_COUNT;
                        added_count_2 = this.BREAK_PLATES_BUY_COUNT;
                        added_count_3 = this.HEADLIGHTS_BUY_COUNT;
                    }
                } else {
                    if (decisionMaker.sample() < supplier1After11.sample()) {
                        added_count_1 = this.SUPRESSORS_BUY_COUNT;
                        added_count_2 = this.BREAK_PLATES_BUY_COUNT;
                        added_count_3 = this.HEADLIGHTS_BUY_COUNT;
                    }
                }

            } else {
                if(curr_day <= 15) {
                    if (decisionMaker.sample() < supplier2Before.sample()) {
                        added_count_1 = this.SUPRESSORS_BUY_COUNT;
                        added_count_2 = this.BREAK_PLATES_BUY_COUNT;
                        added_count_3 = this.HEADLIGHTS_BUY_COUNT;
                    }
                } else {
                    if (decisionMaker.sample() < supplier2After.sample()) {
                        added_count_1 = this.SUPRESSORS_BUY_COUNT;
                        added_count_2 = this.BREAK_PLATES_BUY_COUNT;
                        added_count_3 = this.HEADLIGHTS_BUY_COUNT;
                    }
                }

            }

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

            double penalty = 0;
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
}
