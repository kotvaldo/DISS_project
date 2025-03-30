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

        /*System.out.println("[EndOfMontagingEvent - KONŠTRUKTOR] Vytvorený pre objednávku ID " + order.getId() +
                ", čas: " + time + ", pracovník ID: " + worker.getId());*/
    }

    @Override
    public void Execute() {
        FurnitureEventCore core = (FurnitureEventCore) simulationCore;
        /*System.out.println("[EndOfMontagingEvent - EXECUTE] Objednávka ID " + order.getId() +
                " dokončila montáž. Čas: " + this.time);
*/
        // 1. Ukončenie alebo presun do kovania
        if (order.getType() == 3) {
            Worker targetWorkerForMontage = null;
            for (Worker w : core.getWorkersC()) {
                if (w.getCurrentState() == WorkerBussyState.NON_BUSSY_WORKER.getValue()) {
                    targetWorkerForMontage = w;
                    break;
                }
            }

            if (targetWorkerForMontage == null || !core.getQueueMontage().isEmpty()) {
                order.setState(OrderStateValues.WAITING_IN_QUEUE_4.getValue());
                core.getQueueMontage().addLast(order);
              //  System.out.println("[EndOfMontagingEvent] Objednávka ID " + order.getId() + " pridaná do fronty kovania (queueFour).");
            } else {
                double newTime = this.time + Utility.calculateFourth(order, core, targetWorkerForMontage);
                if (newTime < core.getEndTime()) {
                    order.setState(OrderStateValues.PROCESSING_FITTINGS.getValue());
                    targetWorkerForMontage.setCurrentState(WorkerBussyState.BUSSY_WORKER.getValue());
                    targetWorkerForMontage.setOrder(order);
                    core.addEvent(new EndOfMontageEvent(newTime, PriorityValues.IMPORTANT_EVENT.getValue(), simulationCore, order, targetWorkerForMontage));
              /*      System.out.println("[EndOfMontagingEvent] Objednávka ID " + order.getId() +
                            " ide rovno na montáž kovania (worker ID: " + targetWorkerForMontage.getId() + ", čas: " + newTime + ")");
              */  }
            }
        } else {
            order.setState(OrderStateValues.ORDER_DONE.getValue());
            order.getWorkPlace().setOrder(null);
            //neviem ci toto moze byt
            order.setWorkPlace(null);
            worker.setCurrentState(WorkerBussyState.NON_BUSSY_WORKER.getValue());
            worker.setOrder(null);
            order.setEndTime(core.getSimulationTime());
            core.getAverageTimeOfWorking().add(order.getTimeOfWork());


            //core.ordersArrayList.remove(order);
            //System.out.println("[EndOfMontagingEvent] Objednávka ID " + order.getId() + " je dokončená.");
        }


        // 2. Priradenie ďalšej objednávky tomuto workerovi
        if (!core.getQueueAssembly().isEmpty()) {
            Order nextOrder = core.getQueueAssembly().removeFirst();
            double newTime = this.time + Utility.calculateThird(nextOrder, core, worker);
            if (newTime < core.getEndTime()) {
                nextOrder.setState(OrderStateValues.PROCESSING_MONTAGING.getValue());
                worker.setCurrentState(WorkerBussyState.BUSSY_WORKER.getValue());
                worker.setOrder(nextOrder);
                core.addEvent(new EndOfAssemblyEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), simulationCore, nextOrder, worker));
               /* System.out.println("[EndOfMontagingEvent] Worker ID " + worker.getId() +
                        " pokračuje ďalšou objednávkou ID " + nextOrder.getId() + " na montáž (čas: " + newTime + ")");*/
            }
        } else {
            worker.setCurrentState(WorkerBussyState.NON_BUSSY_WORKER.getValue());
            worker.setOrder(null);
            //System.out.println("[EndOfMontagingEvent] Worker ID " + worker.getId() + " nemá ďalšiu prácu – je voľný.");
        }

    }
}
