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

public class EndOfCuttingEvent extends Event {
    private Order order;
    private Worker worker;

    public EndOfCuttingEvent(double time, int priority, SimulationCore simulationCore, Order order, Worker worker) {
        super(time, priority, simulationCore);
        this.order = order;
        this.worker = worker;
    }


    @Override
    public void Execute() {
        FurnitureEventCore core = (FurnitureEventCore) simulationCore;

        worker.setOrder(null, this.time);
        core.getFreeWorkersA().addLast(worker);


        if (!core.getFreeWorkersC().isEmpty()) {
            Worker targetWorkerColoring = core.getFreeWorkersC().removeFirst();

            Order orderToProcess;
            if (!core.getQueueColoring().isEmpty()) {
                orderToProcess = core.getQueueColoring().removeFirst();
                core.getQueueColoring().addLast(this.order);
                this.order.setState(OrderStateValues.WAITING_IN_QUEUE_2.getValue());
            } else {
                orderToProcess = this.order;
            }

            double ttt = Utility.calculateSecondTime(orderToProcess, core, targetWorkerColoring);
            double newTime = this.time + ttt;
            if (newTime < core.getEndTime()) {
                orderToProcess.setState(OrderStateValues.PROCESSING_COLORING.getValue());
                targetWorkerColoring.setOrder(orderToProcess, this.time);
                core.addEvent(new EndOfColoringEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this.simulationCore, orderToProcess, targetWorkerColoring));
            }

        } else {
            core.getQueueColoring().addLast(this.order);
            this.order.setState(OrderStateValues.WAITING_IN_QUEUE_2.getValue());
        }




        // planning cutting event again



        if (!core.getFreeWorkersA().isEmpty() && !core.getQueueCutting().isEmpty()) {
            Worker targetWorkerForCuttingAgain = core.getFreeWorkersA().removeFirst();
            Order nextOrder = core.getQueueCutting().removeFirst();
            double timeOfWork = Utility.calculateFirstTime(nextOrder, core, targetWorkerForCuttingAgain);
            double newTime = this.time + timeOfWork;
            if (newTime < core.getEndTime()) {
                nextOrder.setState(OrderStateValues.PROCESSING_CUTTING.getValue());
                targetWorkerForCuttingAgain.setOrder(nextOrder, this.time);
                nextOrder.addToTimeOfWork(newTime - time);

                core.addEvent(new EndOfCuttingEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this.simulationCore, nextOrder, targetWorkerForCuttingAgain));
            }
        }

    }


}
