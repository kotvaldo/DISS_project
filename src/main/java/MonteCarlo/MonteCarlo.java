package MonteCarlo;

import Parameters.StrategyParameters;
import SimulationCore.*;
import Strategy.IStrategy;
import Strategy.Strategy;
import Utility.Utility;

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
    private final ArrayList<Integer> suppressorsDemand;
    private final ArrayList<Integer> breakPlatesDemand;
    private final ArrayList<Integer> headlightsDemand;
    StrategyParameters params;

    public MonteCarlo() {
        dailyCosts = new ArrayList<>();
        dailyFineCosts = new ArrayList<>();
        suppressorsDemand = new ArrayList<>();
        breakPlatesDemand = new ArrayList<>();
        headlightsDemand = new ArrayList<>();
        params = new StrategyParameters();

    }

    @Override
    protected void experiment() {
        if (strategy != null) {
            if (params == null) {
                params = new StrategyParameters();
            }
            params.setTotalCost(totalCost);
            params.setDailyCosts(dailyCosts);
            params.setDailyFineCosts(dailyFineCosts);
            params.setActualRepCount(actualRepCount);
            params.setHeadlightsDemand(headlightsDemand);
            params.setSuppressorsDemand(suppressorsDemand);
            params.setBreakPlatesDemand(breakPlatesDemand);
            params.setTargetRepCount((int) (this.repCount * 0.1));
            params.setDailyFineCosts(dailyFineCosts);
            params.setTargetAVGRepCount((int) (this.repCount * 0.01));
            totalCost = strategy.algorithm(params);
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
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Predikovaný dopyt - Tlmiče: " + Utility.trimmedMean(suppressorsDemand, 0.2));
        System.out.println("Predikovaný dopyt - Brzdy: " + Utility.trimmedMean(breakPlatesDemand, 0.2));
        System.out.println("Predikovaný dopyt - Svetlá: " + Utility.trimmedMean(headlightsDemand,0.2));
    }

    @Override
    protected void beforeSimulation() {
        this.actualRepCount++;
        dailyCostsCopy = new ArrayList<>(dailyCosts);
        dailyFineCostsCopy = new ArrayList<>(dailyFineCosts);
        if(actualRepCount < (int) (this.repCount * 0.1)) {
            this.dailyCosts.clear();
            this.dailyFineCosts.clear();
        }


    }

    @Override
    protected void afterSimulation() {
        if (this.actualRepCount > 0) {
            this.averageCost = this.totalCost / this.actualRepCount;
            if (actualRepCount % updateFrequency == 0 && actualRepCount >= burnCount) {
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
