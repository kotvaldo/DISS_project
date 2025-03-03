package SimulationCore;

public abstract class SimulationCore {

    public void runSimulation(int repCount) {
        beforeSimulation();
        for(int i = 0; i < repCount; i++) {
            beforeRunSimulation();
            experiment();
            afterRunSimulation();
        }
        afterSimulation();
    }
    protected abstract void experiment();
    protected abstract void beforeRunSimulation();
    protected abstract void afterRunSimulation();
    protected abstract void beforeSimulation();
    protected abstract void afterSimulation();
}
