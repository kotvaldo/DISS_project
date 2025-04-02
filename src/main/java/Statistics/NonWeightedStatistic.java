package Statistics;

public class NonWeightedStatistic implements Statistic {
    private int count;
    private double sum;
    private double sumSquares;
    private double lower;
    private double upper;
    public NonWeightedStatistic() {
        count = 0;
        sum = 0.0;
        sumSquares = 0.0;
        lower = 0.0;
        upper = 0.0;
    }

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

    @Override
    public int meanInt() {
        if (count == 0) return 0;
        return (int) Math.round(sum / count);
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
        if (count < 30) return "n/a";

        double mean = mean();
        double stdDev = standardDeviation();
        double z = 1.96; // 95% confidence interval

        double marginError = z * stdDev / Math.sqrt(count);
        lower = mean - marginError;
        upper = mean + marginError;

        return String.format("[%.4f ; %.4f]", lower, upper);
    }

    public double getLowerBound() {
        return lower;
    }

    public double getUpperBound() {
        return upper;
    }
    @Override
    public void clear() {
        count = 0;
        sum = 0.0;
        sumSquares = 0.0;
        lower = 0.0;
        upper = 0.0;
    }
}
