package Statistics;

public class NonWeightedStatistic implements Statistic {
    private int count = 0;
    private double sum = 0.0;
    private double sumSquares = 0.0;

    public NonWeightedStatistic() {}

    @Override
    public void add(double value) {
        count++;
        sum += value;
        sumSquares += value * value;
    }

    @Override
    public double mean() {
        if (count == 0) return 0;
        return sum / count;
    }

    public double variance() {
        if (count < 2) return 0;
        return (sumSquares - (sum * sum) / count) / (count - 1);
    }

    public double standardDeviation() {
        return Math.sqrt(variance());
    }

    @Override
    public String confidenceInterval() {
        if (count < 2) return "n/a";

        double mean = mean();
        double stdDev = standardDeviation();
        double z = 1.96; // 95% confidence interval

        double marginError = z * stdDev / Math.sqrt(count);
        double lower = mean - marginError;
        double upper = mean + marginError;

        return String.format("[%.4f ; %.4f]", lower, upper);
    }

    @Override
    public void clear() {
        count = 0;
        sum = 0.0;
        sumSquares = 0.0;
    }
}
