package Furniture.Events;

import EventSimulation.Event;
import Furniture.Entity.Order;
import Furniture.Entity.WorkPlace;
import Furniture.Entity.WorkerA;
import Furniture.Enums.OrderStateValues;
import Furniture.Enums.PriorityValues;
import Furniture.FurnitureEventCore;
import IDGenerator.IDGenerator;
import SimulationCore.SimulationCore;
import Utility.Utility;

import java.util.LinkedList;

public class EndOfAccessingEvent extends Event {

    Order order;
    WorkerA workerA;

    public EndOfAccessingEvent(double time, int priority, SimulationCore simulationCore, Order order, WorkerA worker) {
        super(time, priority, simulationCore);
        this.order = order;
        this.workerA = worker;
    }

    @Override
    public void Execute() {
        FurnitureEventCore core = (FurnitureEventCore) simulationCore;


        double cuttingTime = Utility.calculateCuttingTime(order, core, workerA);
        double newTime = this.time + cuttingTime;

        if (newTime < core.getEndTime()) {
            order.setState(OrderStateValues.PROCESSING_CUTTING.getValue());
            core.addEvent(new EndOfCuttingEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this.simulationCore, order, workerA));
        }

        if (!core.getFreeWorkersA().isEmpty() && !core.getQueueCutting().isEmpty()) {
            WorkerA targetWorkerForCuttingAgain = core.getFreeWorkersA().removeFirst();
            Order nextOrder = core.getQueueCutting().removeFirst();
            nextOrder.setQueueCuttingLeaveTime(this.time);
            core.recordQueueLengthCutting(this.time);
            double waitingTime = nextOrder.getQueueCuttingLeaveTime() - nextOrder.getQueueCuttingEnterTime();
            if (nextOrder.getQueueCuttingEnterTime() >= 0 && nextOrder.getQueueCuttingLeaveTime() >= 0 && waitingTime > 0) {
                core.getAverageTimeInQueueCutting().add(waitingTime);
            }

            double cutting = Utility.calculateAccessingTime(core);
            double newtimeNew = this.time + cuttingTime;

            if (newTime < core.getEndTime()) {

                targetWorkerForCuttingAgain.getUtilisation().start(this.time);
                nextOrder.setQueueCuttingLeaveTime(this.time);
                nextOrder.setState(OrderStateValues.ACCESSING_NEW_ORDER.getValue());
                targetWorkerForCuttingAgain.setOrder(nextOrder, this.time);
                core.addEvent(new EndOfAccessingEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this.simulationCore, nextOrder, targetWorkerForCuttingAgain));
            }
        }


    }
}
