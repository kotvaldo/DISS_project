package Furniture.Events;

import EventSimulation.Event;
import Furniture.Entity.Order;
import Furniture.Entity.Worker;
import Furniture.Entity.WorkerB;
import Furniture.Entity.WorkerC;
import Furniture.Enums.OrderStateValues;
import Furniture.Enums.PriorityValues;
import Furniture.FurnitureEventCore;
import SimulationCore.SimulationCore;
import Utility.Utility;

public class EndOfColoringEvent extends Event {
    private final WorkerC worker;
    private final Order order;

    protected EndOfColoringEvent(double time, int priority, SimulationCore simulationCore, Order order, WorkerC worker) {
        super(time, priority, simulationCore);
        this.order = order;
        this.worker = worker;
    }

    @Override
    public void Execute() {
        FurnitureEventCore core = (FurnitureEventCore) simulationCore;

        worker.getUtilisation().stop(this.time);
        worker.setOrder(null, this.time);
        core.getFreeWorkersC().addLast(worker);

        // ----------------------------
        // plánovanie assembly eventu
        // ----------------------------

        if (!core.getFreeWorkersB().isEmpty()) {
            WorkerB targetWorkerAssembly = core.getFreeWorkersB().removeFirst();
            Order orderToProcess;

            if (!core.getQueueAssembly().isEmpty()) {
                orderToProcess = core.getQueueAssembly().removeFirst();
                orderToProcess.setQueueAssemblyLeaveTime(this.time);
                core.recordQueueLengthAssembly(this.time);

                double waitingTime = orderToProcess.getQueueAssemblyLeaveTime() - orderToProcess.getQueueAssemblyEnterTime();
                if (orderToProcess.getQueueAssemblyEnterTime() >= 0 && orderToProcess.getQueueAssemblyLeaveTime() >= 0 && waitingTime > 0) {
                    core.getAverageTimeInQueueAssembly().add(waitingTime);
                }

                if(!core.getQueueAssembly().contains(this.order)) {
                    this.order.setQueueAssemblyEnterTime(this.time);
                    core.getQueueAssembly().addLast(this.order);
                    this.order.setState(OrderStateValues.WAITING_IN_QUEUE_3.getValue());
                    core.recordQueueLengthAssembly(this.time);
                }

            } else {
                orderToProcess = this.order;
            }

            double assemblyTime = Utility.calculateAssemblyTime(orderToProcess, core, targetWorkerAssembly);
            double newTime = this.time + assemblyTime;

            if (newTime < core.getEndTime()) {
                orderToProcess.setState(OrderStateValues.PROCESSING_ASSEMBLY.getValue());
                targetWorkerAssembly.setOrder(orderToProcess, this.time);
                targetWorkerAssembly.getUtilisation().start(this.time);
                core.addEvent(new EndOfAssemblyEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), simulationCore, orderToProcess, targetWorkerAssembly));
            }

        } else {
            this.order.setQueueAssemblyEnterTime(this.time);
            core.getQueueAssembly().addLast(this.order);
            core.recordQueueLengthAssembly(this.time);
            this.order.setState(OrderStateValues.WAITING_IN_QUEUE_3.getValue());
        }

        // ----------------------------
        // MONTAGE
        // ----------------------------

        if (!core.getQueueMontage().isEmpty() && !core.getFreeWorkersC().isEmpty()) {
            WorkerC targetWorkerForMontage = core.getFreeWorkersC().removeFirst();
            Order montageOrder = core.getQueueMontage().removeFirst();
            montageOrder.setQueueMontageLeaveTime(this.time);
            core.recordQueueLengthMontage(this.time);
            double waitingTime = montageOrder.getQueueMontageLeaveTime() - montageOrder.getQueueMontageEnterTime();
            if (montageOrder.getQueueMontageEnterTime() >= 0 && montageOrder.getQueueMontageLeaveTime() >= 0 && waitingTime > 0) {
                core.getAverageTimeInQueueMontage().add(waitingTime);
            }

            double montageTime = Utility.calculateMontageTime(montageOrder, core, targetWorkerForMontage);
            double newTime = this.time + montageTime;
            if (newTime < core.getEndTime()) {
                montageOrder.setState(OrderStateValues.PROCESSING_MONTAGE.getValue());
                targetWorkerForMontage.getUtilisation().start(this.time);
                targetWorkerForMontage.setOrder(montageOrder, this.time);
                core.addEvent(new EndOfMontageEvent(newTime, PriorityValues.IMPORTANT_EVENT.getValue(), simulationCore, montageOrder, targetWorkerForMontage));
            }
        }

        // ----------------------------
        // COLORING (znova)
        // ----------------------------
        if (!core.getQueueColoring().isEmpty() && !core.getFreeWorkersC().isEmpty()) {
            WorkerC targetWorkerForColoringAgain = core.getFreeWorkersC().removeFirst();
            Order nextColoringOrder = core.getQueueColoring().removeFirst();
            nextColoringOrder.setQueueColoringLeaveTime(this.time);
            core.recordQueueLengthColoring(this.time);

            double coloringAgainTime = Utility.calculateColoringTime(nextColoringOrder, core, targetWorkerForColoringAgain);
            double newTime = this.time + coloringAgainTime;

            if (newTime < core.getEndTime()) {
                targetWorkerForColoringAgain.getUtilisation().start(this.time);
                nextColoringOrder.setState(OrderStateValues.PROCESSING_COLORING.getValue());
                targetWorkerForColoringAgain.setOrder(nextColoringOrder, this.time);
                core.addEvent(new EndOfColoringEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), simulationCore, nextColoringOrder, targetWorkerForColoringAgain));
            }
        }
    }
}
