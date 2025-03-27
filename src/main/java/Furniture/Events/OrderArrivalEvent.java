package Furniture.Events;

import EventSimulation.Event;
import Furniture.Enums.OrderStateValues;
import IDGenerator.IDGenerator;
import Furniture.Entity.Order;
import Furniture.Entity.WorkPlace;
import Furniture.Entity.Worker;
import Furniture.Enums.PriorityValues;
import Furniture.Enums.WorkerBussyState;
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
        WorkPlace workPlace = core.getWorkPlace();
        LinkedList<Order> queueOne = workPlace.getQueueOne();

        Worker targetWorker = null;
        for(Worker w : workPlace.getWorkersA()) {
            if(w.getCurrentState() == WorkerBussyState.NON_BUSSY_WORKER.getValue()) {
                targetWorker = w;
            }
        }

        int orderType = core.getTypeOfOrderDist().sample();
        Order order = new Order(IDGenerator.getInstance().getNextOrderId(), orderType);
        core.ordersArrayList.add(order);

        order.setState(OrderStateValues.ORDER_NEW.getValue());
        if(targetWorker == null) {
            queueOne.addLast(order);
            order.setState(OrderStateValues.WAITING_IN_QUEUE_1.getValue());
        } else {
            if(queueOne.isEmpty()) {
                double newTime = time + Utility.calculateFirstTime(order, core);
                if(newTime < core.getEndTime()) {
                    targetWorker.setCurrentState(WorkerBussyState.BUSSY_WORKER.getValue());
                    order.setState(OrderStateValues.PROCESSING_CUTTING.getValue());
                    targetWorker.setOrderId(order.getId());
                    core.addEvent(new EndOfCuttingEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this.simulationCore, order, targetWorker));
                }
            } else {
                queueOne.addLast(order);
                order.setState(OrderStateValues.WAITING_IN_QUEUE_1.getValue());
                core.dataHandling();
            }
           // System.out.println(targetWorker.getId() + " | " + targetWorker.getCurrentState());
        }

        double newTime = this.time + core.getOrderArrivalDist().sample();
        core.addEvent(new OrderArrivalEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), simulationCore));
        core.dataHandling();


    }
}
