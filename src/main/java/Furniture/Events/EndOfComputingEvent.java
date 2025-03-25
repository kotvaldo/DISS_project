package Furniture.Events;

import EventSimulation.Event;
import SimulationCore.SimulationCore;

public class EndOfComputingEvent extends Event {
    protected EndOfComputingEvent(double time, int priority, SimulationCore simulationCore) {
        super(time, priority, simulationCore);
    }

    @Override
    public void Execute() {

    }
}
