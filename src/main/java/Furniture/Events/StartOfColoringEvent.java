package Furniture.Events;

import EventSimulation.Event;
import SimulationCore.SimulationCore;

public class StartOfColoringEvent extends Event {

    protected StartOfColoringEvent(double time, int priority, SimulationCore simulationCore) {
        super(time, priority, simulationCore);
    }

    @Override
    public void Execute() {

    }
}
