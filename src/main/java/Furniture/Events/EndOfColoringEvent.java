package Furniture.Events;

import EventSimulation.Event;
import Furniture.Entity.Order;
import Furniture.Entity.Worker;
import SimulationCore.SimulationCore;

public class EndOfColoringEvent extends Event {
    Worker worker;
    Order order;

    protected EndOfColoringEvent(double time, int priority, SimulationCore simulationCore, Order order, Worker worker) {
        super(time, priority, simulationCore);
    }

    @Override
    public void Execute() {


    }
}
