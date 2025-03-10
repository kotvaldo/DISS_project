package Strategy;

import Generators.UniformContinuous;

public class StrategyA extends Strategy {

    public StrategyA() {
        super();
    }


    @Override
    protected void setUpSupply(int curr_week) {
        if (curr_week <= 10) {
            if (decisionMaker.sample() < supplier1Before.sample()) {
                added_count_1 = this.SUPRESSORS_BUY_COUNT;
                added_count_2 = this.BREAK_PLATES_BUY_COUNT;
                added_count_3 = this.HEADLIGHTS_BUY_COUNT;
            }
        } else {
            if (decisionMaker.sample() < supplier1After.sample()) {
                added_count_1 = this.SUPRESSORS_BUY_COUNT;
                added_count_2 = this.BREAK_PLATES_BUY_COUNT;
                added_count_3 = this.HEADLIGHTS_BUY_COUNT;
            }
        }
    }


}
