package Furniture.Events;

import EventSimulation.Event;
import Furniture.Entity.Order;
import Furniture.Entity.WorkPlace;
import Furniture.Entity.Worker;
import Furniture.Enums.OrderStateValues;
import Furniture.Enums.PriorityValues;
import Furniture.Enums.WorkerStateValues;
import Furniture.FurnitureEventCore;
import SimulationCore.SimulationCore;
import Utility.Utility;

public class EndOfColoringEvent extends Event {
    Worker worker;
    Order order;

    protected EndOfColoringEvent(double time, int priority, SimulationCore simulationCore, Order order, Worker worker) {
        super(time, priority, simulationCore);
        this.order = order;
        this.worker = worker;
    }

    @Override
    public void Execute() {
        FurnitureEventCore core = (FurnitureEventCore) simulationCore;
        WorkPlace workPlace = core.getWorkPlace();

        if(workPlace.getQueueFour().isEmpty()) {
            if (workPlace.getQueuesTwo().isEmpty()) {
                worker.setCurrentState(WorkerStateValues.NON_BUSSY_WORKER.getValue());
            } else {
                Order order = workPlace.getQueuesTwo().removeFirst();
                double newTime = this.time + Utility.calculateSecondTime(order, core);
                if (newTime < core.getEndTime()) {
                    order.setState(OrderStateValues.PROCESSING_COLORING.getValue());
                    core.addEvent(new EndOfColoringEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this.simulationCore, order, worker));
                }
            }
        } else {
            Order order = workPlace.getQueueFour().removeFirst();
            double newTime = this.time + Utility.calculateFourth(order, core);
            if (newTime < core.getEndTime()) {
                order.setState(OrderStateValues.PROCESSING_FITTINGS.getValue());
                core.addEvent(new EndOfFittings(newTime, PriorityValues.BASIC_EVENT.getValue(), this.simulationCore, order, worker));
            }
        }

        Worker targetWorkerForTwo = null;
        for (Worker w : core.getWorkPlace().getWorkersThree()) {
            if (w.getCurrentState() == WorkerStateValues.NON_BUSSY_WORKER.getValue()) {
                targetWorkerForTwo = w;
            }
        }

        if (targetWorkerForTwo == null) {
            workPlace.getQueuesThree().addLast(order);
            order.setState(OrderStateValues.WAITING_IN_QUEUE_3.getValue());
        } else {
            if (workPlace.getQueuesThree().isEmpty()) {
                double newTime = Utility.calculateThird(order, core);
                if (newTime <= core.getEndTime()) {

                    order.setState(OrderStateValues.PROCESSING_COMPUTING.getValue());
                    targetWorkerForTwo.setCurrentState(WorkerStateValues.BUSSY_WORKER.getValue());
                    core.addEvent(new EndOfColoringEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this.simulationCore, order, targetWorkerForTwo));
                }

            } else {
                workPlace.getQueuesThree().addLast(order);
                order.setState(OrderStateValues.WAITING_IN_QUEUE_3.getValue());
            }
        }
        core.dataHandling();
    }
}
