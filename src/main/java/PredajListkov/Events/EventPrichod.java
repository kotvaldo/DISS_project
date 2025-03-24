package PredajListkov.Events;

import EventSimulation.Event;
import PredajListkov.StanokCore;
import SimulationCore.SimulationCore;

public class EventPrichod extends Event {
    protected EventPrichod(double time, int priority, SimulationCore simulationCore) {
        super(time, priority, simulationCore);
    }

    @Override
    public void Execute() {
        StanokCore stanokCore = (StanokCore) simulationCore;

    }
}
