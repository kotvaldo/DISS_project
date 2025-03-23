package EventSimulation;

import SimulationCore.SimulationCore;

import java.util.PriorityQueue;

public abstract class BaseEventSimulationCore extends SimulationCore {
    protected PriorityQueue<Event> events;
    protected double simulationTime;
    protected double endTime;

    protected boolean isSlowDown;
    protected double slowDownSpeed;

    protected BaseEventSimulationCore() {
        events = new PriorityQueue<>();
    }


    @Override
    protected void experiment() {
        while (!events.isEmpty() && !this.isCancelled) {


        }
    }

    @Override
    protected abstract void beforeRunSimulation();

    @Override
    protected abstract void afterRunSimulation();

    @Override
    protected abstract void beforeSimulation();

    @Override
    protected abstract void afterSimulation();

    public double getSimulationTime() {
        return simulationTime;
    }
    public double getEndTime() {
        return endTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public void setSimulationTime(double simulationTime) {
        this.simulationTime = simulationTime;
    }
}
