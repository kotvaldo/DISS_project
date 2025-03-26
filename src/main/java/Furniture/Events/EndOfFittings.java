package Furniture.Events;

import EventSimulation.Event;
import Furniture.Entity.Order;
import Furniture.Entity.WorkPlace;
import Furniture.Entity.Worker;
import Furniture.Enums.OrderStateValues;
import Furniture.Enums.PriorityValues;
import Furniture.Enums.WorkerStateValues;
import Furniture.FurnitureEventCore;
import SimulationCore.SimulationCore;
import Utility.Utility;

public class EndOfFittings extends Event {
    private Order order;
    private Worker worker;

    protected EndOfFittings(double time, int priority, SimulationCore simulationCore, Order order, Worker worker) {
        super(time, priority, simulationCore);
        this.order = order;
        this.worker = worker;
    }

    @Override
    public void Execute() {
        FurnitureEventCore core = (FurnitureEventCore) simulationCore;
        WorkPlace workPlace = core.getWorkPlace();

        if (workPlace.getQueueOne().isEmpty()) {
            worker.setCurrentState(WorkerStateValues.NON_BUSSY_WORKER.getValue());
        } else {
            Order order = workPlace.getQueueOne().removeFirst();
            double newTime = this.time + Utility.calculateFirstTime(order, core);
            if(newTime < core.getEndTime()) {
                core.addEvent(new EndOfCuttingEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this.simulationCore, order, worker));
            }
        }
        order.setState(OrderStateValues.ORDER_DONE.getValue());
        core.dataHandling();
    }
}
