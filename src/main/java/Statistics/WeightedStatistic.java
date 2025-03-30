package Statistics;

public class WeightedStatistic implements Statistic {
    protected double weightedSum;
    protected double weightSum;
    protected double weightedSumSquares;

    public WeightedStatistic() {
        clear();
    }

    public void add(double value, double weight) {
        weightedSum += value * weight;
        weightedSumSquares += value * value * weight;
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
    public int meanInt() {
        if (weightSum == 0) return 0;
        return (int) Math.round(weightedSum / weightSum);
    }

    public double variance() {
        if (weightSum <= 1e-8) return 0;
        double mean = mean();
        return (weightedSumSquares / weightSum) - (mean * mean);
    }

    public double standardDeviation() {
        return Math.sqrt(variance());
    }

    @Override
    public String confidenceInterval() {
        if (weightSum < 30) return "n/a";
        double mean = mean();
        double stdDev = standardDeviation();
        double z = 1.96;
        double marginError = z * stdDev / Math.sqrt(weightSum);
        return String.format("[%.4f ; %.4f]", mean - marginError, mean + marginError);
    }

    @Override
    public void clear() {
        weightedSum = 0;
        weightedSumSquares = 0;
        weightSum = 0;
    }
}
