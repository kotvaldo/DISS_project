package Nabytok.Events;

import EventSimulation.Event;
import SimulationCore.SimulationCore;

public class OrderArrivalEvent extends Event {
    protected OrderArrivalEvent(double time, int priority, SimulationCore simulationCore) {
        super(time, priority, simulationCore);
    }

    @Override
    public void Execute() {

    }
}
