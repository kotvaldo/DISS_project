package EventSimulation;

import SimulationCore.SimulationCore;

import java.util.PriorityQueue;

public abstract class BaseEventSimulationCore extends SimulationCore {
    protected PriorityQueue<Event> events;
    protected double simulationTime;
    protected double endTime;

    protected BaseEventSimulationCore() {
        events = new PriorityQueue<>();
    }


    @Override
    protected abstract void experiment();

    @Override
    protected abstract void beforeRunSimulation();

    @Override
    protected abstract void afterRunSimulation();

    @Override
    protected abstract void beforeSimulation();

    @Override
    protected abstract void afterSimulation();



}
