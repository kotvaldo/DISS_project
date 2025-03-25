package Furniture.Events;

import EventSimulation.Event;
import Furniture.Entity.Order;
import Furniture.Entity.WorkPlace;
import Furniture.Entity.Worker;
import Furniture.Enums.PriorityValues;
import Furniture.Enums.WorkerStateValues;
import Furniture.FurnitureEventCore;
import SimulationCore.SimulationCore;

public class EndOfCuttingEvent extends Event {
    private Order order;
    private Worker worker;
    protected EndOfCuttingEvent(double time, int priority, SimulationCore simulationCore, Order order, Worker worker) {
        super(time, priority, simulationCore);
        this.order = order;
        this.worker = worker;
    }

    @Override
    public void Execute() {
        FurnitureEventCore core = (FurnitureEventCore) simulationCore;
        WorkPlace workPlace = core.getWorkPlace();

        if(workPlace.getQueueOne().isEmpty()) {
            worker.setCurrentState(WorkerStateValues.NON_BUSSY_WORKER.getValue());
        } else {
            double newTime = ;
            core.addEvent(new EndOfCuttingEvent(newTime, PriorityValues.BASIC_EVENT,workPlace.getQueueOne().removeFirst(), worker));
        }

        Worker targetWorkerForTwo = null;
        for(Worker w : core.getWorkPlace().getWorkersTwo()) {
            if(w.getCurrentState() == WorkerStateValues.NON_BUSSY_WORKER.getValue()) {
                targetWorkerForTwo = w;
            }
        }


    }

    private double calculateTime(Order order, FurnitureEventCore core) {
        double totalTime = 0.0;
        if(order.getType() == 1) {
            totalTime += core.getColoringTypeOneDist().sample();
        } else if(order.getType() == 2) {
            totalTime += core.getColoringTypeTwoDist().sample();
        } else if(order.getType() == 3) {
            totalTime += core.getColoringTypeThreeDist().sample();
        }
        totalTime += core.getTimeMovingToAnotherWorkshopDist().sample();
        return totalTime;

    }
}
