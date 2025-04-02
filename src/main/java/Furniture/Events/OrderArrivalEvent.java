package Furniture.Events;

import EventSimulation.Event;
import Furniture.Entity.WorkerA;
import Furniture.Enums.OrderStateValues;
import IDGenerator.IDGenerator;
import Furniture.Entity.Order;
import Furniture.Entity.WorkPlace;
import Furniture.Enums.PriorityValues;
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

        int orderType = core.getGenerators().getTypeOfOrderDist().sample();

        Order order = new Order(IDGenerator.getInstance().getNextOrderId(), orderType, time);
        order.setState(OrderStateValues.ORDER_NEW.getValue());
        core.ordersArrayList.add(order);

        WorkPlace workPlace = core.getWorkplaces()
                .stream()
                .filter(wp -> !wp.isBusy())
                .findFirst()
                .orElseGet(() -> {
                    WorkPlace newWp = new WorkPlace();
                    core.getWorkplaces().add(newWp);
                    return newWp;
                });

        order.setWorkPlace(workPlace);
        workPlace.setOrder(order);

        LinkedList<Order> queueCutting = core.getQueueCutting();

        if (!core.getFreeWorkersA().isEmpty()) {
            WorkerA targetWorker = core.getFreeWorkersA().removeFirst();
            Order orderToProcess;

            if (!queueCutting.isEmpty()) {
                orderToProcess = queueCutting.removeFirst();
                // posuvat vytvoreny

                orderToProcess.setQueueCuttingLeaveTime(this.time);
                double waitingTime = orderToProcess.getQueueCuttingLeaveTime() - orderToProcess.getQueueCuttingEnterTime();
                if (orderToProcess.getQueueCuttingEnterTime() >= 0 && orderToProcess.getQueueCuttingLeaveTime() >= 0 && waitingTime > 0) {
                    core.getAverageTimeInQueueCutting().add(waitingTime);
                }

                core.recordQueueLengthCutting(this.time);

                queueCutting.addLast(order);
                order.setQueueCuttingEnterTime(this.time);

                core.recordQueueLengthCutting(this.time);

                order.setState(OrderStateValues.WAITING_IN_QUEUE_1.getValue());
            } else {
                orderToProcess = order;
            }

            double timeOfEvent = Utility.calculateCuttingTime(orderToProcess, core, targetWorker);
            double newTime = time + timeOfEvent;

            if (newTime < core.getEndTime()) {
                targetWorker.getUtilisation().start(this.time);
                orderToProcess.setState(OrderStateValues.PROCESSING_CUTTING.getValue());
                targetWorker.setOrder(orderToProcess, this.time);
                core.addEvent(new EndOfCuttingEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this.simulationCore, orderToProcess, targetWorker));
            }

        } else {
            order.setQueueCuttingEnterTime(this.time);
            queueCutting.addLast(order);
            core.recordQueueLengthCutting(this.time);
            order.setState(OrderStateValues.WAITING_IN_QUEUE_1.getValue());
        }


        double arrivalTimeOffset = core.getGenerators().getOrderArrivalDist().sample();
        double newTime = this.time + arrivalTimeOffset;
        if (newTime < core.getEndTime()) {
            core.addEvent(new OrderArrivalEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), simulationCore));
        }
    }
}
