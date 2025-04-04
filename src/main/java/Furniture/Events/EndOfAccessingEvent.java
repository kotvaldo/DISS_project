package Furniture.Events;

import EventSimulation.Event;
import Furniture.Entity.Order;
import Furniture.Entity.WorkPlace;
import Furniture.Entity.WorkerA;
import Furniture.Enums.OrderStateValues;
import Furniture.Enums.PriorityValues;
import Furniture.FurnitureEventCore;
import IDGenerator.IDGenerator;
import SimulationCore.SimulationCore;
import Utility.Utility;

import java.util.LinkedList;

public class EndOfAccessingEvent extends Event {

    Order order;
    WorkerA workerA;

    public EndOfAccessingEvent(double time, int priority, SimulationCore simulationCore, Order order, WorkerA worker) {
        super(time, priority, simulationCore);
        this.order = order;
        this.workerA = worker;
    }

    @Override
    public void Execute() {
        FurnitureEventCore core = (FurnitureEventCore) simulationCore;


        double cuttingTime = Utility.calculateCuttingTime(order, core, workerA);
        double newTime = this.time + cuttingTime;

        if (newTime < core.getEndTime()) {
            order.setState(OrderStateValues.PROCESSING_CUTTING.getValue());
            core.addEvent(new EndOfCuttingEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this.simulationCore, order, workerA));
        }


    }
}
