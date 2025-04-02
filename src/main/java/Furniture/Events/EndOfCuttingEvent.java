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
        worker.getUtilisation().stop(this.time);


        worker.setOrder(null, this.time);
        core.getFreeWorkersA().addLast(worker);

        // plánovanie fázy coloring (workerC)
        if (!core.getFreeWorkersC().isEmpty()) {
            WorkerC targetWorkerColoring = core.getFreeWorkersC().removeFirst();

            Order orderToProcess;

            if (!core.getQueueColoring().isEmpty()) {
                orderToProcess = core.getQueueColoring().removeFirst();
                core.recordQueueLengthColoring(this.time);

                if (!core.getQueueColoring().contains(this.order)) {
                    core.getQueueColoring().addLast(this.order);
                    core.recordQueueLengthColoring(this.time);
                    this.order.setState(OrderStateValues.WAITING_IN_QUEUE_2.getValue());
                }
            } else {
                // ak je queue prázdna, spracujem aktuálnu objednávku
                orderToProcess = this.order;
            }

            double coloringTime = Utility.calculateColoringTime(orderToProcess, core, targetWorkerColoring);
            double newTime = this.time + coloringTime;

            if (newTime < core.getEndTime()) {
                targetWorkerColoring.getUtilisation().start(this.time);
                orderToProcess.setState(OrderStateValues.PROCESSING_COLORING.getValue());
                targetWorkerColoring.setOrder(orderToProcess, this.time);
                core.addEvent(new EndOfColoringEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), simulationCore, orderToProcess, targetWorkerColoring));
            }

        } else {
            // Ak WorkerC nie je voľný, vždy pridám do queue
            core.getQueueColoring().addLast(this.order);
            core.recordQueueLengthColoring(this.time);
            this.order.setState(OrderStateValues.WAITING_IN_QUEUE_2.getValue());
        }




        // planning cutting event again

        if (!core.getFreeWorkersA().isEmpty() && !core.getQueueCutting().isEmpty()) {
            WorkerA targetWorkerForCuttingAgain = core.getFreeWorkersA().removeFirst();
            Order nextOrder = core.getQueueCutting().removeFirst();
            double cuttingTime = Utility.calculateCuttingTime(nextOrder, core, targetWorkerForCuttingAgain);
            double newTime = this.time + cuttingTime;
            if (newTime < core.getEndTime()) {
                core.recordQueueLengthCutting(this.time);
                targetWorkerForCuttingAgain.getUtilisation().start(this.time);
                nextOrder.setState(OrderStateValues.PROCESSING_CUTTING.getValue());
                targetWorkerForCuttingAgain.setOrder(nextOrder, this.time);
                //nextOrder.addToTimeOfWork(newTime - time);

                core.addEvent(new EndOfCuttingEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this.simulationCore, nextOrder, targetWorkerForCuttingAgain));
            }
        }

    }


}
