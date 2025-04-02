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
        core.recordQueueLengths(this.time);

        //uvolnenie workera C po coloringu
        worker.setOrder(null, this.time);
        core.getFreeWorkersC().addLast(worker);


        //planovanie assembly eventu

        // plánovanie Assembly fázy (Worker B)
        if (!core.getFreeWorkersB().isEmpty()) {
            WorkerB targetWorkerAssembly = core.getFreeWorkersB().removeFirst();
            order.setState(OrderStateValues.PROCESSING_ASSEMBLY.getValue());

            double assemblyTime = Utility.calculateAssemblyTime(order, core, targetWorkerAssembly);
            double newTime = this.time + assemblyTime;

            targetWorkerAssembly.setOrder(order, this.time);
            core.addEvent(new EndOfAssemblyEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), simulationCore, order, targetWorkerAssembly));

        } else {
            core.getQueueAssembly().addLast(order);
            order.setState(OrderStateValues.WAITING_IN_QUEUE_3.getValue());
        }





        // ----------------------------
        // MONTAGE
        // ----------------------------

        if (!core.getQueueMontage().isEmpty() && !core.getFreeWorkersC().isEmpty()) {
            WorkerC targetWorkerForMontage = core.getFreeWorkersC().removeFirst();
            Order montageOrder = core.getQueueMontage().removeFirst();
            double currentTime = this.time;
            double montageTime = Utility.calculateMontageTime(montageOrder, core, targetWorkerForMontage);
            double newTime = currentTime + montageTime;
            if (newTime < core.getEndTime()) {
                montageOrder.setState(OrderStateValues.PROCESSING_MONTAGE.getValue());
                targetWorkerForMontage.setOrder(montageOrder, this.time);
                core.addEvent(new EndOfMontageEvent(newTime, PriorityValues.IMPORTANT_EVENT.getValue(), simulationCore, montageOrder, targetWorkerForMontage));
            }

        }
        if(order.getId() == 500) {
            order.getId();
        }
        // ----------------------------
        // COLORING (znova)
        // ----------------------------

        if (!core.getQueueColoring().isEmpty() && !core.getFreeWorkersC().isEmpty()) {
            WorkerC targetWorkerForColoringAgain = core.getFreeWorkersC().removeFirst();
            Order nextColoringOrder = core.getQueueColoring().removeFirst();
            double coloringAgainTime = Utility.calculateColoringTime(nextColoringOrder, core, targetWorkerForColoringAgain);
            double newTime = this.time + coloringAgainTime;

            if (newTime < core.getEndTime()) {
                nextColoringOrder.setState(OrderStateValues.PROCESSING_COLORING.getValue());
                targetWorkerForColoringAgain.setOrder(nextColoringOrder, this.time);
                core.addEvent(new EndOfColoringEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), simulationCore, nextColoringOrder, targetWorkerForColoringAgain));
            }
        }
    }
}
