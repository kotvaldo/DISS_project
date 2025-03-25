package Nabytok.Events;

import EventSimulation.Event;
import Nabytok.Entity.Order;
import SimulationCore.SimulationCore;

public class EndOfPrepareEvent extends Event {
    private Order order;
    protected EndOfPrepareEvent(double time, int priority, SimulationCore simulationCore, Order order) {
        super(time, priority, simulationCore);
        this.order = order;
    }

    @Override
    public void Execute() {

    }
}
