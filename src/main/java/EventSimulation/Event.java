package EventSimulation;

import SimulationCore.SimulationCore;

public abstract class Event {
    protected double time;
    protected int priority;
    protected SimulationCore simulationCore;

    protected Event(double time, int priority, SimulationCore simulationCore) {
        this.priority = priority;
        this.simulationCore = simulationCore;
        this.time = time;
    }


    public abstract void execute();

}
