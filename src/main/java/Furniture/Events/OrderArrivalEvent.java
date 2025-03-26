package Furniture.Events;

import EventSimulation.Event;
import Furniture.Enums.OrderStateValues;
import IDGenerator.IDGenerator;
import Furniture.Entity.Order;
import Furniture.Entity.WorkPlace;
import Furniture.Entity.Worker;
import Furniture.Enums.PriorityValues;
import Furniture.Enums.WorkerStateValues;
import Furniture.FurnitureEventCore;
import SimulationCore.SimulationCore;
import Utility.Utility;

import java.util.LinkedList;

public class OrderArrivalEvent extends Event {
    public OrderArrivalEvent(double time, int priority, SimulationCore simulationCore) {
        super(time, priority, simulationCore);
    }

    @Override
    public void Execute() {
        FurnitureEventCore core = (FurnitureEventCore) simulationCore;
        double newTime = this.time + core.getOrderArrivalDist().sample();
        this.time = newTime;
        core.addEvent(this);
        WorkPlace workPlace = core.getWorkPlace();
        LinkedList<Order> queueOne = workPlace.getQueueOne();

        Worker targetWorker = null;
        for(Worker w : workPlace.getWorkersOne()) {
            if(w.getCurrentState() == WorkerStateValues.NON_BUSSY_WORKER.getValue()) {
                targetWorker = w;
            }
        }
        int orderType = core.getTypeOfOrderDist().sample();
        Order order = new Order(IDGenerator.getInstance().getNextOrderId(), orderType);
        order.setState(OrderStateValues.ORDER_NEW.getValue());
        if(targetWorker == null) {
            queueOne.addLast(order);
            order.setState(OrderStateValues.WAITING_IN_QUEUE_1.getValue());
        } else {
            if(queueOne.isEmpty()) {
                newTime = time + Utility.calculateFirstTime(order, core);
                if(newTime <= core.getEndTime()) {
                    targetWorker.setCurrentState(WorkerStateValues.BUSSY_WORKER.getValue());
                    core.addEvent(new EndOfCuttingEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this.simulationCore, order, targetWorker));
                    order.setState(OrderStateValues.PROCESSING_CUTTING.getValue());
                }

            } else {
                queueOne.addLast(order);
                order.setState(OrderStateValues.WAITING_IN_QUEUE_1.getValue());
            }
        }
        core.dataHandling();
    }
}
