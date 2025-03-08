package SimulationCore;

public abstract class SimulationCore {

    protected long repCount;
    protected int actualRepCount;
    protected boolean isCancelled = false;

    public void runSimulation() {
        beforeRunSimulation();
        for(int i = 0; i < repCount; i++) {
            if(isCancelled) {
                break;
            }
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
