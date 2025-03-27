package Furniture.Events;

import EventSimulation.Event;
import Furniture.Entity.Order;
import Furniture.Entity.WorkPlace;
import Furniture.Entity.Worker;
import Furniture.Enums.OrderStateValues;
import Furniture.Enums.PriorityValues;
import Furniture.Enums.WorkerBussyState;
import Furniture.FurnitureEventCore;
import SimulationCore.SimulationCore;
import Utility.Utility;

public class EndOfColoringEvent extends Event {
    Worker worker;
    Order order;

    protected EndOfColoringEvent(double time, int priority, SimulationCore simulationCore, Order order, Worker worker) {
        super(time, priority, simulationCore);
        this.order = order;
        this.worker = worker;

        /*System.out.println("[EndOfColoringEvent - KONŠTRUKTOR] Vytvorený pre objednávku ID " + order.getId() +
                ", čas: " + time + ", pracovník ID: " + worker.getId());*/
    }

    @Override
    public void Execute() {
        FurnitureEventCore core = (FurnitureEventCore) simulationCore;
        WorkPlace workPlace = core.getWorkPlace();

        //System.out.println("[EndOfColoringEvent - EXECUTE] Objednávka ID " + order.getId() + " dokončila lakovanie. Čas: " + this.time);

        // 1. Pokus o posun objednávky na montáž
        Worker targetWorkerForMontaging = null;
        for (Worker w : workPlace.getWorkersB()) {
            if (w.getCurrentState() == WorkerBussyState.NON_BUSSY_WORKER.getValue()) {
                targetWorkerForMontaging = w;
                break;
            }
        }

        if (targetWorkerForMontaging == null || !workPlace.getQueuesThree().isEmpty()) {
            order.setState(OrderStateValues.WAITING_IN_QUEUE_3.getValue());
            workPlace.getQueuesThree().addLast(order);
            //System.out.println("[EndOfColoringEvent] Objednávka ID " + order.getId() + " pridaná do fronty montáže (queueThree).");
        } else {
            double newTime = this.time + Utility.calculateThird(order, core);
            if (newTime < core.getEndTime()) {
                order.setState(OrderStateValues.PROCESSING_MONTAGING.getValue());
                targetWorkerForMontaging.setCurrentState(WorkerBussyState.BUSSY_WORKER.getValue());
                targetWorkerForMontaging.setOrderId(order.getId());
                core.addEvent(new EndOfAssemblyEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), simulationCore, order, targetWorkerForMontaging));
                //System.out.println("[EndOfColoringEvent] Objednávka ID " + order.getId() + " ide rovno na montáž (worker ID: " + targetWorkerForMontaging.getId() + ", čas: " + newTime + ")");
            }
        }

        // 2. Tento worker pokračuje buď montážou kovania alebo ďalším lakovaním
        if (!workPlace.getQueueFour().isEmpty()) {
            Order fittingsOrder = workPlace.getQueueFour().removeFirst();
            double newTime = this.time + Utility.calculateFourth(fittingsOrder, core);
            if (newTime < core.getEndTime()) {
                fittingsOrder.setState(OrderStateValues.PROCESSING_FITTINGS.getValue());
                worker.setOrderId(fittingsOrder.getId());
                core.addEvent(new EndOfMontageEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), simulationCore, fittingsOrder, worker));
                //System.out.println("[EndOfColoringEvent] Worker ID " + worker.getId() + " ide na montáž kovania pre objednávku ID " + fittingsOrder.getId());
            }
        } else if (!workPlace.getQueuesTwo().isEmpty()) {
            Order nextColoringOrder = workPlace.getQueuesTwo().removeFirst();
            double newTime = this.time + Utility.calculateSecondTime(nextColoringOrder, core);
            if (newTime < core.getEndTime()) {
                worker.setCurrentState(WorkerBussyState.BUSSY_WORKER.getValue());
                nextColoringOrder.setState(OrderStateValues.PROCESSING_COLORING.getValue());
                worker.setOrderId(nextColoringOrder.getId());
                core.addEvent(new EndOfColoringEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), simulationCore, nextColoringOrder, worker));
                //System.out.println("[EndOfColoringEvent] Worker ID " + worker.getId() + " pokračuje ďalšou objednávkou ID " + nextColoringOrder.getId() + " na lakovanie.");
            }
        } else {
            worker.setCurrentState(WorkerBussyState.NON_BUSSY_WORKER.getValue());
            //System.out.println("[EndOfColoringEvent] Worker ID " + worker.getId() + " nemá ďalšiu prácu.");
        }
        core.dataHandling();
    }
}
