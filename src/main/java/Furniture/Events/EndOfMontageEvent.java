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
    private Order order;
    private Worker worker;

    protected EndOfMontageEvent(double time, int priority, SimulationCore simulationCore, Order order, Worker worker) {
        super(time, priority, simulationCore);
        this.order = order;
        this.worker = worker;

       /* System.out.println("[EndOfFittings - KONŠTRUKTOR] Vytvorený pre objednávku ID " + order.getId() +
                ", čas: " + time + ", pracovník ID: " + worker.getId());*/
    }

    @Override
    public void Execute() {
        FurnitureEventCore core = (FurnitureEventCore) simulationCore;

       /* System.out.println("[EndOfFittings - EXECUTE] Objednávka ID " + order.getId() +
                " dokončila montáž kovania. Čas: " + this.time);*/

        // Objednávka je hotová
        order.setState(OrderStateValues.ORDER_DONE.getValue());
        order.getWorkPlace().setOrder(null);
        order.setWorkPlace(null);
        //core.ordersArrayList.remove(order);
      //  System.out.println("[EndOfFittings] Objednávka ID " + order.getId() + " je ukončená.");

        // Priraď ďalšiu objednávku z fronty kovania
        if (!core.getQueueMontage().isEmpty()) {
            Order nextOrder = core.getQueueMontage().removeFirst();
            double newTime = this.time + Utility.calculateFourth(nextOrder, core, worker);
            if (newTime < core.getEndTime()) {
                nextOrder.setState(OrderStateValues.PROCESSING_FITTINGS.getValue());
                worker.setCurrentState(WorkerBussyState.BUSSY_WORKER.getValue());
                worker.setOrder(nextOrder);
                core.addEvent(new EndOfMontageEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this.simulationCore, nextOrder, worker));
               /* System.out.println("[EndOfFittings] Worker ID " + worker.getId() +
                        " pokračuje ďalšou objednávkou ID " + nextOrder.getId() + " na montáž kovania. Čas: " + newTime);*/
            }
        } else {
            worker.setCurrentState(WorkerBussyState.NON_BUSSY_WORKER.getValue());
            worker.setOrder(null);
            //System.out.println("[EndOfFittings] Worker ID " + worker.getId() + " nemá ďalšiu prácu – je voľný.");
        }


    }
}
