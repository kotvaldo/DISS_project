package Statistics;

public class NonWeightedStatistic implements Statistic {
    protected int totalCount;
    protected double sum;

    public NonWeightedStatistic() {
        clear();
    }

    @Override
    public void add(double value) {
        sum += value;
        totalCount++;
    }

    @Override
    public double mean() {
        if (totalCount == 0) return 0;
        return sum / totalCount;
    }

    @Override
    public String confidenceInterval() {
        return "";
    }

    @Override
    public void clear() {
        sum = 0;
        totalCount = 0;
    }
}
