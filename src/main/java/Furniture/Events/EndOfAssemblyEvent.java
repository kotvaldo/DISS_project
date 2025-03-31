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

        worker.setCurrentState(WorkerBussyState.NON_BUSY_WORKER.getValue());
        worker.setOrder(null);

        if (order.getType() == 3) {
            Worker targetWorkerForMontage = null;
            for (Worker w : core.getWorkersC()) {
                if (w.getCurrentState() == WorkerBussyState.NON_BUSY_WORKER.getValue()) {
                    targetWorkerForMontage = w;
                    break;
                }
            }

            if (targetWorkerForMontage == null || !core.getQueueMontage().isEmpty()) {
                order.setState(OrderStateValues.WAITING_IN_QUEUE_4.getValue());
                core.getQueueMontage().addLast(order);
            } else {
                double newTime = this.time + Utility.calculateFourth(order, core, targetWorkerForMontage);
                if (newTime < core.getEndTime()) {
                    order.setState(OrderStateValues.PROCESSING_MONTAGE.getValue());
                    targetWorkerForMontage.setCurrentState(WorkerBussyState.BUSY_WORKER.getValue());
                    targetWorkerForMontage.setOrder(order);
                    core.addEvent(new EndOfMontageEvent(newTime, PriorityValues.IMPORTANT_EVENT.getValue(), simulationCore, order, targetWorkerForMontage));
                }
            }
        } else {


            //Order done if not 3
            order.setState(OrderStateValues.ORDER_DONE.getValue());
            order.getWorkPlace().setOrder(null);
            order.setWorkPlace(null);
            order.setEndTime(time);
            core.getAverageTimeOfWorking().add(order.getTimeOfWork());

        }


        Worker targetWorkerForAssemblyAgain = null;
        for (Worker w : core.getWorkersB()) {
            if (w.getCurrentState() == WorkerBussyState.NON_BUSY_WORKER.getValue()) {
                targetWorkerForAssemblyAgain = w;
                break;
            }
        }

        if (!core.getQueueAssembly().isEmpty() && targetWorkerForAssemblyAgain != null) {
            Order nextOrder = core.getQueueAssembly().removeFirst();
            double newTime = this.time + Utility.calculateThird(nextOrder, core, targetWorkerForAssemblyAgain);
            if (newTime < core.getEndTime()) {
                nextOrder.setState(OrderStateValues.PROCESSING_ASSEMBLY.getValue());
                targetWorkerForAssemblyAgain.setCurrentState(WorkerBussyState.BUSY_WORKER.getValue());
                targetWorkerForAssemblyAgain.setOrder(nextOrder);
                core.addEvent(new EndOfAssemblyEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), simulationCore, nextOrder, targetWorkerForAssemblyAgain));

            }
        }

    }
}
