package Furniture.Events;

import EventSimulation.Event;
import SimulationCore.SimulationCore;

public class EndOfColoringEvent extends Event {
    protected EndOfColoringEvent(double time, int priority, SimulationCore simulationCore) {
        super(time, priority, simulationCore);
    }

    @Override
    public void Execute() {

    }
}
