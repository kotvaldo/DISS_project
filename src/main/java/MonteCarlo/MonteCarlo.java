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
    private final ArrayList<Double> weeklyCosts;
    private ArrayList<Double> weeklyCostsCopy;

    public MonteCarlo() {
        weeklyCosts = new ArrayList<>();

    }

    @Override
    protected void experiment() {
        if (strategy != null) {
            totalCost = strategy.algorithm(totalCost, weeklyCosts);
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
        weeklyCostsCopy = new ArrayList<>(weeklyCosts);
        this.weeklyCosts.clear();
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


    public ArrayList<Double> getWeeklyCostsCopy() {
        return weeklyCostsCopy;
    }
}
