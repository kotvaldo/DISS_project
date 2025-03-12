package MonteCarlo;

import SimulationCore.*;
import Strategy.IStrategy;

import java.util.ArrayList;

public class MonteCarlo extends SimulationCore {
    private IStrategy strategy;
    private double totalCost = 0;
    private double averageCost = 0;
    private UpdateListener listener;
    private int burnCount = 0;
    private int updateFrequency = 0;
    private final ArrayList<Double> dailyCosts;
    private final ArrayList<Double> dailyFineCosts;
    private ArrayList<Double> dailyFineCostsCopy;
    private ArrayList<Double> dailyCostsCopy;

    public MonteCarlo() {
        dailyCosts = new ArrayList<>();
        dailyFineCosts = new ArrayList<>();
    }

    @Override
    protected void experiment() {
        if (strategy != null) {
            totalCost = strategy.algorithm(totalCost, dailyCosts, dailyFineCosts);
        }
    }

    @Override
    protected void beforeRunSimulation() {
        this.actualRepCount = 0;
        this.totalCost = 0.0;
        this.isCancelled = false;
    }

    @Override
    protected void afterRunSimulation() {

    }

    @Override
    protected void beforeSimulation() {
        this.actualRepCount++;
        dailyCostsCopy = new ArrayList<>(dailyCosts);
        dailyFineCostsCopy = new ArrayList<>(dailyFineCosts);
        this.dailyCosts.clear();
        this.dailyFineCosts.clear();
    }

    @Override
    protected void afterSimulation() {
        if (this.actualRepCount > 0) {
            this.averageCost = this.totalCost / this.actualRepCount;
            if(actualRepCount % updateFrequency == 0 && actualRepCount >= burnCount) {
                this.listener.onUpdate(this.averageCost);
            }
        }
    }


    public void setStrategy(IStrategy strategy) {
        this.strategy = strategy;
    }

    public void setReplicationCount(int replicationCount) {
        this.repCount = replicationCount;
    }


    public int getRepCount() {
        return this.actualRepCount;
    }

    public void setListener(UpdateListener listener) {
        this.listener = listener;
    }

    public void cancel() {
        this.isCancelled = true;
    }

    public void setBurnCount(int burnCount) {
        this.burnCount = burnCount;
    }

    public void setUpdateFrequency(int updateFrequency) {
        this.updateFrequency = updateFrequency;
    }


    public ArrayList<Double> getDailyCostsCopy() {
        return dailyCostsCopy;
    }

    public ArrayList<Double> getDailyFineCostsCopy() {
        return dailyFineCostsCopy;
    }
}
