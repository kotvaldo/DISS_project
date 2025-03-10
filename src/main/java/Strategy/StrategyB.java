package Strategy;

import Generators.EmpiricContinuous;
import Generators.EmpiricData;
import Generators.UniformContinuous;

import java.util.ArrayList;
import java.util.Arrays;

public class StrategyB extends Strategy {
    protected EmpiricContinuous supplier2Before;
    protected EmpiricContinuous supplier2After;
    protected ArrayList<EmpiricData<Double>> empiricData;
    protected ArrayList<EmpiricData<Double>> empiricData2;

    public StrategyB() {
        super();
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
    protected void setUpSupply(int curr_week) {
        if (curr_week <= 15) {
            if (decisionMaker.sample() < supplier2Before.sample()) added_count_1 = this.SUPRESSORS_BUY_COUNT;
            if (decisionMaker.sample() < supplier2Before.sample())  added_count_2 = this.BREAK_PLATES_BUY_COUNT;
            if (decisionMaker.sample() < supplier2Before.sample())   added_count_3 = this.HEADLIGHTS_BUY_COUNT;

        } else {
            if (decisionMaker.sample() < supplier2After.sample())   added_count_1 = this.SUPRESSORS_BUY_COUNT;

            if (decisionMaker.sample() < supplier2After.sample())  added_count_2 = this.BREAK_PLATES_BUY_COUNT;
            if (decisionMaker.sample() < supplier2After.sample())   added_count_3 = this.HEADLIGHTS_BUY_COUNT;

        }

    }


}
