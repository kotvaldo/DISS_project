package Furniture.Events;

import EventSimulation.Event;
import SimulationCore.SimulationCore;

public class StartOfMontageEvent extends Event {

    protected StartOfMontageEvent(double time, int priority, SimulationCore simulationCore) {
        super(time, priority, simulationCore);
    }

    @Override
    public void Execute() {

    }
}
