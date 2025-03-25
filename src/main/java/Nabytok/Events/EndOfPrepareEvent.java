package Nabytok.Events;

import EventSimulation.Event;
import SimulationCore.SimulationCore;

public class EndOfPrepareEvent extends Event {
    protected EndOfPrepareEvent(double time, int priority, SimulationCore simulationCore) {
        super(time, priority, simulationCore);
    }

    @Override
    public void Execute() {

    }
}
