package Furniture;

import EventSimulation.EventSimulationCore;
import EventSimulation.SystemEvent;
import Furniture.Entity.Order;
import Furniture.Entity.Worker;
import Furniture.Events.OrderArrivalEvent;
import Furniture.Generation.Generators;
import Furniture.Entity.WorkPlace;
import Furniture.Enums.PresetSimulationValues;
import Furniture.Enums.PriorityValues;
import IDGenerator.IDGenerator;
import Statistics.Average;
import Statistics.Utilisation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class FurnitureEventCore extends EventSimulationCore {

    private int countWorkerA;
    private int countWorkerB;
    private int countWorkerC;
    public ArrayList<Order> ordersArrayList = new ArrayList<>();
    private final LinkedList<Order> queueCutting;
    private final ArrayList<Worker> workersA;

    private final ArrayList<Worker> workersC;
    private final LinkedList<Order> queueColoring;

    private final Random rand;

    private final ArrayList<Worker> workersB;
    private final LinkedList<Order> queueAssembly;

    private final LinkedList<Order> queueMontage;

    private final ArrayList<WorkPlace> workplaces;

    private final Average averageTimeOfWorking;
    private final Average newOrdersAfterSimulation;
    private final Generators generators;

    private int countOfFinishedOrders;

    private int burnInCount;
    private final Utilisation utilizationA = new Utilisation();
    private final Utilisation utilizationB = new Utilisation();
    private final Utilisation utilizationC = new Utilisation();
    private final Utilisation utilizationTotal = new Utilisation();

    private final LinkedList<Worker> freeWorkersA;
    private final LinkedList<Worker> freeWorkersB;
    private final LinkedList<Worker> freeWorkersC;

    public FurnitureEventCore() {
        super();
        state = new FurnitureEventState();
        workplaces = new ArrayList<>();
        workersA = new ArrayList<>();
        workersB = new ArrayList<>();
        workersC = new ArrayList<>();

        freeWorkersA = new LinkedList<>();
        freeWorkersB = new LinkedList<>();
        freeWorkersC = new LinkedList<>();

        rand = new Random();
        queueColoring = new LinkedList<>();
        queueAssembly = new LinkedList<>();
        queueMontage = new LinkedList<>();
        queueCutting = new LinkedList<>();
        generators = new Generators();
        //statistiky
        averageTimeOfWorking = new Average();
        newOrdersAfterSimulation = new Average();

        countOfFinishedOrders = 0;
    }

    @Override
    protected void beforeAllReplications() {
        this.state = new FurnitureEventState();
        this.simulationTime = PresetSimulationValues.START_SIMULATION_TIME.getValue();
        this.endTime = PresetSimulationValues.END_OF_SIMULATION.getValue();
        events.clear();
        queueCutting.clear();
        queueAssembly.clear();
        queueMontage.clear();
        queueColoring.clear();

       // initWorkers();
        this.ordersArrayList.clear();
        this.averageTimeOfWorking.clear();
        this.newOrdersAfterSimulation.clear();
        this.workplaces.clear();
        this.state = new FurnitureEventState();
        this.actualRepCount = 0;
        utilizationA.clear();
        utilizationB.clear();
        utilizationC.clear();
        utilizationTotal.clear();
    }
    @Override
    protected void beforeSimulation() {

        this.simulationTime = PresetSimulationValues.START_SIMULATION_TIME.getValue();
        this.endTime = PresetSimulationValues.END_OF_SIMULATION.getValue();
        countOfFinishedOrders = 0;
        this.ordersArrayList.clear();
        this.queueCutting.clear();
        this.queueAssembly.clear();
        this.queueMontage.clear();

        this.queueColoring.clear();
        this.workplaces.clear();
        this.events.clear();
        IDGenerator.getInstance().clearGenerators();
        if(isSlowMode) {
            this.state = new FurnitureEventState();
        }

        initWorkers();

        FurnitureEventState currentState = (FurnitureEventState) state;
        currentState.setSlowDown(this.isSlowMode);
        double newTime = generators.getOrderArrivalDist().sample() + this.simulationTime;
        if(newTime < endTime) {
            events.add(new OrderArrivalEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this));
        }

        if (isSlowMode) {
            double timeFor = slowDownSpeed / frequencyOfUpdate;
            timeFor += this.simulationTime;
            if(timeFor < endTime) {
                events.add(new SystemEvent(timeFor, PriorityValues.SYSTEM_EVENT.getValue(), this));
            }
            this.isGeneratedFirstSystemEvent = true;
        }
    }


    @Override
    protected void afterRunSimulation() {

    }


    @Override
    protected void afterSimulation() {
        FurnitureEventState state = (FurnitureEventState) this.state;
        if(!state.isSlowDown()) {
            double utilizationGroupA = 0;
            double utilizationGroupB = 0;
            double utilizationGroupC = 0;
            double utilizationAll = 0;
            int totalWorkers = workersA.size() + workersB.size() + workersC.size();

            for (Worker w : workersA) {
                utilizationGroupA += w.getTotalBusyTime() / this.endTime;
                utilizationAll += w.getTotalBusyTime() / this.endTime;
            }
            for (Worker w : workersB) {
                utilizationGroupB += w.getTotalBusyTime() / this.endTime;
                utilizationAll += w.getTotalBusyTime() / this.endTime;
            }
            for (Worker w : workersC) {
                utilizationGroupC += w.getTotalBusyTime() / this.endTime;
                utilizationAll += w.getTotalBusyTime() / this.endTime;
            }


            utilizationA.add(utilizationGroupA / workersA.size());
            utilizationB.add(utilizationGroupB / workersB.size());
            utilizationC.add(utilizationGroupC / workersC.size());
            utilizationTotal.add(utilizationAll / totalWorkers);
            newOrdersAfterSimulation.add(queueCutting.size());


            //  System.out.println(queueCutting.size());
            state.setRepCount(this.actualRepCount);
            state.setAverageTimeOfWorking(averageTimeOfWorking);
            state.setNewOrderOnEnd(newOrdersAfterSimulation);
            state.setBurnRepCount(this.burnInCount);
            state.setUtilisationA(utilizationA);
            state.setUtilisationB(utilizationB);
            state.setUtilisationC(utilizationC);
            state.setUtilisationAll(utilizationTotal);
        }
        this.listener.setState(state);
        this.listener.notifyObservers();
    }

    @Override
    public void dataHandling() {
        //System.out.println("It was updated )");
        FurnitureEventState state = (FurnitureEventState) this.state;
        //System.out.println(state.getSimulationTime());
        if(isSlowMode) {
            state.setSimulationTime(this.simulationTime);
            state.setAllOrders(new ArrayList<>(ordersArrayList));
            state.setWorkersA(new ArrayList<>(this.getWorkersA()));
            state.setWorkersB(new ArrayList<>(this.getWorkersB()));
            state.setWorkersC(new ArrayList<>(this.getWorkersC()));
            int newDay = (int) Math.round(simulationTime / (8.0 * 3600.0));
            if (newDay > state.getCurrentDay()) {
                state.setCurrentDay(newDay);
            }
            state.setCountOfAllOrders(ordersArrayList.size());
            state.setCountOfFinishedOrders(countOfFinishedOrders);
            state.setWorkPlaces(new ArrayList<>(workplaces));
            state.setQueueAssembly(queueAssembly.size());
            state.setQueueColoring(queueColoring.size());
            state.setQueueCutting(queueCutting.size());
            state.setQueueMontage(queueMontage.size());
            /*
            if (simulationTime > 0) {
                double utilizationGroupA = 0;
                double utilizationGroupB = 0;
                double utilizationGroupC = 0;
                double utilizationAll = 0;
                int totalWorkers = workersA.size() + workersB.size() + workersC.size();

                for (Worker w : workersA) utilizationGroupA += w.getTotalBusyTime() / simulationTime;
                for (Worker w : workersB) utilizationGroupB += w.getTotalBusyTime() / simulationTime;
                for (Worker w : workersC) utilizationGroupC += w.getTotalBusyTime() / simulationTime;

                utilizationAll = (utilizationGroupA + utilizationGroupB + utilizationGroupC) / totalWorkers;

                System.out.println("=== Priebežná utilizácia ===");
                System.out.printf("Group A: %.2f %%\n", utilizationGroupA / workersA.size() * 100);
                System.out.printf("Group B: %.2f %%\n", utilizationGroupB / workersB.size() * 100);
                System.out.printf("Group C: %.2f %%\n", utilizationGroupC / workersC.size() * 100);
                System.out.printf("Total  : %.2f %%\n", utilizationAll * 100);
                System.out.println("============================");
            } */


        }
        //state.setAverageTimeOfWorking(averageTimeOfWorking);

        this.listener.setState(state);
        this.listener.notifyObservers();

    }

    private void initWorkers() {
        workersA.clear();
        workersC.clear();
        workersB.clear();

        freeWorkersA.clear();
        freeWorkersB.clear();
        freeWorkersC.clear();

        for (int i = 0; i < this.countWorkerA; i++) {
            workersA.add(new Worker("A"));
            freeWorkersA.add(workersA.get(i));
        }
        System.out.println(workersA.size());
        for (int i = 0; i < this.countWorkerC; i++) {
            workersC.add(new Worker("C"));
            freeWorkersC.add(workersC.get(i));
        }

        System.out.println(workersC.size());
        System.out.println(countWorkerB);
        for (int i = 0; i <  this.countWorkerB; i++) {
            workersB.add(new Worker("B"));
            freeWorkersB.add(workersB.get(i));
        }

        System.out.println(workersB.size());
    }


    public void setCountOfFinishedOrders(int countOfFinishedOrders) {
        this.countOfFinishedOrders = countOfFinishedOrders;
    }

    public int getCountOfFinishedOrders() {
        return this.countOfFinishedOrders;
    }

    public void setReplicationCount(int replicationCount) {
        this.repCount = replicationCount;
    }



    public LinkedList<Order> getQueueCutting() {
        return queueCutting;
    }

    public ArrayList<Worker> getWorkersA() {
        return workersA;
    }

    public ArrayList<Worker> getWorkersC() {
        return workersC;
    }

    public LinkedList<Order> getQueueColoring() {
        return queueColoring;
    }

    public ArrayList<Worker> getWorkersB() {
        return workersB;
    }

    public LinkedList<Order> getQueueAssembly() {
        return queueAssembly;
    }

    public LinkedList<Order> getQueueMontage() {
        return queueMontage;
    }

    public ArrayList<WorkPlace> getWorkplaces() {
        return workplaces;
    }

    public void setCountWorkerA(int countWorkerA) {
        this.countWorkerA = countWorkerA;
    }

    public void setCountWorkerB(int countWorkerB) {
        this.countWorkerB = countWorkerB;
    }

    public void setCountWorkerC(int countWorkerC) {
        this.countWorkerC = countWorkerC;
    }

    public Average getAverageTimeOfWorking() {
        return averageTimeOfWorking;
    }

    public Generators getGenerators() {
        return generators;
    }

    public int getBurnInCount() {
        return burnInCount;
    }

    public void setBurnInCount(int burnInCount) {
        this.burnInCount = burnInCount;
    }

    public LinkedList<Worker> getFreeWorkersA() {
        return freeWorkersA;
    }

    public LinkedList<Worker> getFreeWorkersB() {
        return freeWorkersB;
    }

    public LinkedList<Worker> getFreeWorkersC() {
        return freeWorkersC;
    }

    public Random getRand() {
        return rand;
    }
}
