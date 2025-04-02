package Statistics;

public class Utilisation extends TimeWeightedStatistic {

    public Utilisation() {
        super();
    }

    public void start(double time) {
        recordChange(time, 1);
    }

    public void stop(double time) {
        recordChange(time, 0);
    }

    public double getUtilisation() {
        return getMean();
    }

    public double getUtilisationPercent() {
        return getMean() * 100.0;
    }
}
