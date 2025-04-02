package Furniture.Events;

import EventSimulation.Event;
import Furniture.Entity.WorkerA;
import Furniture.Enums.OrderStateValues;
import IDGenerator.IDGenerator;
import Furniture.Entity.Order;
import Furniture.Entity.WorkPlace;
import Furniture.Entity.Worker;
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

        core.recordQueueLengths(this.time);

        // vybratie typu orderu
        int orderType = core.getGenerators().getTypeOfOrderDist().sample();

        // novy order
        Order order = new Order(IDGenerator.getInstance().getNextOrderId(), orderType, time);
        order.setState(OrderStateValues.ORDER_NEW.getValue());
        core.ordersArrayList.add(order);

        // vybratie workPlacu
        WorkPlace workPlace = core.getWorkplaces()
                .stream()
                .filter(wp -> !wp.isBusy())
                .findFirst()
                .orElseGet(() -> {
                    WorkPlace newWp = new WorkPlace();
                    core.getWorkplaces().add(newWp);
                    return newWp;
                });

        //set Workeplacu
        order.setWorkPlace(workPlace);
        workPlace.setOrder(order);

        LinkedList<Order> queueCutting = core.getQueueCutting();

        if (!core.getFreeWorkersA().isEmpty()) {
            WorkerA targetWorker = core.getFreeWorkersA().removeFirst();

            Order orderToProcess;

            if (!queueCutting.isEmpty()) {
                // Ak fronta nie je prázdna, vždy ber prvú objednávku z fronty
                orderToProcess = queueCutting.removeFirst();

                // Práve vytvorená objednávka musí ísť do fronty vždy
                queueCutting.addLast(order);
                order.setState(OrderStateValues.WAITING_IN_QUEUE_1.getValue());

            } else {
                // Ak fronta je prázdna, spracuj aktuálnu objednávku
                orderToProcess = order;
            }

            double timeOfEvent = Utility.calculateCuttingTime(orderToProcess, core, targetWorker);
            double newTime = time + timeOfEvent;

            if (newTime < core.getEndTime()) {
                orderToProcess.setState(OrderStateValues.PROCESSING_CUTTING.getValue());
                targetWorker.setOrder(orderToProcess, this.time);
                core.addEvent(new EndOfCuttingEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this.simulationCore, orderToProcess, targetWorker));
            }

        } else {
            // Ak nie je voľný pracovník, pridaj objednávku do fronty
            queueCutting.addLast(order);
            order.setState(OrderStateValues.WAITING_IN_QUEUE_1.getValue());
        }


        double newTime = this.time + core.getGenerators().getOrderArrivalDist().sample();
        if (newTime < core.getEndTime()) {
            core.addEvent(new OrderArrivalEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), simulationCore));
        }
    }

}
