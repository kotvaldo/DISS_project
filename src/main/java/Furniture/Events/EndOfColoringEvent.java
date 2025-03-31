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
        worker.setOrder(null, this.time);

        //planning assembly

        Worker targetWorkerAssembly = null;
        for (Worker w : core.getWorkersB()) {
            if (w.getCurrentState() == WorkerBussyState.NON_BUSY_WORKER.getValue()) {
                targetWorkerAssembly = w;
                break;
            }
        }


        if (targetWorkerAssembly == null) {
            core.getQueueAssembly().addLast(this.order);
            this.order.setState(OrderStateValues.WAITING_IN_QUEUE_3.getValue());
        } else {
            if (core.getQueueAssembly().isEmpty()) {
                double ttt = Utility.calculateThird(this.order, core, targetWorkerAssembly);
                double newTime = this.time + ttt;
                if (newTime < core.getEndTime()) {
                    this.order.setState(OrderStateValues.PROCESSING_ASSEMBLY.getValue());
                    targetWorkerAssembly.setOrder(order, this.time);
                    order.addToTimeOfWork(newTime - time);
                    core.addEvent(new EndOfAssemblyEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this.simulationCore, this.order, targetWorkerAssembly));
                }
            } else {
                this.order.setState(OrderStateValues.WAITING_IN_QUEUE_3.getValue());
                core.getQueueAssembly().addLast(this.order);
            }
        }



        //planing montage if possible
        Worker targetWorkerForMontage = null;
        for (Worker w : core.getWorkersC()) {
            if (w.getCurrentState() == WorkerBussyState.NON_BUSY_WORKER.getValue()) {
                targetWorkerForMontage = w;
                break;
            }
        }

        if (!core.getQueueMontage().isEmpty() && targetWorkerForMontage != null) {
            Order montageOrder = core.getQueueMontage().removeFirst();
            double newTime = this.time + Utility.calculateFourth(montageOrder, core, targetWorkerForMontage);
            if (newTime < core.getEndTime()) {
                montageOrder.setState(OrderStateValues.PROCESSING_MONTAGE.getValue());
                targetWorkerForMontage.setOrder(montageOrder, this.time);
                //montageOrder.addToTimeOfWork(newTime - time);

                core.addEvent(new EndOfMontageEvent(newTime, PriorityValues.IMPORTANT_EVENT.getValue(), simulationCore, montageOrder, targetWorkerForMontage));
            }
        }

        //planing montage if possible
        Worker targetWorkerForColoringAgain = null;
        for (Worker w : core.getWorkersC()) {
            if (w.getCurrentState() == WorkerBussyState.NON_BUSY_WORKER.getValue()) {
                targetWorkerForColoringAgain = w;
                break;
            }
        }

        if (!core.getQueueColoring().isEmpty() && targetWorkerForColoringAgain != null) {
            Order nextColoringOrder = core.getQueueColoring().removeFirst();
            double newTime = this.time + Utility.calculateSecondTime(nextColoringOrder, core, targetWorkerForColoringAgain);
            if (newTime < core.getEndTime()) {
                nextColoringOrder.setState(OrderStateValues.PROCESSING_COLORING.getValue());
                targetWorkerForColoringAgain.setOrder(nextColoringOrder, this.time);
                //nextColoringOrder.addToTimeOfWork(newTime - time);
                core.addEvent(new EndOfColoringEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), simulationCore, nextColoringOrder, targetWorkerForColoringAgain));
            }
        }
    }
}
