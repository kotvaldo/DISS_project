package Furniture.Events;

import EventSimulation.Event;
import Furniture.Entity.Order;
import Furniture.Entity.WorkPlace;
import Furniture.Entity.Worker;
import Furniture.Enums.PriorityValues;
import Furniture.Enums.WorkerStateValues;
import Furniture.FurnitureEventCore;
import SimulationCore.SimulationCore;
import Utility.Utility;

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
            Order order = workPlace.getQueueOne().removeFirst();
            double newTime = this.time + Utility.calculateFirstTime(order, core);
            core.addEvent(new EndOfCuttingEvent(newTime, PriorityValues.BASIC_EVENT.getValue() ,this.simulationCore, order, worker));
        }

        Worker targetWorkerForTwo = null;
        for(Worker w : core.getWorkPlace().getWorkersTwo()) {
            if(w.getCurrentState() == WorkerStateValues.NON_BUSSY_WORKER.getValue()) {
                targetWorkerForTwo = w;
            }
        }

        if(targetWorkerForTwo == null) {
            workPlace.getQueuesTwo().addLast(order);
        } else {
            if(workPlace.getQueuesTwo().isEmpty()) {
                double newTime = Utility.calculateSecondTime(order, core);
                targetWorkerForTwo.setCurrentState(WorkerStateValues.BUSSY_WORKER.getValue());
                core.addEvent(new EndOfColoringEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this.simulationCore, order, targetWorkerForTwo));
            } else {
                workPlace.getQueuesTwo().addLast(order);
            }
        }

    }


}
