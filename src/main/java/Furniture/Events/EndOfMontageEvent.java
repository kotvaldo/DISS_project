package Furniture.Events;

import EventSimulation.Event;
import Furniture.Entity.Order;
import Furniture.Entity.WorkerC;
import Furniture.Enums.OrderStateValues;
import Furniture.Enums.PriorityValues;
import Furniture.FurnitureEventCore;
import SimulationCore.SimulationCore;
import Utility.Utility;

public class EndOfMontageEvent extends Event {
    private final Order order;
    private final WorkerC worker;

    protected EndOfMontageEvent(double time, int priority, SimulationCore simulationCore, Order order, WorkerC worker) {
        super(time, priority, simulationCore);
        this.order = order;
        this.worker = worker;
    }

    @Override
    public void Execute() {
        FurnitureEventCore core = (FurnitureEventCore) simulationCore;

        worker.getUtilisation().stop(this.time);
        // Uvoľní workerC
        worker.setOrder(null, this.time);
        core.getFreeWorkersC().addLast(worker);

        // Dokončenie objednávky
        order.setState(OrderStateValues.ORDER_DONE.getValue());
        order.getWorkPlace().setOrder(null);
        order.setWorkPlace(null);
        order.setEndTime(time);
        core.getAverageTimeOfWorking().add(order.getTimeOfWorkArrivalAndEnd());
        core.setCountOfFinishedOrders(core.getCountOfFinishedOrders() + 1);



        if (!core.getQueueMontage().isEmpty() && !core.getFreeWorkersC().isEmpty()) {
            WorkerC targetWorkerForMontage = core.getFreeWorkersC().removeFirst();
            Order nextOrder = core.getQueueMontage().removeFirst();
            core.recordQueueLengthMontage(this.time);
            double waitingTime = nextOrder.getQueueMontageLeaveTime() - nextOrder.getQueueMontageEnterTime();
            if (nextOrder.getQueueMontageEnterTime() >= 0 && nextOrder.getQueueMontageLeaveTime() >= 0 && waitingTime > 0) {
                core.getAverageTimeInQueueMontage().add(waitingTime);
            }
            nextOrder.setQueueMontageLeaveTime(this.time);
            double newTime = this.time + Utility.calculateMontageTime(nextOrder, core, targetWorkerForMontage);
            if (newTime < core.getEndTime()) {
                targetWorkerForMontage.getUtilisation().start(this.time);
                nextOrder.setState(OrderStateValues.PROCESSING_MONTAGE.getValue());
                targetWorkerForMontage.setOrder(nextOrder, this.time);
                core.addEvent(new EndOfMontageEvent(newTime, PriorityValues.IMPORTANT_EVENT.getValue(), this.simulationCore, nextOrder, targetWorkerForMontage));
            }
        }

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
