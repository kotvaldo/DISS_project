package SimulationCore;

import Strategy.IStrategy;

public class MonteCarlo extends SimulationCore{
    private IStrategy strategy;

    public MonteCarlo(){

    }



    @Override
    protected void experiment() {

    }

    @Override
    protected void beforeRunSimulation() {

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
