package State;

public class MonteCarloState implements IState {
    private double average = 0.0;
    private int repCount = 0;
    private int currentReplicationCount;

    public MonteCarloState() {

    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public int getRepCount() {
        return repCount;
    }

    public void setRepCount(int repCount) {
        this.repCount = repCount;
    }

    public int getCurrentReplicationCount() {
        return currentReplicationCount;
    }

    public void setCurrentReplicationCount(int currentReplicationCount) {
        this.currentReplicationCount = currentReplicationCount;
    }
}
