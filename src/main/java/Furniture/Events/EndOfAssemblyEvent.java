package Furniture.Events;

import EventSimulation.Event;
import Furniture.Entity.Order;
import Furniture.Entity.Worker;
import Furniture.Enums.OrderStateValues;
import Furniture.Enums.PriorityValues;
import Furniture.FurnitureEventCore;
import SimulationCore.SimulationCore;
import Utility.Utility;

public class EndOfAssemblyEvent extends Event {
    private final Worker worker;
    private final Order order;

    public EndOfAssemblyEvent(double time, int priority, SimulationCore simulationCore, Order order, Worker worker) {
        super(time, priority, simulationCore);
        this.order = order;
        this.worker = worker;
    }

    @Override
    public void Execute() {
        FurnitureEventCore core = (FurnitureEventCore) simulationCore;

        // Uvoľníme workerB
        worker.setOrder(null, this.time);
        core.getFreeWorkersB().addLast(worker);

        // ---------------------------------------------------
        // Typ 3 -> potrebuje montage
        // ---------------------------------------------------

        if (order.getType() == 3) {
            if (!core.getFreeWorkersC().isEmpty()) {
                Worker targetWorkerForMontage = core.getFreeWorkersC().removeFirst();
                Order orderToProcess;

                if (!core.getQueueMontage().isEmpty()) {
                    orderToProcess = core.getQueueMontage().removeFirst();
                    core.getQueueMontage().addLast(this.order);
                    this.order.setState(OrderStateValues.WAITING_IN_QUEUE_4.getValue());
                } else {
                    orderToProcess = this.order;
                }

                double newTime = this.time + Utility.calculateFourth(orderToProcess, core, targetWorkerForMontage);
                if (newTime < core.getEndTime()) {
                    orderToProcess.setState(OrderStateValues.PROCESSING_MONTAGE.getValue());
                    targetWorkerForMontage.setOrder(orderToProcess, this.time);
                    core.addEvent(new EndOfMontageEvent(newTime, PriorityValues.IMPORTANT_EVENT.getValue(), simulationCore, orderToProcess, targetWorkerForMontage));
                }

            } else {
                // Ak nie je voľný worker
                order.setState(OrderStateValues.WAITING_IN_QUEUE_4.getValue());
                core.getQueueMontage().addLast(order);
            }
        }  else {
            order.setState(OrderStateValues.ORDER_DONE.getValue());
            order.getWorkPlace().setOrder(null);
            order.setWorkPlace(null);
            order.setEndTime(time);
            core.getAverageTimeOfWorking().add(order.getTimeOfWork());
            core.setCountOfFinishedOrders(core.getCountOfFinishedOrders() + 1);
        }


        if (!core.getQueueAssembly().isEmpty() && !core.getFreeWorkersB().isEmpty()) {
            Worker targetWorkerForAssemblyAgain = core.getFreeWorkersB().removeFirst();
            Order nextOrder = core.getQueueAssembly().removeFirst();
            double newTime = this.time + Utility.calculateThird(nextOrder, core, targetWorkerForAssemblyAgain);
            if (newTime < core.getEndTime()) {
                nextOrder.setState(OrderStateValues.PROCESSING_ASSEMBLY.getValue());
                targetWorkerForAssemblyAgain.setOrder(nextOrder, this.time);
                core.addEvent(new EndOfAssemblyEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), simulationCore, nextOrder, targetWorkerForAssemblyAgain));
            }
        }
    }
}
