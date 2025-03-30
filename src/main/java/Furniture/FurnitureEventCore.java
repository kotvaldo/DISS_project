package Furniture;

import EventSimulation.EventSimulationCore;
import EventSimulation.SystemEvent;
import Furniture.Entity.Order;
import Furniture.Entity.Worker;
import Furniture.Events.OrderArrivalEvent;
import Generators.*;
import Furniture.Entity.WorkPlace;
import Furniture.Enums.PresetSimulationValues;
import Furniture.Enums.PriorityValues;
import Statistics.Average;

import java.util.ArrayList;
import java.util.LinkedList;

public class FurnitureEventCore extends EventSimulationCore {
    private final Exponential orderArrivalDist;
    private final EmpiricDiscrete typeOfOrderDist;
    private final EmpiricContinuous cuttingTypeOneDist;
    private final UniformContinuous cuttingTypeTwoDist;
    private final UniformContinuous cuttingTypeThreeDist;
    private final UniformContinuous coloringTypeOneDist;
    private final UniformContinuous coloringTypeTwoDist;
    private final UniformContinuous coloringTypeThreeDist;
    private final UniformContinuous assemblyTypeOneDist;
    private final UniformContinuous assemblyTypeTwoDist;
    private final UniformContinuous assemblyTypeThreeDist;
    private final UniformContinuous montageDist;
    private final Triangular timeInStorageDist;
    private final Triangular timeMovingToStorageDist;
    private final Triangular timeMovingToAnotherWorkshopDist;
    private int countWorkerA;
    private int countWorkerB;
    private int countWorkerC;
    public ArrayList<Order> ordersArrayList = new ArrayList<>();
    private final LinkedList<Order> queueCutting;
    private final ArrayList<Worker> workersA;

    private final ArrayList<Worker> workersC;
    private final LinkedList<Order> queueColoring;

    private final ArrayList<Worker> workersB;
    private final LinkedList<Order> queueAssembly;

    private final LinkedList<Order> queueMontage;

    private final ArrayList<WorkPlace> workplaces;

    private final Average averageTimeOfWorking;
    private final Average newOrdersAfterSimulation;



    public FurnitureEventCore() {
        super();
        state = new FurnitureEventState();
        orderArrivalDist = new Exponential(1800.0);
        ArrayList<EmpiricData<Integer>> typeList = new ArrayList<>();
        typeList.add(new EmpiricData<>(1, 2, 0.5));
        typeList.add(new EmpiricData<>(2, 3, 0.2));
        typeList.add(new EmpiricData<>(3, 4, 0.3));
        typeOfOrderDist = new EmpiricDiscrete(typeList);
        ArrayList<EmpiricData<Double>> list = new ArrayList<>();
        list.add(new EmpiricData<>(10.0, 25.0, 0.6));
        list.add(new EmpiricData<>(25.0, 50.0, 0.4));
        //first
        cuttingTypeOneDist = new EmpiricContinuous(list);
        coloringTypeOneDist = new UniformContinuous(12000.0, 36600.0);
        assemblyTypeOneDist = new UniformContinuous(1800.0, 3600.0);

        // second
        cuttingTypeTwoDist = new UniformContinuous(720.0, 960.0);
        coloringTypeTwoDist = new UniformContinuous(12600.0, 32400.0);
        assemblyTypeTwoDist = new UniformContinuous(840.0, 1440.0);

        workplaces = new ArrayList<>();

        // third
        cuttingTypeThreeDist = new UniformContinuous(900.0, 4800.0);
        coloringTypeThreeDist = new UniformContinuous(36000.0, 42000.0);
        assemblyTypeThreeDist = new UniformContinuous(2100.0, 4500.0);
        montageDist = new UniformContinuous(900.0, 1500.0);

        //moving Dist
        timeMovingToStorageDist = new Triangular(60.0, 480.0, 120.0);
        timeInStorageDist = new Triangular(300.0, 900.0, 500.0);
        timeMovingToAnotherWorkshopDist = new Triangular(120.0, 500.0, 150.0);


        workersA = new ArrayList<>();
        workersB = new ArrayList<>();
        workersC = new ArrayList<>();
        queueColoring = new LinkedList<>();
        queueAssembly = new LinkedList<>();
        queueMontage = new LinkedList<>();
        queueCutting = new LinkedList<>();

        //statistiky
        averageTimeOfWorking = new Average();
        newOrdersAfterSimulation = new Average();
    }

