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
        WorkPlace workPlace = core.getWorkPlace();

       /* System.out.println("[EndOfFittings - EXECUTE] Objednávka ID " + order.getId() +
                " dokončila montáž kovania. Čas: " + this.time);*/

        // Objednávka je hotová
        order.setState(OrderStateValues.ORDER_DONE.getValue());
      //  System.out.println("[EndOfFittings] Objednávka ID " + order.getId() + " je ukončená.");

        // Priraď ďalšiu objednávku z fronty kovania
        if (!workPlace.getQueueFour().isEmpty()) {
            Order nextOrder = workPlace.getQueueFour().removeFirst();
            double newTime = this.time + Utility.calculateFourth(nextOrder, core);
            if (newTime < core.getEndTime()) {
                nextOrder.setState(OrderStateValues.PROCESSING_FITTINGS.getValue());
                core.addEvent(new EndOfMontageEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this.simulationCore, nextOrder, worker));
               /* System.out.println("[EndOfFittings] Worker ID " + worker.getId() +
                        " pokračuje ďalšou objednávkou ID " + nextOrder.getId() + " na montáž kovania. Čas: " + newTime);*/
            }
        } else {
            worker.setCurrentState(WorkerBussyState.NON_BUSSY_WORKER.getValue());
            //System.out.println("[EndOfFittings] Worker ID " + worker.getId() + " nemá ďalšiu prácu – je voľný.");
        }

        core.dataHandling();
    }
}
