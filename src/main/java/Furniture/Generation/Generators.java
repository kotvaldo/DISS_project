package Furniture.Generation;

import java.util.ArrayList;
import java.util.Random;

import Generators.*;

public class Generators {
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
    public Generators() {


        Random rand = new Random(1);

        orderArrivalDist = new Exponential(1800.0, rand.nextInt());
        ArrayList<EmpiricData<Integer>> typeList = new ArrayList<>();
        typeList.add(new EmpiricData<>(1, 2, 0.5));
        typeList.add(new EmpiricData<>(2, 3, 0.15));
        typeList.add(new EmpiricData<>(3, 4, 0.35));
        typeOfOrderDist = new EmpiricDiscrete(typeList, rand.nextInt());
        ArrayList<EmpiricData<Double>> typeOneCuttingList = new ArrayList<>();
        typeOneCuttingList.add(new EmpiricData<>(600.0, 1500.0, 0.6));
        typeOneCuttingList.add(new EmpiricData<>(1500.0, 3000.0, 0.4));
        //first
        cuttingTypeOneDist = new EmpiricContinuous(typeOneCuttingList, rand.nextInt());
        coloringTypeOneDist = new UniformContinuous(12000.0, 36600.0, rand.nextInt());
        assemblyTypeOneDist = new UniformContinuous(1800.0, 3600.0, rand.nextInt());

        // second
        cuttingTypeTwoDist = new UniformContinuous(720.0, 960.0, rand.nextInt());
        coloringTypeTwoDist = new UniformContinuous(12600.0, 32400.0, rand.nextInt());
        assemblyTypeTwoDist = new UniformContinuous(840.0, 1440.0, rand.nextInt());


        // third
        cuttingTypeThreeDist = new UniformContinuous(900.0, 4800.0, rand.nextInt());
        coloringTypeThreeDist = new UniformContinuous(36000.0, 42000.0, rand.nextInt());
        assemblyTypeThreeDist = new UniformContinuous(2100.0, 4500.0, rand.nextInt());
        montageDist = new UniformContinuous(900.0, 1500.0, rand.nextInt());

        //moving Dist
        timeMovingToStorageDist = new Triangular(60.0, 480.0, 120.0, rand.nextInt());
        timeInStorageDist = new Triangular(300.0, 900.0, 500.0, rand.nextInt());
        timeMovingToAnotherWorkshopDist = new Triangular(120.0, 500.0, 150.0, rand.nextInt());
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
}
