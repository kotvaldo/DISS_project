package SimulationCore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulationManager {
    private final ExecutorService executor;

    public SimulationManager(int threadPoolSize) {
        executor = Executors.newFixedThreadPool(threadPoolSize);
    }

    public void startSimulation(SimulationCore simulation) {
        executor.execute(simulation);
    }

    public void stopAllSimulations() {
        executor.shutdown();
    }
}

