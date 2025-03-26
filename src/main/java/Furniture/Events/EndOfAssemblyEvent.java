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

public class EndOfAssemblyEvent extends Event {
    private Worker worker;
    private Order order;

    public EndOfAssemblyEvent(double time, int priority, SimulationCore simulationCore, Order order, Worker worker) {
        super(time, priority, simulationCore);
        this.order = order;
        this.worker = worker;

        System.out.println("[EndOfMontagingEvent - KONŠTRUKTOR] Vytvorený pre objednávku ID " + order.getId() +
                ", čas: " + time + ", pracovník ID: " + worker.getId());
    }

    @Override
    public void Execute() {
        FurnitureEventCore core = (FurnitureEventCore) simulationCore;
        WorkPlace workPlace = core.getWorkPlace();

        System.out.println("[EndOfMontagingEvent - EXECUTE] Objednávka ID " + order.getId() +
                " dokončila montáž. Čas: " + this.time);

        // 1. Ukončenie alebo presun do kovania
        if (order.getType() == 3) {
            Worker targetWorkerForMontage = null;
            for (Worker w : workPlace.getWorkersTwo()) {
                if (w.getCurrentState() == WorkerBussyState.NON_BUSSY_WORKER.getValue()) {
                    targetWorkerForMontage = w;
                    break;
                }
            }

            if (targetWorkerForMontage == null || !workPlace.getQueueFour().isEmpty()) {
                order.setState(OrderStateValues.WAITING_IN_QUEUE_4.getValue());
                workPlace.getQueueFour().addLast(order);
                System.out.println("[EndOfMontagingEvent] Objednávka ID " + order.getId() + " pridaná do fronty kovania (queueFour).");
            } else {
                double newTime = Utility.calculateFourth(order, core);
                if (newTime < core.getEndTime()) {
                    order.setState(OrderStateValues.PROCESSING_FITTINGS.getValue());
                    targetWorkerForMontage.setCurrentState(WorkerBussyState.BUSSY_WORKER.getValue());
                    core.addEvent(new EndOfMontageEvent(newTime, PriorityValues.IMPORTANT_EVENT.getValue(), simulationCore, order, targetWorkerForMontage));
                    System.out.println("[EndOfMontagingEvent] Objednávka ID " + order.getId() +
                            " ide rovno na montáž kovania (worker ID: " + targetWorkerForMontage.getId() + ", čas: " + newTime + ")");
                }
            }
        } else {
            order.setState(OrderStateValues.ORDER_DONE.getValue());
            System.out.println("[EndOfMontagingEvent] Objednávka ID " + order.getId() + " je dokončená.");
        }


        // 2. Priradenie ďalšej objednávky tomuto workerovi
        if (!workPlace.getQueuesThree().isEmpty()) {
            Order nextOrder = workPlace.getQueuesThree().removeFirst();
            double newTime = this.time + Utility.calculateThird(nextOrder, core);
            if (newTime < core.getEndTime()) {
                nextOrder.setState(OrderStateValues.PROCESSING_MONTAGING.getValue());
                core.addEvent(new EndOfAssemblyEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), simulationCore, nextOrder, worker));
                System.out.println("[EndOfMontagingEvent] Worker ID " + worker.getId() +
                        " pokračuje ďalšou objednávkou ID " + nextOrder.getId() + " na montáž (čas: " + newTime + ")");
            }
        } else {
            worker.setCurrentState(WorkerBussyState.NON_BUSSY_WORKER.getValue());
            System.out.println("[EndOfMontagingEvent] Worker ID " + worker.getId() + " nemá ďalšiu prácu – je voľný.");
        }

        core.dataHandling();
    }
}
