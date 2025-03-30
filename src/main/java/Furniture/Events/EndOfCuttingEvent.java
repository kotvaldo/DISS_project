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
       /* System.out.println("[EndOfCuttingEvent - KONŠTRUKTOR] Vytvorený pre objednávku ID " + order.getId() +
                ", čas: " + time + ", pracovník ID: " + worker.getId());*/
    }


    @Override
    public void Execute() {
        FurnitureEventCore core = (FurnitureEventCore) simulationCore;
        
       /* System.out.println("[EndOfCuttingEvent - EXECUTE] Objednávka ID " + order.getId() +
                " dokončila rezanie. Čas: " + this.time);
*/
        // Presun do fázy 2 (lakovanie)


        Worker targetWorkerForTwo = null;
        for (Worker w : core.getWorkersC()) {
            if (w.getCurrentState() == WorkerBussyState.NON_BUSY_WORKER.getValue()) {
                targetWorkerForTwo = w;
                break;
            }
        }




        if (targetWorkerForTwo == null) {
            //          System.out.println("No WorkerForTwo found");
            core.getQueueColoring().addLast(this.order);
            this.order.setState(OrderStateValues.WAITING_IN_QUEUE_2.getValue());
            //        System.out.println("[EndOfCuttingEvent] Objednávka ID " + order.getId() + " pridaná do fronty lakovania (queueTwo).");
        } else {
            if (core.getQueueColoring().isEmpty()) {
                double newTime = this.time + Utility.calculateSecondTime(this.order, core, targetWorkerForTwo);
                if (newTime < core.getEndTime()) {
                    this.order.setState(OrderStateValues.PROCESSING_COLORING.getValue());
                    targetWorkerForTwo.setCurrentState(WorkerBussyState.BUSY_WORKER.getValue());
                    targetWorkerForTwo.setOrder(order);
                    //System.out.println(targetWorkerForTwo.getCurrentState());
                    core.addEvent(new EndOfColoringEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this.simulationCore, this.order, targetWorkerForTwo));
      /*              System.out.println("[EndOfCuttingEvent] Objednávka ID " + order.getId() +
                            " ide rovno na lakovanie (worker ID: " + targetWorkerForTwo.getId() + ", čas: " + newTime + ")");*/
                }
            } else {
                this.order.setState(OrderStateValues.WAITING_IN_QUEUE_2.getValue());
                core.getQueueColoring().addLast(this.order);
               // System.out.println("[EndOfCuttingEvent] Objednávka ID " + order.getId() + " pridaná do fronty lakovania (queueTwo) – fronta nie je prázdna.");
            }
        }

        // Pokus o ďalšiu objednávku na rezanie

        if (core.getQueueCutting().isEmpty()) {
            worker.setCurrentState(WorkerBussyState.NON_BUSY_WORKER.getValue());
            worker.setOrder(null);
           // System.out.println("[EndOfCuttingEvent] Žiadna ďalšia objednávka na rezanie – worker ID " + worker.getId() + " je voľný.");
        } else {
            Order nextOrder = core.getQueueCutting().removeFirst();
            double newTime = this.time + Utility.calculateFirstTime(nextOrder, core, worker);
            if (newTime < core.getEndTime()) {
                nextOrder.setState(OrderStateValues.PROCESSING_CUTTING.getValue());
                worker.setCurrentState(WorkerBussyState.BUSY_WORKER.getValue());
                worker.setOrder(nextOrder);
                //System.out.println(worker.getCurrentState());
                core.addEvent(new EndOfCuttingEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this.simulationCore, nextOrder, worker));
           //     System.out.println("[EndOfCuttingEvent] Ďalšia objednávka ID " + nextOrder.getId() + " ide na rezanie (čas: " + newTime + ")");
            }
        }

    }


}
