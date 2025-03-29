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
        //System.out.println("[EndOfColoringEvent - EXECUTE] Objednávka ID " + order.getId() + " dokončila lakovanie. Čas: " + this.time);

        // 1. Pokus o posun objednávky na montáž
        Worker targetWorkerForMontaging = null;
        for (Worker w : core.getWorkersB()) {
            if (w.getCurrentState() == WorkerBussyState.NON_BUSSY_WORKER.getValue()) {
                targetWorkerForMontaging = w;
                break;
            }
        }

        if (targetWorkerForMontaging == null || !core.getQueueAssembly().isEmpty()) {
            order.setState(OrderStateValues.WAITING_IN_QUEUE_3.getValue());
            core.getQueueAssembly().addLast(order);
            //System.out.println("[EndOfColoringEvent] Objednávka ID " + order.getId() + " pridaná do fronty montáže (queueThree).");
        } else {
            double newTime = this.time + Utility.calculateThird(order, core);
            if (newTime < core.getEndTime()) {
                order.setState(OrderStateValues.PROCESSING_MONTAGING.getValue());
                targetWorkerForMontaging.setCurrentState(WorkerBussyState.BUSSY_WORKER.getValue());
                targetWorkerForMontaging.setOrder(order);
                core.addEvent(new EndOfAssemblyEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), simulationCore, order, targetWorkerForMontaging));
                //System.out.println("[EndOfColoringEvent] Objednávka ID " + order.getId() + " ide rovno na montáž (worker ID: " + targetWorkerForMontaging.getId() + ", čas: " + newTime + ")");
            }
        }

        // 2. Tento worker pokračuje buď montážou kovania alebo ďalším lakovaním
        if (!core.getQueueMontage().isEmpty()) {
            Order fittingsOrder = core.getQueueMontage().removeFirst();
            double newTime = this.time + Utility.calculateFourth(fittingsOrder, core);
            if (newTime < core.getEndTime()) {
                fittingsOrder.setState(OrderStateValues.PROCESSING_FITTINGS.getValue());
                worker.setOrder(fittingsOrder);
                core.addEvent(new EndOfMontageEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), simulationCore, fittingsOrder, worker));
                //System.out.println("[EndOfColoringEvent] Worker ID " + worker.getId() + " ide na montáž kovania pre objednávku ID " + fittingsOrder.getId());
            }
        } else if (!core.getQueueColoring().isEmpty()) {
            Order nextColoringOrder = core.getQueueColoring().removeFirst();
            double newTime = this.time + Utility.calculateSecondTime(nextColoringOrder, core);
            if (newTime < core.getEndTime()) {
                worker.setCurrentState(WorkerBussyState.BUSSY_WORKER.getValue());
                nextColoringOrder.setState(OrderStateValues.PROCESSING_COLORING.getValue());
                worker.setOrder(nextColoringOrder);
                core.addEvent(new EndOfColoringEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), simulationCore, nextColoringOrder, worker));
                //System.out.println("[EndOfColoringEvent] Worker ID " + worker.getId() + " pokračuje ďalšou objednávkou ID " + nextColoringOrder.getId() + " na lakovanie.");
            }
        } else {
            worker.setCurrentState(WorkerBussyState.NON_BUSSY_WORKER.getValue());
            //System.out.println("[EndOfColoringEvent] Worker ID " + worker.getId() + " nemá ďalšiu prácu.");
        }
    }
}
