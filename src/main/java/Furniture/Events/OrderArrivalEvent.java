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
        LinkedList<Order> queueOne = core.getQueueCutting();


        int orderType = core.getTypeOfOrderDist().sample();
        Order order = new Order(IDGenerator.getInstance().getNextOrderId(), orderType, time);
        if(core.isSlowMode()) {
            core.ordersArrayList.add(order);
        }
        WorkPlace workPlace = null;

        if(core.getWorkplaces().isEmpty()) {
            workPlace = new WorkPlace();
            core.getWorkplaces().add(workPlace);
            order.setWorkPlace(workPlace);
            workPlace.setOrder(order);
        } else {
            for (WorkPlace wp : core.getWorkplaces()) {
                if(!wp.isBussy()) {
                    workPlace = wp;
                    break;
                }
            }
            if(workPlace == null) {
                workPlace = new WorkPlace();
                core.getWorkplaces().add(workPlace);
                order.setWorkPlace(workPlace);
                workPlace.setOrder(order);
            } else {
                order.setWorkPlace(workPlace);
                workPlace.setOrder(order);
            }
        }

        Worker targetWorker = null;
        for(Worker w : core.getWorkersA()) {
            if(w.getCurrentState() == WorkerBussyState.NON_BUSSY_WORKER.getValue()) {
                targetWorker = w;
                break;
            }
        }

        order.setState(OrderStateValues.ORDER_NEW.getValue());
        if(targetWorker == null) {
            queueOne.addLast(order);
            order.setState(OrderStateValues.WAITING_IN_QUEUE_1.getValue());
        } else {
            if(queueOne.isEmpty()) {
                double newTime = time + Utility.calculateFirstTime(order, core, targetWorker);
                if(newTime < core.getEndTime()) {
                    targetWorker.setCurrentState(WorkerBussyState.BUSSY_WORKER.getValue());
                    order.setState(OrderStateValues.PROCESSING_CUTTING.getValue());
                    targetWorker.setOrder(order);
                    core.addEvent(new EndOfCuttingEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this.simulationCore, order, targetWorker));
                }
            } else {
                queueOne.addLast(order);
                order.setState(OrderStateValues.WAITING_IN_QUEUE_1.getValue());
            }
           // System.out.println(targetWorker.getId() + " | " + targetWorker.getCurrentState());
        }

        double newTime = this.time + core.getOrderArrivalDist().sample();
        if(newTime < core.getEndTime()) {
            core.addEvent(new OrderArrivalEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), simulationCore));

        }

        //System.out.println(queueOne.size());

    }
}
