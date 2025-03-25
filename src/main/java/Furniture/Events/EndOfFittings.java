package Furniture.Events;

import EventSimulation.Event;
import Furniture.Entity.Order;
import Furniture.Entity.Worker;
import SimulationCore.SimulationCore;

public class EndOfFittings extends Event {
    protected EndOfFittings(double time, int priority, SimulationCore simulationCore, Order order, Worker worker) {
        super(time, priority, simulationCore);
    }

    @Override
    public void Execute() {

    }
}
