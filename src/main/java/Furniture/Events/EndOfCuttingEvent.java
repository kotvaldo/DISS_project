package Furniture.Events;

import EventSimulation.Event;
import Furniture.Entity.Order;
import Furniture.Entity.Worker;
import Furniture.Enums.OrderStateValues;
import Furniture.Enums.PriorityValues;
import Furniture.Enums.WorkerBussyState;
import Furniture.FurnitureEventCore;
import SimulationCore.SimulationCore;
import Utility.Utility;

public class EndOfCuttingEvent extends Event {
    private Order order;
    private Worker worker;

    public EndOfCuttingEvent(double time, int priority, SimulationCore simulationCore, Order order, Worker worker) {
        super(time, priority, simulationCore);
        this.order = order;
        this.worker = worker;
    }


    @Override
    public void Execute() {
        FurnitureEventCore core = (FurnitureEventCore) simulationCore;

        worker.setOrder(null);
        worker.setCurrentState(WorkerBussyState.NON_BUSY_WORKER.getValue());

        //finding worker for coloring

        Worker targetWorkerColoring = null;
        for (Worker w : core.getWorkersC()) {
            if (w.getCurrentState() == WorkerBussyState.NON_BUSY_WORKER.getValue()) {
                targetWorkerColoring = w;
                break;
            }
        }

        //planning coloring Event


        if (targetWorkerColoring == null) {
            core.getQueueColoring().addLast(this.order);
            this.order.setState(OrderStateValues.WAITING_IN_QUEUE_2.getValue());
        } else {
            if (core.getQueueColoring().isEmpty()) {
                double ttt = Utility.calculateSecondTime(this.order, core, targetWorkerColoring);
                double newTime = this.time + ttt;
                if (newTime < core.getEndTime()) {
                    this.order.setState(OrderStateValues.PROCESSING_COLORING.getValue());
                    targetWorkerColoring.setCurrentState(WorkerBussyState.BUSY_WORKER.getValue());
                    targetWorkerColoring.setOrder(order);
                    core.addEvent(new EndOfColoringEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this.simulationCore, this.order, targetWorkerColoring));
                }
            } else {
                this.order.setState(OrderStateValues.WAITING_IN_QUEUE_2.getValue());
                core.getQueueColoring().addLast(this.order);
            }
        }

        // planning cutting event again

        Worker targetWorkerForCuttingAgain = null;
        for (Worker w : core.getWorkersA()) {
            if (w.getCurrentState() == WorkerBussyState.NON_BUSY_WORKER.getValue()) {
                targetWorkerForCuttingAgain = w;
                break;
            }
        }

        if (targetWorkerForCuttingAgain != null && !core.getQueueCutting().isEmpty()) {
            Order nextOrder = core.getQueueCutting().removeFirst();
            double timeOfWork = Utility.calculateFirstTime(nextOrder, core, targetWorkerForCuttingAgain);
            double newTime = this.time + timeOfWork;
            if (newTime < core.getEndTime()) {
                nextOrder.setState(OrderStateValues.PROCESSING_CUTTING.getValue());
                targetWorkerForCuttingAgain.setCurrentState(WorkerBussyState.BUSY_WORKER.getValue());
                targetWorkerForCuttingAgain.setOrder(nextOrder);
                core.addEvent(new EndOfCuttingEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this.simulationCore, nextOrder, targetWorkerForCuttingAgain));
            }
        }

    }


}
