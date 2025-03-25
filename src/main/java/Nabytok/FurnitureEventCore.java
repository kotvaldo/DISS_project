package Nabytok;

import EventSimulation.EventSimulationCore;
import Generators.*;
import Nabytok.Entity.WorkPlace;
import Nabytok.Enums.PresetSimulationValues;
import Nabytok.Events.OrderArrivalEvent;

import java.util.ArrayList;

public class FurnitureEventCore extends EventSimulationCore {
    private final Exponential orderArrivalDist;
    private final EmpiricDiscrete typeOfOrderDist;
    private final EmpiricContinuous preparingTypeOneDist;
    private final UniformContinuous preparingTypeTwoDist;
    private final UniformContinuous preparingTypeThreeDist;
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
    private int countWorkerA;
    private int countWorkerB;
    private int countWorkerC;
    private WorkPlace workPlace;

    public FurnitureEventCore() {
        super();
        state = new FurnitureEventState();

        orderArrivalDist = new Exponential(1800.0);
        ArrayList<EmpiricData<Integer>> typeList = new ArrayList<>();
        typeList.add(new EmpiricData<>(1, 2, 0.5));
        typeList.add(new EmpiricData<>(2, 3, 0.2));
        typeList.add(new EmpiricData<>(3, 4, 0.3));
        typeOfOrderDist = new EmpiricDiscrete(typeList);
        ArrayList<EmpiricData<Double>> preparingList = new ArrayList<>();
        preparingList.add(new EmpiricData<>(10.0, 25.0, 0.6));
        preparingList.add(new EmpiricData<>(25.0, 50.0, 0.4));
        //first
        preparingTypeOneDist = new EmpiricContinuous(preparingList);
        coloringTypeOneDist = new UniformContinuous(12000.0, 36600.0);
        computingTypeOneDist = new UniformContinuous(1800.0, 3600.0);

        // second
        preparingTypeTwoDist = new UniformContinuous(720.0, 960.0);
        coloringTypeTwoDist = new UniformContinuous(12600.0, 32400.0);
        computingTypeTwoDist = new UniformContinuous(840.0, 1440.0);

        // third
        preparingTypeThreeDist = new UniformContinuous(900.0, 4800.0);
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

    }
    @Override
    protected void beforeSimulation() {
        workPlace.clearOrderQueues();
        double time = this.simulationTime + orderArrivalDist.sample();
        events.add(new OrderArrivalEvent(time, 1, this));
    }

    @Override
    protected void afterRunSimulation() {

    }


    @Override
    protected void afterSimulation() {

    }

    @Override
    protected void dataHandling() {

    }



    public Exponential getOrderArrivalDist() {
        return orderArrivalDist;
    }

    public EmpiricDiscrete getTypeOfOrderDist() {
        return typeOfOrderDist;
    }


    public EmpiricContinuous getPreparingTypeOneDist() {
        return preparingTypeOneDist;
    }


    public UniformContinuous getPreparingTypeTwoDist() {
        return preparingTypeTwoDist;
    }


    public UniformContinuous getPreparingTypeThreeDist() {
        return preparingTypeThreeDist;
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
