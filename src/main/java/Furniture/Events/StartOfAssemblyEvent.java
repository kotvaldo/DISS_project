package Furniture.Events;

import EventSimulation.Event;
import SimulationCore.SimulationCore;

public class StartOfAssemblyEvent extends Event {
    protected StartOfAssemblyEvent(double time, int priority, SimulationCore simulationCore) {
        super(time, priority, simulationCore);
    }

    @Override
    public void Execute() {

    }
}
