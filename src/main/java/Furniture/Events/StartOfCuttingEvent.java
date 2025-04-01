package Furniture.Events;

import EventSimulation.Event;
import SimulationCore.SimulationCore;

public class StartOfCuttingEvent extends Event {
    protected StartOfCuttingEvent(double time, int priority, SimulationCore simulationCore) {
        super(time, priority, simulationCore);
    }

    @Override
    public void Execute() {

    }
}