    @Override
    protected void beforeAllReplications() {
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

    }
    @Override
    protected void beforeSimulation() {
        this.state = new FurnitureEventState();
        this.simulationTime = PresetSimulationValues.START_SIMULATION_TIME.getValue();
        this.endTime = PresetSimulationValues.END_OF_SIMULATION.getValue();

        this.ordersArrayList.clear();
        this.queueCutting.clear();
        this.queueAssembly.clear();
        this.queueMontage.clear();
        this.queueColoring.clear();
        this.workplaces.clear();
        this.events.clear();

        initWorkers();

        FurnitureEventState currentState = (FurnitureEventState) state;
        currentState.setSlowDown(this.isSlowMode);

        double newTime = orderArrivalDist.sample();
        events.add(new OrderArrivalEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this));

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
        newOrdersAfterSimulation.add(queueCutting.size());
        System.out.println(queueCutting.size());
        state.setRepCount(this.actualRepCount);
        state.setAverageTimeOfWorking(averageTimeOfWorking);
        state.setNewOrderOnEnd(newOrdersAfterSimulation);
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
            int newDay = (int)(simulationTime / (8.0 * 3600.0));
            if (newDay > state.getCurrentDay()) {
                state.setCurrentDay(newDay);
            }

            state.setWorkPlaces(new ArrayList<>(workplaces));
            state.setQueueAssembly(queueAssembly.size());
            state.setQueueColoring(queueColoring.size());
            state.setQueueCutting(queueCutting.size());
            state.setQueueMontage(queueMontage.size());

        }
        //state.setAverageTimeOfWorking(averageTimeOfWorking);

        this.listener.setState(state);
        this.listener.notifyObservers();

    }

    private void initWorkers() {
        workersA.clear();
        workersC.clear();
        workersB.clear();
        for (int i = 0; i < this.countWorkerA; i++) {
            workersA.add(new Worker("A"));
        }
        for (int i = 0; i < this.countWorkerC; i++) {
            workersC.add(new Worker("C"));
        }
        for (int i = 0; i <  this.countWorkerB; i++) {
            workersB.add(new Worker("B"));
        }

    }


    public Exponential getOrderArrivalDist() {
        return orderArrivalDist;
    }

    public EmpiricDiscrete getTypeOfOrderDist() {
        return typeOfOrderDist;
    }


    public EmpiricContinuous getCuttingTypeOneDist() {
        return cuttingTypeOneDist;
    }


    public UniformContinuous getCuttingTypeTwoDist() {
        return cuttingTypeTwoDist;
    }

    public void setReplicationCount(int replicationCount) {
        this.repCount = replicationCount;
    }

    public UniformContinuous getCuttingTypeThreeDist() {
        return cuttingTypeThreeDist;
    }


    public UniformContinuous getColoringTypeOneDist() {
        return coloringTypeOneDist;
    }


    public UniformContinuous getColoringTypeTwoDist() {
        return coloringTypeTwoDist;
    }


    public UniformContinuous getColoringTypeThreeDist() {
        return coloringTypeThreeDist;
    }


    public UniformContinuous getAssemblyTypeOneDist() {
        return assemblyTypeOneDist;
    }

    public UniformContinuous getAssemblyTypeTwoDist() {
        return assemblyTypeTwoDist;
    }


    public UniformContinuous getAssemblyTypeThreeDist() {
        return assemblyTypeThreeDist;
    }


    public UniformContinuous getMontageDist() {
        return montageDist;
    }


    public Triangular getTimeInStorageDist() {
        return timeInStorageDist;
    }

    public Triangular getTimeMovingToStorageDist() {
        return timeMovingToStorageDist;
    }

    public Triangular getTimeMovingToAnotherWorkshopDist() {
        return timeMovingToAnotherWorkshopDist;
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
}
