package Furniture.Events;

import EventSimulation.Event;
import Furniture.Entity.Order;
import Furniture.Entity.Worker;
import Furniture.Enums.OrderStateValues;
import Furniture.Enums.PriorityValues;
import Furniture.FurnitureEventCore;
import SimulationCore.SimulationCore;
import Utility.Utility;

public class EndOfColoringEvent extends Event {
    private final Worker worker;
    private final Order order;

    protected EndOfColoringEvent(double time, int priority, SimulationCore simulationCore, Order order, Worker worker) {
        super(time, priority, simulationCore);
        this.order = order;
        this.worker = worker;
    }

    @Override
    public void Execute() {
        FurnitureEventCore core = (FurnitureEventCore) simulationCore;
        core.recordQueueLengths(this.time);


        worker.setOrder(null, this.time);
        core.getFreeWorkersC().addLast(worker);


        if (!core.getFreeWorkersB().isEmpty()) {
            Worker targetWorkerAssembly = core.getFreeWorkersB().removeFirst();
            Order orderToProcess;

            if (!core.getQueueAssembly().isEmpty()) {
                orderToProcess = core.getQueueAssembly().removeFirst();
                core.getQueueAssembly().addLast(this.order);
                this.order.setState(OrderStateValues.WAITING_IN_QUEUE_3.getValue());
            } else {
                orderToProcess = this.order;
            }

            double ttt = Utility.calculateThird(orderToProcess, core, targetWorkerAssembly);
            double newTime = this.time + ttt;
            if (newTime < core.getEndTime()) {
                orderToProcess.setState(OrderStateValues.PROCESSING_ASSEMBLY.getValue());
                targetWorkerAssembly.setOrder(orderToProcess, this.time);
                core.addEvent(new EndOfAssemblyEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this.simulationCore, orderToProcess, targetWorkerAssembly));
            }

        } else {
            core.getQueueAssembly().addLast(this.order);
            this.order.setState(OrderStateValues.WAITING_IN_QUEUE_3.getValue());
        }



        // ----------------------------
        // MONTAGE
        // ----------------------------

        if (!core.getQueueMontage().isEmpty() && !core.getFreeWorkersC().isEmpty()) {
            Worker targetWorkerForMontage = core.getFreeWorkersC().removeFirst();
            Order montageOrder = core.getQueueMontage().removeFirst();
            double newTime = this.time + Utility.calculateFourth(montageOrder, core, targetWorkerForMontage);
            if (newTime < core.getEndTime()) {
                montageOrder.setState(OrderStateValues.PROCESSING_MONTAGE.getValue());
                targetWorkerForMontage.setOrder(montageOrder, this.time);
                core.addEvent(new EndOfMontageEvent(newTime, PriorityValues.IMPORTANT_EVENT.getValue(), simulationCore, montageOrder, targetWorkerForMontage));
            }
        }

        // ----------------------------
        // COLORING (znova)
        // ----------------------------

        if (!core.getQueueColoring().isEmpty() && !core.getFreeWorkersC().isEmpty()) {
            Worker targetWorkerForColoringAgain = core.getFreeWorkersC().removeFirst();
            Order nextColoringOrder = core.getQueueColoring().removeFirst();
            double newTime = this.time + Utility.calculateSecondTime(nextColoringOrder, core, targetWorkerForColoringAgain);
            if (newTime < core.getEndTime()) {
                nextColoringOrder.setState(OrderStateValues.PROCESSING_COLORING.getValue());
                targetWorkerForColoringAgain.setOrder(nextColoringOrder, this.time);
                core.addEvent(new EndOfColoringEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), simulationCore, nextColoringOrder, targetWorkerForColoringAgain));
            }
        }
    }
}
