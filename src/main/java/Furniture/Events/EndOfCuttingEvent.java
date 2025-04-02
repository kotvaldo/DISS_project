package Furniture.Events;

import EventSimulation.Event;
import Furniture.Entity.Order;
import Furniture.Entity.Worker;
import Furniture.Entity.WorkerA;
import Furniture.Entity.WorkerC;
import Furniture.Enums.OrderStateValues;
import Furniture.Enums.PriorityValues;
import Furniture.FurnitureEventCore;
import SimulationCore.SimulationCore;
import Utility.Utility;

public class EndOfCuttingEvent extends Event {
    private Order order;
    private WorkerA worker;

    public EndOfCuttingEvent(double time, int priority, SimulationCore simulationCore, Order order, WorkerA worker) {
        super(time, priority, simulationCore);
        this.order = order;
        this.worker = worker;
    }

    @Override
    public void Execute() {
        FurnitureEventCore core = (FurnitureEventCore) simulationCore;
        core.recordQueueLengths(this.time);

        // uvolníme workerA
        worker.setOrder(null, this.time);
        core.getFreeWorkersA().addLast(worker);

        // === Coloring fáza ===
        // ak je voľný workerC, hneď spracuj order bez zaraďovania do fronty
        if (!core.getFreeWorkersC().isEmpty()) {
            WorkerC targetWorkerColoring = core.getFreeWorkersC().removeFirst();

            order.setState(OrderStateValues.PROCESSING_COLORING.getValue());
            targetWorkerColoring.setOrder(order, this.time);

            double coloringTime = Utility.calculateColoringTime(order, core, targetWorkerColoring);
            double newTime = this.time + coloringTime;

            if (newTime < core.getEndTime()) {
                core.addEvent(new EndOfColoringEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), simulationCore, order, targetWorkerColoring));
            }

        } else {
            // inak ide order do fronty
            core.getQueueColoring().addLast(order);
            order.setState(OrderStateValues.WAITING_IN_QUEUE_2.getValue());
        }

        // === Cutting znova ===
        if (!core.getFreeWorkersA().isEmpty() && !core.getQueueCutting().isEmpty()) {
            WorkerA targetWorkerForCuttingAgain = core.getFreeWorkersA().removeFirst();
            Order nextOrder = core.getQueueCutting().removeFirst();

            double cuttingTime = Utility.calculateCuttingTime(nextOrder, core, targetWorkerForCuttingAgain);
            double newTime = this.time + cuttingTime;

            if (newTime < core.getEndTime()) {
                nextOrder.setState(OrderStateValues.PROCESSING_CUTTING.getValue());
                targetWorkerForCuttingAgain.setOrder(nextOrder, this.time);
                core.addEvent(new EndOfCuttingEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this.simulationCore, nextOrder, targetWorkerForCuttingAgain));
            }
        }
    }
}
