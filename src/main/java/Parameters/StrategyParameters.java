package Parameters;

import java.util.ArrayList;

public class StrategyParameters implements IParameters {
    private double totalCost;
    private ArrayList<Double> dailyCosts;
    private ArrayList<Double> dailyFineCosts;
    private ArrayList<Integer> suppressorsDemand;
    private ArrayList<Integer> breakPlatesDemand;
    private ArrayList<Integer> headlightsDemand;
    private int actualRepCount;
    private int targetRepCount;
    private int targetAVGRepCount;

    public StrategyParameters() {
        this.dailyCosts = new ArrayList<>();
        this.dailyFineCosts = new ArrayList<>();
        this.suppressorsDemand = new ArrayList<>();
        this.breakPlatesDemand = new ArrayList<>();
        this.headlightsDemand = new ArrayList<>();
    }

    public double getTotalCost() { return totalCost; }
    public ArrayList<Double> getDailyCosts() { return dailyCosts; }
    public ArrayList<Double> getDailyFineCosts() { return dailyFineCosts; }
    public ArrayList<Integer> getSuppressorsDemand() { return suppressorsDemand; }
    public ArrayList<Integer> getBreakPlatesDemand() { return breakPlatesDemand; }
    public ArrayList<Integer> getHeadlightsDemand() { return headlightsDemand; }
    public int getActualRepCount() { return actualRepCount; }
    public int getTargetRepCount() { return targetRepCount; }

    public void setTotalCost(double totalCost) { this.totalCost = totalCost; }
    public void setDailyCosts(ArrayList<Double> dailyCosts) { this.dailyCosts = dailyCosts; }
    public void setDailyFineCosts(ArrayList<Double> dailyFineCosts) { this.dailyFineCosts = dailyFineCosts; }
    public void setSuppressorsDemand(ArrayList<Integer> suppressorsDemand) { this.suppressorsDemand = suppressorsDemand; }
    public void setBreakPlatesDemand(ArrayList<Integer> breakPlatesDemand) { this.breakPlatesDemand = breakPlatesDemand; }
    public void setHeadlightsDemand(ArrayList<Integer> headlightsDemand) { this.headlightsDemand = headlightsDemand; }
    public void setActualRepCount(int actualRepCount) { this.actualRepCount = actualRepCount; }
    public void setTargetRepCount(int targetRepCount) { this.targetRepCount = targetRepCount; }

    public int getTargetAVGRepCount() {
        return targetAVGRepCount;
    }

    public void setTargetAVGRepCount(int targetAVGRepCount) {
        this.targetAVGRepCount = targetAVGRepCount;
    }
}
