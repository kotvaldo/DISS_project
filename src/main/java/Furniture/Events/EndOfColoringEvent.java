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
        worker.setCurrentState(WorkerBussyState.NON_BUSY_WORKER.getValue());
        worker.setOrder(null);

        //planning assembly

        Worker targetWorkerForAssembly = null;
        for (Worker w : core.getWorkersB()) {
            if (w.getCurrentState() == WorkerBussyState.NON_BUSY_WORKER.getValue()) {
                targetWorkerForAssembly = w;
                break;
            }
        }

        if (targetWorkerForAssembly == null || !core.getQueueAssembly().isEmpty()) {
            order.setState(OrderStateValues.WAITING_IN_QUEUE_3.getValue());
            core.getQueueAssembly().addLast(order);
        } else {
            double newTime = this.time + Utility.calculateThird(order, core, targetWorkerForAssembly);
            if (newTime < core.getEndTime()) {
                order.setState(OrderStateValues.PROCESSING_ASSEMBLY.getValue());
                targetWorkerForAssembly.setCurrentState(WorkerBussyState.BUSY_WORKER.getValue());
                targetWorkerForAssembly.setOrder(order);
                core.addEvent(new EndOfAssemblyEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), simulationCore, order, targetWorkerForAssembly));
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
                targetWorkerForMontage.setOrder(montageOrder);
                targetWorkerForMontage.setCurrentState(WorkerBussyState.BUSY_WORKER.getValue());
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
                targetWorkerForColoringAgain.setCurrentState(WorkerBussyState.BUSY_WORKER.getValue());
                nextColoringOrder.setState(OrderStateValues.PROCESSING_COLORING.getValue());
                targetWorkerForColoringAgain.setOrder(nextColoringOrder);
                core.addEvent(new EndOfColoringEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), simulationCore, nextColoringOrder, targetWorkerForColoringAgain));
            }
        }
    }
}
