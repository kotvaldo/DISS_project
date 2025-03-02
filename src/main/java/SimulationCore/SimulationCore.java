package SimulationCore;

import org.jfree.data.category.DefaultCategoryDataset;

public abstract class SimulationCore {

    public void runSimulation(int repCount) {
        beforeSimulation();
        for(int i = 0; i < repCount; i++) {
            experiment();
        }
        afterSimulation();
    }
    protected abstract void experiment();
    protected abstract void beforeSimulation();
    protected abstract void afterSimulation();
}
