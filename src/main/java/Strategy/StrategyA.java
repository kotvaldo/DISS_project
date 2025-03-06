package Strategy;

import Generators.UniformContinuous;

import java.util.ArrayList;

public class StrategyA extends Strategy {
    private UniformContinuous supplier1Before11;
    private UniformContinuous supplier1After11;
    public StrategyA() {
        super();
        this.supplier1Before11 = new UniformContinuous(0.1,0.7);
        this.supplier1After11 = new UniformContinuous(0.3,0.95);
    }
    @Override
    public void algorithm(double totalCost, ArrayList<Integer> totalProducts) {
        for (int i = 0; i < 30; i++) {
            int curr_day = i+1;
            if(curr_day <= 10) {

            } else {

            }
        }
    }

}
