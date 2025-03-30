package Statistics;

public interface Statistic {
    void add(double value);
    double mean();
    int meanInt();
    String confidenceInterval();
    void clear();
}
