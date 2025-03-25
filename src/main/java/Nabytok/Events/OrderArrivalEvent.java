package Nabytok.Events;

import EventSimulation.Event;
import IDGenerator.IDGenerator;
import Nabytok.Entity.Order;
import Nabytok.Entity.WorkPlace;
import Nabytok.Entity.Worker;
import Nabytok.Enums.PriorityValues;
import Nabytok.Enums.WorkerStateValues;
import Nabytok.FurnitureEventCore;
import SimulationCore.SimulationCore;

import java.util.LinkedList;

public class OrderArrivalEvent extends Event {
    public OrderArrivalEvent(double time, int priority, SimulationCore simulationCore) {
        super(time, priority, simulationCore);
    }

    @Override
    public void Execute() {
        FurnitureEventCore core = (FurnitureEventCore) simulationCore;
        double newTime = this.time + core.getOrderArrivalDist().sample();
        core.addEvent(new OrderArrivalEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), simulationCore));
        WorkPlace workPlace = core.getWorkPlace();
        LinkedList<Order> queueOne = workPlace.getQueueOne();
        int isBussy = 0;
        for(Worker w : workPlace.getWorkersOne()) {
            if(w.getCurrentState() == WorkerStateValues.BUSSY_WORKER.getValue()) {
                isBussy++;
            }

        }
        int orderType = core.getTypeOfOrderDist().sample();
        Order order = new Order(IDGenerator.getInstance().getNextOrderId(), orderType);
        if(isBussy != 0) {
            queueOne.addLast(order);
            queueOne.removeFirst();
        }


    }
}
