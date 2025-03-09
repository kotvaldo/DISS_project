package MonteCarlo;

import SimulationCore.*;
import Strategy.IStrategy;

public class MonteCarlo extends SimulationCore {
    private IStrategy strategy;
    private double totalCost = 0;
    private double averageCost = 0;
    private UpdateListener listener;
    private int burnCount = 0;
    private int updateFrequency = 0;

    public MonteCarlo() {


    }

    @Override
    protected void experiment() {
        if (strategy != null) {
            totalCost = strategy.algorithm(totalCost);
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
    }

    @Override
    protected void afterSimulation() {
        if (this.actualRepCount > 0) {
            this.averageCost = this.totalCost / this.actualRepCount;
            if(actualRepCount % updateFrequency == 0 && actualRepCount > burnCount) {
                this.listener.onUpdate(this.averageCost);
            }
        }

    }

    public IStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(IStrategy strategy) {
        this.strategy = strategy;
    }

    public void setReplicationCount(int replicationCount) {
        this.repCount = replicationCount;
    }

    public double getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(double averageCost) {
        this.averageCost = averageCost;
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
}
