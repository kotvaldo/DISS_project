package Statistics;

public class WeightedStatistic implements Statistic {
    protected double weightedSum;
    protected double weightSum;

    public WeightedStatistic() {
        clear();
    }

    public void add(double value, double weight) {
        weightedSum += value * weight;
        weightSum += weight;
    }

    @Override
    public void add(double value) {
        add(value, 1);
    }

    @Override
    public double mean() {
        if (weightSum == 0) return 0;
        return weightedSum / weightSum;
    }

    @Override
    public String confidenceInterval() {
        return "";
    }

    @Override
    public void clear() {
        weightedSum = 0;
        weightSum = 0;
    }
}
