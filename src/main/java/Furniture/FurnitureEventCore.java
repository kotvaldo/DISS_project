package Furniture;

import EventSimulation.EventSimulationCore;
import EventSimulation.SystemEvent;
import Furniture.Entity.Order;
import Furniture.Entity.Worker;
import Furniture.Enums.OrderStateValues;
import Furniture.Events.OrderArrivalEvent;
import Generators.*;
import Furniture.Entity.WorkPlace;
import Furniture.Enums.PresetSimulationValues;
import Furniture.Enums.PriorityValues;

import java.util.ArrayList;

public class FurnitureEventCore extends EventSimulationCore {
    private final Exponential orderArrivalDist;
    private final EmpiricDiscrete typeOfOrderDist;
    private final EmpiricContinuous cuttingTypeOneDist;
    private final UniformContinuous cuttingTypeTwoDist;
    private final UniformContinuous cuttingTypeThreeDist;
    private final UniformContinuous coloringTypeOneDist;
    private final UniformContinuous coloringTypeTwoDist;
    private final UniformContinuous coloringTypeThreeDist;
    private final UniformContinuous computingTypeOneDist;
    private final UniformContinuous computingTypeTwoDist;
    private final UniformContinuous computingTypeThreeDist;
    private final UniformContinuous fittingTypeThreeDist;
    private final Triangular timeInStorageDist;
    private final Triangular timeMovingToStorageDist;
    private final Triangular timeMovingToAnotherWorkshopDist;
    private int countWorkerA = 5;
    private int countWorkerB = 6;
    private int countWorkerC = 3;
    private WorkPlace workPlace;
    public ArrayList<Order> ordersArrayList = new ArrayList<>();

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
        computingTypeOneDist = new UniformContinuous(1800.0, 3600.0);

        // second
        cuttingTypeTwoDist = new UniformContinuous(720.0, 960.0);
        coloringTypeTwoDist = new UniformContinuous(12600.0, 32400.0);
        computingTypeTwoDist = new UniformContinuous(840.0, 1440.0);

        // third
        cuttingTypeThreeDist = new UniformContinuous(900.0, 4800.0);
        coloringTypeThreeDist = new UniformContinuous(36000.0, 42000.0);
        computingTypeThreeDist = new UniformContinuous(2100.0, 4500.0);
        fittingTypeThreeDist = new UniformContinuous(900.0, 1500.0);

        //moving Dist
        timeMovingToStorageDist = new Triangular(60.0, 480.0, 120.0);
        timeInStorageDist = new Triangular(300.0, 900.0, 500.0);
        timeMovingToAnotherWorkshopDist = new Triangular(120.0, 500.0, 150.0);

        workPlace = new WorkPlace();

    }

    @Override
    protected void beforeRunSimulation() {
        this.simulationTime = PresetSimulationValues.START_SIMULATION_TIME.getValue();
        this.endTime = PresetSimulationValues.END_OF_SIMULATION.getValue();
        events.clear();
        workPlace.initWorkers(countWorkerA, countWorkerB, countWorkerC);
        workPlace.clearOrderQueues();
        this.state = new FurnitureEventState();

    }
    @Override
    protected void beforeSimulation() {
        workPlace.clearOrderQueues();
        this.workPlace.initWorkers(countWorkerA, countWorkerB, countWorkerC);
        isSlowMode = true;
        double newTime = orderArrivalDist.sample();
        events.add(new OrderArrivalEvent(newTime, PriorityValues.BASIC_EVENT.getValue(), this));

        events.add(new SystemEvent(PresetSimulationValues.START_SIMULATION_TIME.getValue() + 1, PriorityValues.SYSTEM_EVENT.getValue(), this));
        this.isGeneratedFirstSystemEvent = true;
    }

    @Override
    protected void afterRunSimulation() {

    }


    @Override
    protected void afterSimulation() {

    }

    @Override
    public void dataHandling() {
        //System.out.println("It was updated )");
        FurnitureEventState state = (FurnitureEventState) this.state;

        state.setSimulationTime(this.simulationTime);
        //System.out.println(state.getSimulationTime());
        state.setAllOrders(ordersArrayList);
        state.setWorkersA(workPlace.getWorkersOne());
        state.setWorkersB(workPlace.getWorkersThree());
        state.setWorkersC(workPlace.getWorkersTwo());

        this.listener.setState(state);
        this.listener.notifyObservers();

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


    public UniformContinuous getComputingTypeOneDist() {
        return computingTypeOneDist;
    }

    public UniformContinuous getComputingTypeTwoDist() {
        return computingTypeTwoDist;
    }


    public UniformContinuous getComputingTypeThreeDist() {
        return computingTypeThreeDist;
    }


    public UniformContinuous getFittingTypeThreeDist() {
        return fittingTypeThreeDist;
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

    public int getCountWorkerA() {
        return countWorkerA;
    }

    public void setCountWorkerA(int countWorkerA) {
        this.countWorkerA = countWorkerA;
    }

    public int getCountWorkerB() {
        return countWorkerB;
    }

    public void setCountWorkerB(int countWorkerB) {
        this.countWorkerB = countWorkerB;
    }

    public int getCountWorkerC() {
        return countWorkerC;
    }

    public void setCountWorkerC(int countWorkerC) {
        this.countWorkerC = countWorkerC;
    }
    public WorkPlace getWorkPlace() {
        return workPlace;
    }

}
