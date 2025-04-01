package Furniture.Generation;

import java.util.ArrayList;
import java.util.Random;

import Generators.*;

public class Generators {
    private final Exponential orderArrivalDist;
    private final EmpiricDiscrete typeOfOrderDist;
    private final EmpiricContinuous cuttingTableDist;
    private final UniformContinuous cuttingChairDist;
    private final UniformContinuous cuttingWardrobeDist;
    private final UniformContinuous coloringTableDist;
    private final UniformContinuous coloringChairDist;
    private final UniformContinuous coloringWardrobeDist;
    private final UniformContinuous assemblyTableDist;
    private final UniformContinuous assemblyChairDist;
    private final UniformContinuous assemblyWardrobeDist;
    private final UniformContinuous montageWardrobeDist;
    private final Triangular timeSpentInStorageDist;
    private final Triangular timeMovingIntoStorageDist;
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
        typeOneCuttingList.add(new EmpiricData<>(10.0 * 60, 25.0 * 60, 0.6));
        typeOneCuttingList.add(new EmpiricData<>(25.0 * 60, 50.0 * 60, 0.4));
        //first
        cuttingTableDist = new EmpiricContinuous(typeOneCuttingList, rand.nextInt());
        coloringTableDist = new UniformContinuous(200.0 * 60, 610.0 * 60, rand.nextInt());
        assemblyTableDist = new UniformContinuous(30.0 * 60, 60.0 * 60, rand.nextInt());

        // second
        cuttingChairDist = new UniformContinuous(12.0 * 60, 16.0 * 60, rand.nextInt());
        coloringChairDist = new UniformContinuous(210.0 * 60, 540.0 * 60, rand.nextInt());
        assemblyChairDist = new UniformContinuous(14.0 * 60, 24.0 * 60, rand.nextInt());


        // third
        cuttingWardrobeDist = new UniformContinuous(15.0 * 60, 80.0 * 60, rand.nextInt());
        coloringWardrobeDist = new UniformContinuous(600.0 * 60, 700.0 * 60, rand.nextInt());
        assemblyWardrobeDist = new UniformContinuous(35.0 * 60, 75.0 * 60, rand.nextInt());
        montageWardrobeDist = new UniformContinuous(15.0 * 60, 25.0 * 60, rand.nextInt());

        //moving Dist
        timeMovingIntoStorageDist = new Triangular(60.0, 480.0, 120.0, rand.nextInt());
        timeSpentInStorageDist = new Triangular(300.0, 900.0, 500.0, rand.nextInt());
        timeMovingToAnotherWorkshopDist = new Triangular(120.0, 500.0, 150.0, rand.nextInt());
    }

    public Exponential getOrderArrivalDist() {
        return orderArrivalDist;
    }

    public EmpiricDiscrete getTypeOfOrderDist() {
        return typeOfOrderDist;
    }

    public EmpiricContinuous getCuttingTableDist() {
        return cuttingTableDist;
    }

    public UniformContinuous getCuttingChairDist() {
        return cuttingChairDist;
    }

    public UniformContinuous getCuttingWardrobeDist() {
        return cuttingWardrobeDist;
    }

    public UniformContinuous getColoringTableDist() {
        return coloringTableDist;
    }

    public UniformContinuous getColoringChairDist() {
        return coloringChairDist;
    }

    public UniformContinuous getColoringWardrobeDist() {
        return coloringWardrobeDist;
    }

    public UniformContinuous getAssemblyTableDist() {
        return assemblyTableDist;
    }

    public UniformContinuous getAssemblyChairDist() {
        return assemblyChairDist;
    }

    public UniformContinuous getAssemblyWardrobeDist() {
        return assemblyWardrobeDist;
    }

    public UniformContinuous getMontageWardrobeDist() {
        return montageWardrobeDist;
    }

    public Triangular getTimeSpentInStorageDist() {
        return timeSpentInStorageDist;
    }

    public Triangular getTimeMovingIntoStorageDist() {
        return timeMovingIntoStorageDist;
    }

    public Triangular getTimeMovingToAnotherWorkshopDist() {
        return timeMovingToAnotherWorkshopDist;
    }
}
