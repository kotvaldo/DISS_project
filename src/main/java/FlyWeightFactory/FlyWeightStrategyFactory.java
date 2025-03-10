package FlyWeightFactory;

import Strategy.*;

import java.util.ArrayList;

public class FlyWeightStrategyFactory {
    ArrayList<IStrategy> strategies;
    public FlyWeightStrategyFactory() {
        strategies = new ArrayList<>();
        strategies.add(new StrategyA());
        strategies.add(new StrategyB());
        strategies.add(new StrategyC());
        strategies.add(new StrategyD());
        strategies.add(new CustomStrategy());
    }

    public IStrategy getStrategy(String strategyName) {
        return switch (strategyName) {
            case "StrategyA" -> strategies.getFirst();
            case "StrategyB" -> strategies.get(1);
            case "StrategyC" -> strategies.get(2);
            case "StrategyD" -> strategies.get(3);
            case "Custom" -> strategies.getLast();
            default -> null;
        };
    }

}
