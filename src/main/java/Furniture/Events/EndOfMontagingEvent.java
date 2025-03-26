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

public class EndOfMontagingEvent extends Event {
    private Worker worker;
    private Order order;
    public EndOfMontagingEvent(double time, int priority, SimulationCore simulationCore, Order order, Worker worker) {
        super(time, priority, simulationCore);
        this.order = order;
        this.worker = worker;
    }

    @Override
    public void Execute() {
        FurnitureEventCore core = (FurnitureEventCore) simulationCore;
        WorkPlace workPlace = core.getWorkPlace();

        if (workPlace.getQueuesThree().isEmpty()) {
            worker.setCurrentState(WorkerStateValues.NON_BUSSY_WORKER.getValue());
        } else {
            Order order = workPlace.getQueuesThree().removeFirst();
            double newTime = this.time + Utility.calculateThird(order, core);
            if (newTime < core.getEndTime()) {
                order.setState(OrderStateValues.PROCESSING_COMPUTING.getValue());
                core.addEvent(new EndOfMontagingEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this.simulationCore, order, worker));
            }
        }

        if(order.getType() == 3) {
            Worker targetWorkerForFour = null;
            for (Worker w : core.getWorkPlace().getWorkersThree()) {
                if (w.getCurrentState() == WorkerStateValues.NON_BUSSY_WORKER.getValue()) {
                    targetWorkerForFour = w;
                }
            }

            if (targetWorkerForFour == null) {
                order.setState(OrderStateValues.WAITING_IN_QUEUE_4.getValue());
                workPlace.getQueueFour().addLast(order);
            } else {
                if (workPlace.getQueueFour().isEmpty()) {
                    double newTime = Utility.calculateFourth(order, core);
                    if (newTime <= core.getEndTime()) {
                        order.setState(OrderStateValues.PROCESSING_FITTINGS.getValue());
                        targetWorkerForFour.setCurrentState(WorkerStateValues.BUSSY_WORKER.getValue());
                        core.addEvent(new EndOfFittings(newTime, PriorityValues.IMPORTANT_EVENT.getValue(), this.simulationCore, order, targetWorkerForFour));
                    }

                } else {
                    order.setState(OrderStateValues.WAITING_IN_QUEUE_4.getValue());
                    workPlace.getQueueFour().addLast(order);
                }
            }
        }

        core.dataHandling();
    }
}
