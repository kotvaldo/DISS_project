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
        core.recordQueueLengths(this.time);


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

        if(order.getId() == 500) {
            order.getId();
        }

        if (!core.getQueueMontage().isEmpty() && !core.getFreeWorkersC().isEmpty()) {
            WorkerC targetWorkerForMontage = core.getFreeWorkersC().removeFirst();
            Order nextOrder = core.getQueueMontage().removeFirst();
            double currentTime = this.time;
            double montageTime = Utility.calculateMontageTime(nextOrder, core, targetWorkerForMontage);
            double newTime = currentTime + montageTime;
            if (newTime < core.getEndTime()) {
                nextOrder.setState(OrderStateValues.PROCESSING_MONTAGE.getValue());
                targetWorkerForMontage.setOrder(nextOrder, this.time);
                core.addEvent(new EndOfMontageEvent(newTime, PriorityValues.IMPORTANT_EVENT.getValue(), this.simulationCore, nextOrder, targetWorkerForMontage));
            }
        }
    }
}
