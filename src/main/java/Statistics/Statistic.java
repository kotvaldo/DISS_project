package Statistics;

public interface Statistic {
    void add(double value);
    double mean();
    String confidenceInterval();
    void clear();
}
