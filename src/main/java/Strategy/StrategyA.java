package Strategy;

import Generators.UniformContinuous;

public class StrategyA extends Strategy {
    private final UniformContinuous supplier1Before;
    private final UniformContinuous supplier1After;
    public StrategyA() {
        super();
        this.supplier1Before = new UniformContinuous(0.1,0.7);
        this.supplier1After = new UniformContinuous(0.3,0.95);
    }


    @Override
    protected void setUpSupply(int curr_day) {
        if (curr_day <= 10) {
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
