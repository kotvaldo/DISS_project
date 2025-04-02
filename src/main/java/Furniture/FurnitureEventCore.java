package Furniture;

import EventSimulation.EventSimulationCore;
import EventSimulation.SystemEvent;
import Furniture.Entity.*;
import Furniture.Events.OrderArrivalEvent;
import Furniture.Generation.Generators;
import Furniture.Enums.PresetSimulationValues;
import Furniture.Enums.PriorityValues;
import IDGenerator.IDGenerator;
import Statistics.Average;
import Statistics.TimeWeightedStatistic;
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
    private final ArrayList<WorkerA> workersA;

    private final ArrayList<WorkerC> workersC;
    private final LinkedList<Order> queueColoring;

    private final Random rand;

    private final ArrayList<WorkerB> workersB;
    private final LinkedList<Order> queueAssembly;

    private final LinkedList<Order> queueMontage;

    private final ArrayList<WorkPlace> workplaces;

    private final Average averageTimeOfWorking;
    private final Average newOrdersAfterSimulation;
    private final Generators generators;

    private int countOfFinishedOrders;

    private int burnInCount;
    private final Average utilizationA = new Average();
    private final Average utilizationB = new Average();
    private final Average utilizationC = new Average();
    private final Average utilizationTotal = new Average();

    private final ArrayList<Average> utilizationWorkersA = new ArrayList<>();
    private final ArrayList<Average> utilizationWorkersB = new ArrayList<>();
    private final ArrayList<Average> utilizationWorkersC = new ArrayList<>();

    private final LinkedList<WorkerA> freeWorkersA;
    private final LinkedList<WorkerB> freeWorkersB;
    private final LinkedList<WorkerC> freeWorkersC;

    private final TimeWeightedStatistic cuttingQL = new TimeWeightedStatistic();
    private final TimeWeightedStatistic coloringQL = new TimeWeightedStatistic();
    private final TimeWeightedStatistic assemblyQL = new TimeWeightedStatistic();
    private final TimeWeightedStatistic montageQL = new TimeWeightedStatistic();

    private final Average cuttingQLStats = new Average();
    private final Average coloringQLStats = new Average();
    private final Average assemblyQLStats = new Average();
    private final Average montageQLStats = new Average();
    private final Average countOfOrders =  new Average();;
    private final Average countOfOrdersFinished = new Average();

    private final Average averageTimeInQueueCutting = new Average();
    private final Average averageTimeInQueueColoring = new Average();
    private final Average averageTimeInQueueAssembly = new Average();
    private final Average averageTimeInQueueMontage = new Average();


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
        countOfOrders.clear();
        cuttingQLStats.clear();
        coloringQLStats.clear();
        assemblyQLStats.clear();
        montageQLStats.clear();
        countOfOrdersFinished.clear();

        utilizationWorkersA.clear();
        utilizationWorkersB.clear();
        utilizationWorkersC.clear();

        // Cyklus podľa countWorkerA/B/C
        for (int i = 0; i < countWorkerA; i++) {
            utilizationWorkersA.add(new Average());
        }
        for (int i = 0; i < countWorkerB; i++) {
            utilizationWorkersB.add(new Average());
        }
        for (int i = 0; i < countWorkerC; i++) {
            utilizationWorkersC.add(new Average());
        }

        averageTimeInQueueCutting.clear();
        averageTimeInQueueColoring.clear();
        averageTimeInQueueAssembly.clear();
        averageTimeInQueueMontage.clear();

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
        cuttingQL.clear();
        coloringQL.clear();
        assemblyQL.clear();
        montageQL.clear();

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
        if(this.simulationTime < endTime) {
            events.add(new OrderArrivalEvent(this.simulationTime, PriorityValues.BASIC_EVENT.getValue(), this));
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
        /*System.out.println(countOfOrders.mean());
        System.out.println(countOfOrdersFinished.mean());
        System.out.println(this.utilizationA.mean());
        System.out.println(this.utilizationB.mean());
        System.out.println(this.utilizationC.mean());
        for (Average a : this.utilizationWorkersA) {
            System.out.println("A" + a.mean());
        }
        for (Average a : this.utilizationWorkersB) {
            System.out.println("B" + a.mean());
        }
        for (Average a : this.utilizationWorkersC) {
            System.out.println("C" + a.mean());
        }*/

       /* System.out.println("Avg time in Queue Cutting: " + averageTimeInQueueCutting.mean()/ 3600);
        System.out.println("Avg time in Queue Coloring: " + averageTimeInQueueColoring.mean()/ 3600);
        System.out.println("Avg time in Queue Assembly: " + averageTimeInQueueAssembly.mean()/ 3600);
        System.out.println("Avg time in Queue Montage: " + averageTimeInQueueMontage.mean() / 3600);*/

    }


    @Override
    protected void afterSimulation() {
        FurnitureEventState state = (FurnitureEventState) this.state;
        if(!state.isSlowDown()) {
            double utilizationGroupA = 0.0;
            double utilizationGroupB = 0.0;
            double utilizationGroupC = 0.0;
            double utilizationAll = 0.0;

            for (int i = 0; i < workersA.size(); i++) {
                Worker w = workersA.get(i);
                double util = w.getUtilisation().getUtilisation();
                utilizationGroupA += util;
                utilizationAll += util;
                utilizationWorkersA.get(i).add(util);
            }

            for (int i = 0; i < workersB.size(); i++) {
                Worker w = workersB.get(i);
                double util = w.getUtilisation().getUtilisation();
                utilizationGroupB += util;
                utilizationAll += util;
                utilizationWorkersB.get(i).add(util);
            }

            for (int i = 0; i < workersC.size(); i++) {
                Worker w = workersC.get(i);
                double util = w.getUtilisation().getUtilisation();
                utilizationGroupC += util;
                utilizationAll += util;
                utilizationWorkersC.get(i).add(util);
            }


            countOfOrders.add(ordersArrayList.size());
            cuttingQLStats.add(cuttingQL.getMean());
            coloringQLStats.add(coloringQL.getMean());
            assemblyQLStats.add(assemblyQL.getMean());
            montageQLStats.add(montageQL.getMean());
            countOfOrdersFinished.add(countOfFinishedOrders);


            utilizationA.add(utilizationGroupA / workersA.size());
            utilizationB.add(utilizationGroupB / workersB.size());
            utilizationC.add(utilizationGroupC / workersC.size());
            utilizationTotal.add(utilizationAll / (workersA.size() + workersB.size() + workersC.size()));

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
            state.setAssemblyQLStat(assemblyQLStats);
            state.setMontageQLStat(montageQLStats);
            state.setColoringQLStat(coloringQLStats);
            state.setUtilisationWorkersA(utilizationWorkersA);
            state.setUtilisationWorkersB(utilizationWorkersB);
            state.setUtilisationWorkersC(utilizationWorkersC);
            state.setWorkersA(new ArrayList<>(this.getWorkersA()));
            state.setWorkersB(new ArrayList<>(this.getWorkersB()));
            state.setWorkersC(new ArrayList<>(this.getWorkersC()));
            state.setCuttingQLStat(cuttingQLStats);
            state.setAvgAllOrders(countOfOrders);
            state.setAvgFinishedOrders(countOfOrdersFinished);
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


        }

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
            workersA.add(new WorkerA());
            freeWorkersA.add(workersA.get(i));
        }
       // System.out.println(workersA.size());
        for (int i = 0; i < this.countWorkerC; i++) {
            workersC.add(new WorkerC());
            freeWorkersC.add(workersC.get(i));
        }

        //System.out.println(workersC.size());
        //System.out.println(countWorkerB);
        for (int i = 0; i <  this.countWorkerB; i++) {
            workersB.add(new WorkerB());
            freeWorkersB.add(workersB.get(i));
        }

    //    System.out.println(workersB.size());
    }

    public void recordQueueLengthCutting(double currentTime) {
        cuttingQL.recordChange(currentTime, queueCutting.size());
    }

    public void recordQueueLengthColoring(double currentTime) {
        coloringQL.recordChange(currentTime, queueColoring.size());
    }

    public void recordQueueLengthAssembly(double currentTime) {
        assemblyQL.recordChange(currentTime, queueAssembly.size());
    }

    public void recordQueueLengthMontage(double currentTime) {
        montageQL.recordChange(currentTime, queueMontage.size());
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

    public ArrayList<WorkerA> getWorkersA() {
        return workersA;
    }

    public ArrayList<WorkerC> getWorkersC() {
        return workersC;
    }

    public LinkedList<Order> getQueueColoring() {
        return queueColoring;
    }

    public ArrayList<WorkerB> getWorkersB() {
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

    public LinkedList<WorkerA> getFreeWorkersA() {
        return freeWorkersA;
    }

    public LinkedList<WorkerB> getFreeWorkersB() {
        return freeWorkersB;
    }

    public LinkedList<WorkerC> getFreeWorkersC() {
        return freeWorkersC;
    }

    public Random getRand() {
        return rand;
    }

    public Average getAverageTimeInQueueCutting() {
        return averageTimeInQueueCutting;
    }

    public Average getAverageTimeInQueueColoring() {
        return averageTimeInQueueColoring;
    }

    public Average getAverageTimeInQueueAssembly() {
        return averageTimeInQueueAssembly;
    }

    public Average getAverageTimeInQueueMontage() {
        return averageTimeInQueueMontage;
    }
}
