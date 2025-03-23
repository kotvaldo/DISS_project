package EventSimulation;

import SimulationCore.SimulationCore;

public abstract class Event implements Comparable<Event> {
    protected double time;
    protected int priority;
    protected final SimulationCore simulationCore;

    protected Event(double time, int priority, SimulationCore simulationCore) {
        this.priority = priority;
        this.simulationCore = simulationCore;
        this.time = time;
    }

    public abstract void Execute();

    public double getTime() {
        return time;
    }
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public SimulationCore getSimulationCore() {
        return simulationCore;
    }

    @Override
    public int compareTo(Event other) {
        if (this.time != other.time) {
            return Double.compare(this.time, other.time);
        }
        return Integer.compare(this.priority, other.priority);
    }
}
