package Statistics;

public class TimeWeightedStatistic {
    private double lastChangeTime;
    private int lastValue;

    private double weightedSum;           // Σ q(t) * delta_t
    private double weightedSumSquares;    // Σ (q(t)^2) * delta_t
    private double timeSum;               // Σ delta_t

    private double lower;                 // CI lower
    private double upper;                 // CI upper

    public TimeWeightedStatistic() {
        clear();
    }

    public void recordChange(double currentTime, int newValue) {
        double dt = currentTime - lastChangeTime;
        if (dt > 0) {
            weightedSum += lastValue * dt;
            weightedSumSquares += (lastValue * lastValue) * dt;
            timeSum += dt;
        }
        lastChangeTime = currentTime;
        lastValue = newValue;
    }

    public double getMean() {
        if (timeSum == 0) {
            return 0;
        }
        return weightedSum / timeSum;
    }

    public double variance() {
        if (timeSum == 0) return 0;
        double mean = getMean();
        return (weightedSumSquares / timeSum) - (mean * mean);
    }

    public double standardDeviation() {
        return Math.sqrt(variance());
    }

    public String confidenceInterval() {
        if (timeSum < 30) return "n/a";  // rule-of-thumb
        double mean = getMean();
        double stdDev = standardDeviation();
        double z = 1.96; // 95% interval
        double marginError = z * stdDev / Math.sqrt(timeSum);
        lower = mean - marginError;
        upper = mean + marginError;
        return String.format("[%.4f ; %.4f]", lower, upper);
    }

    public double getLower() {
        return lower;
    }

    public double getUpper() {
        return upper;
    }

    public double getIS() {
        return timeSum;
    }

    public void clear() {
        lastChangeTime = 0;
        lastValue = 0;
        weightedSum = 0;
        weightedSumSquares = 0;
        timeSum = 0;
        lower = 0;
        upper = 0;
    }
}
