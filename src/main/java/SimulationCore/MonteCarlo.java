package SimulationCore;

import Strategy.IStrategy;

import java.util.ArrayList;

public class MonteCarlo extends SimulationCore{
    private IStrategy strategy;
    private double totalCost;
    private ArrayList<Integer> totalProducts;

    public MonteCarlo(){
        this.totalProducts = new ArrayList<>();
        this.totalCost = 0;
        this.totalProducts.add(0);
        this.totalProducts.add(0);
        this.totalProducts.add(0);

    }



    @Override
    protected void experiment() {
        if(strategy != null){
            this.strategy.algorithm(totalCost, totalProducts);
        }
    }

    @Override
    protected void beforeRunSimulation() {
        for(int i = 0; i < this.totalProducts.size(); i++){
            this.totalProducts.set(i,0);
        }
        this.totalCost = 0;
    }

    @Override
    protected void afterRunSimulation() {

    }

    @Override
    protected void beforeSimulation() {

    }

    @Override
    protected void afterSimulation() {

    }

    public IStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(IStrategy strategy) {
        this.strategy = strategy;
    }
}
