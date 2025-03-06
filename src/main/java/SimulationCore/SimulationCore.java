package SimulationCore;

public abstract class SimulationCore implements Runnable {

    protected long repCount;
    protected int actualRepCount;

    @Override
    public void run() {
        runSimulation();
    }

    public void runSimulation() {
        beforeRunSimulation();
        for(int i = 0; i < repCount; i++) {
            beforeSimulation();
            experiment();
            afterSimulation();
        }
        afterRunSimulation();
    }
    protected abstract void experiment();
    protected abstract void beforeRunSimulation();
    protected abstract void afterRunSimulation();
    protected abstract void beforeSimulation();
    protected abstract void afterSimulation();
}
