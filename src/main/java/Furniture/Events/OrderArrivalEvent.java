package Furniture.Events;

import EventSimulation.Event;
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

        if(targetWorker == null) {
            queueOne.addLast(order);
        } else {
            if(queueOne.isEmpty()) {
                newTime = time + Utility.calculateFirstTime(order, core);
                targetWorker.setCurrentState(WorkerStateValues.BUSSY_WORKER.getValue());
                core.addEvent(new EndOfCuttingEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this.simulationCore, order, targetWorker));
            } else {
                queueOne.addLast(order);
            }
        }




    }
    private double calculateTime(Order order, FurnitureEventCore core) {
        double totalTime = 0.0;
        totalTime += core.getTimeInStorageDist().sample();
        totalTime += core.getTimeInStorageDist().sample();
        if(order.getType() == 1) {
            totalTime += core.getCuttingTypeOneDist().sample();
        } else if(order.getType() == 2) {
            totalTime += core.getCuttingTypeTwoDist().sample();
        } else if(order.getType() == 3) {
            totalTime += core.getCuttingTypeThreeDist().sample();
        }
        totalTime += core.getTimeMovingToAnotherWorkshopDist().sample();
        return totalTime;

    }
}
