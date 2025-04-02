package Furniture.Events;

import EventSimulation.Event;
import Furniture.Entity.Order;
import Furniture.Entity.WorkerB;
import Furniture.Entity.WorkerC;
import Furniture.Enums.OrderStateValues;
import Furniture.Enums.PriorityValues;
import Furniture.FurnitureEventCore;
import SimulationCore.SimulationCore;
import Utility.Utility;

public class EndOfAssemblyEvent extends Event {
    private final WorkerB worker;
    private final Order order;

    public EndOfAssemblyEvent(double time, int priority, SimulationCore simulationCore, Order order, WorkerB worker) {
        super(time, priority, simulationCore);
        this.order = order;
        this.worker = worker;
    }

    @Override
    public void Execute() {
        FurnitureEventCore core = (FurnitureEventCore) simulationCore;
        worker.getUtilisation().stop(this.time);

        // Uvoľníme workerB
        worker.setOrder(null, this.time);
        core.getFreeWorkersB().addLast(worker);

        // ---------------------------------------------------
        // Typ 3 -> potrebuje montage kovani
        // ---------------------------------------------------

        if (order.getType() == 3) {
            if (!core.getFreeWorkersC().isEmpty()) {
                WorkerC targetWorkerForMontage = core.getFreeWorkersC().removeFirst();
                Order orderToProcess;

                if (!core.getQueueMontage().isEmpty()) {
                    orderToProcess = core.getQueueMontage().removeFirst();
                    // aktuálny order (this.order) ide do fronty vždy
                    core.getQueueMontage().addLast(this.order);
                    core.recordQueueLengthMontage(this.time);
                    this.order.setState(OrderStateValues.WAITING_IN_QUEUE_4.getValue());
                } else {
                    orderToProcess = this.order;
                }

                double montageTime = Utility.calculateMontageTime(orderToProcess, core, targetWorkerForMontage);
                double newTime = this.time + montageTime;

                if (newTime < core.getEndTime()) {
                    targetWorkerForMontage.getUtilisation().start(this.time);
                    orderToProcess.setState(OrderStateValues.PROCESSING_MONTAGE.getValue());
                    targetWorkerForMontage.setOrder(orderToProcess, this.time);
                    core.addEvent(new EndOfMontageEvent(newTime, PriorityValues.IMPORTANT_EVENT.getValue(), simulationCore, orderToProcess, targetWorkerForMontage));
                }

            } else {
                // Ak worker C nie je voľný, pridaj objednávku vždy do queue
                core.getQueueMontage().addLast(this.order);
                core.recordQueueLengthMontage(this.time);
                this.order.setState(OrderStateValues.WAITING_IN_QUEUE_4.getValue());
            }
        } else {
            //ak order nie je typu skrina, tak skoncim objednavku
            order.setState(OrderStateValues.ORDER_DONE.getValue());
            order.getWorkPlace().setOrder(null);
            order.setWorkPlace(null);
            order.setEndTime(time);
            core.getAverageTimeOfWorking().add(order.getTimeOfWorkArrivalAndEnd());
            core.setCountOfFinishedOrders(core.getCountOfFinishedOrders() + 1);
        }


        // Znovu planovanie skladania

        //kontrola ci je nieco v queue a zaroven ci je volny pracovnik B
        if (!core.getQueueAssembly().isEmpty() && !core.getFreeWorkersB().isEmpty()) {
            //ak ano vytiahnem si order a aj workera

            WorkerB targetWorkerForAssemblyAgain = core.getFreeWorkersB().removeFirst();
            Order nextOrder = core.getQueueAssembly().removeFirst();
            //vypocet casu

            double assemblyTime = Utility.calculateAssemblyTime(nextOrder, core, targetWorkerForAssemblyAgain);
            double newTime = this.time + assemblyTime;

            if (newTime < core.getEndTime()) {
                core.recordQueueLengthAssembly(this.time);
                targetWorkerForAssemblyAgain.getUtilisation().start(this.time);
                nextOrder.setState(OrderStateValues.PROCESSING_ASSEMBLY.getValue());
                targetWorkerForAssemblyAgain.setOrder(nextOrder, this.time);
                core.addEvent(new EndOfAssemblyEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), simulationCore, nextOrder, targetWorkerForAssemblyAgain));
            }
        }
    }
}
