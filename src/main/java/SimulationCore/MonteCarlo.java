package SimulationCore;

import Strategy.IStrategy;

public class MonteCarlo extends SimulationCore {
    private IStrategy strategy;
    private double totalCost;
    private double averageCost = 0.0;

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
    }

    @Override
    protected void afterRunSimulation() {
        System.out.println("Total Cost: " + this.totalCost);
        System.out.println("Average Cost: " + this.averageCost);
    }

    @Override
    protected void beforeSimulation() {
        this.actualRepCount++;
    }

    @Override
    protected void afterSimulation() {
        if (this.actualRepCount > 0) {
            this.averageCost = this.totalCost / this.actualRepCount;
        }
        System.out.println("Average Cost: " + this.averageCost);
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
}
