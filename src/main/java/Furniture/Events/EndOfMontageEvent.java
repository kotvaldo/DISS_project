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

public class EndOfMontageEvent extends Event {
    private final Order order;
    private final Worker worker;

    protected EndOfMontageEvent(double time, int priority, SimulationCore simulationCore, Order order, Worker worker) {
        super(time, priority, simulationCore);
        this.order = order;
        this.worker = worker;

    }

    @Override
    public void Execute() {
        FurnitureEventCore core = (FurnitureEventCore) simulationCore;
        worker.setOrder(null, this.time);
        //Order finish
        order.setState(OrderStateValues.ORDER_DONE.getValue());
        order.getWorkPlace().setOrder(null);
        order.setWorkPlace(null);
        order.setEndTime(time);
        core.getAverageTimeOfWorking().add(order.getTimeOfWork());
        core.setCountOfFinishedOrders(core.getCountOfFinishedOrders() + 1);


        //Again Montage Planning
        Worker targetWorkerForMontage = null;
        for (Worker w : core.getWorkersC()) {
            if (w.getCurrentState() == WorkerBussyState.NON_BUSY_WORKER.getValue()) {
                targetWorkerForMontage = w;
                break;
            }
        }

        if (!core.getQueueMontage().isEmpty() && targetWorkerForMontage != null) {
            Order nextOrder = core.getQueueMontage().removeFirst();
            double newTime = this.time + Utility.calculateFourth(nextOrder, core, targetWorkerForMontage);
            if (newTime < core.getEndTime()) {
                nextOrder.setState(OrderStateValues.PROCESSING_MONTAGE.getValue());
                targetWorkerForMontage.setOrder(nextOrder, this.time);
                nextOrder.addToTimeOfWork(newTime - time);
                core.addEvent(new EndOfMontageEvent(newTime, PriorityValues.IMPORTANT_EVENT.getValue(), this.simulationCore, nextOrder, targetWorkerForMontage));
            }
        }

    }
}
