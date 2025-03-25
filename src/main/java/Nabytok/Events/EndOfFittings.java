package Nabytok.Events;

import EventSimulation.Event;
import SimulationCore.SimulationCore;

public class EndOfFittings extends Event {
    protected EndOfFittings(double time, int priority, SimulationCore simulationCore) {
        super(time, priority, simulationCore);
    }

    @Override
    public void Execute() {

    }
}
