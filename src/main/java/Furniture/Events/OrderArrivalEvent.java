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

        //orderType


        //initializing new order
        int orderType = core.getGenerators().getTypeOfOrderDist().sample();
        Order order = new Order(IDGenerator.getInstance().getNextOrderId(), orderType, time);
        order.setState(OrderStateValues.ORDER_NEW.getValue());
        core.ordersArrayList.add(order);


        WorkPlace workPlace = null;

        if(core.getWorkplaces().isEmpty()) {
            workPlace = new WorkPlace();
            core.getWorkplaces().add(workPlace);
        } else {
            for (WorkPlace wp : core.getWorkplaces()) {
                if(!wp.isBusy()) {
                    workPlace = wp;
                    break;
                }
            }

            if(workPlace == null) {
                workPlace = new WorkPlace();
                core.getWorkplaces().add(workPlace);
            }
        }
        order.setWorkPlace(workPlace);
        workPlace.setOrder(order);

        //planning cutting event

        Worker targetWorker = null;
        if(!core.getFreeWorkersA().isEmpty()) {
            targetWorker = core.getFreeWorkersA().getFirst();
        }



        LinkedList<Order> queueCutting = core.getQueueCutting();
        if(targetWorker == null) {
            queueCutting.addLast(order);
            order.setState(OrderStateValues.WAITING_IN_QUEUE_1.getValue());
        } else {
            if(queueCutting.isEmpty()) {
                double timeOfEvent = Utility.calculateFirstTime(order, core, targetWorker);
                double newTime = time + timeOfEvent;
                if(newTime < core.getEndTime()) {
                    targetWorker = core.getFreeWorkersA().removeFirst();
                    order.setState(OrderStateValues.PROCESSING_CUTTING.getValue());
                    targetWorker.setOrder(order, this.time);
                  //  order.addToTimeOfWork(newTime - timeOfEvent);
                    core.addEvent(new EndOfCuttingEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this.simulationCore, order, targetWorker));
                }
            }
        }

        // plan another arrival
        double newTime = this.time + core.getGenerators().getOrderArrivalDist().sample();
        if(newTime < core.getEndTime()) {
            core.addEvent(new OrderArrivalEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), simulationCore));
        }


    }
}
