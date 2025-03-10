package Strategy;

public class StrategyB extends Strategy {


    public StrategyB() {
        super();
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
