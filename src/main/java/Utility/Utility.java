package Utility;

import Furniture.Entity.Order;
import Furniture.Entity.WorkPlace;
import Furniture.Entity.Worker;
import Furniture.FurnitureEventCore;

import java.util.ArrayList;
import java.util.Collections;

public class Utility {
    private Utility() {

    }

    public static int trimmedMean(ArrayList<Integer> demandList, double trimPercent) {
        if (demandList.isEmpty()) return 0;

        ArrayList<Integer> sortedList = new ArrayList<>(demandList);
        Collections.sort(sortedList);

        int trimCount = (int) (sortedList.size() * trimPercent);

        if (trimCount * 2 >= sortedList.size()) trimCount = sortedList.size() / 4;

        ArrayList<Integer> trimmedList = new ArrayList<>(sortedList.subList(trimCount, sortedList.size() - trimCount));

        return (int) trimmedList.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

    public static double calculateFirstTime(Order order, FurnitureEventCore core, Worker worker) {
        double totalTime = 0.0;

        WorkPlace current = worker.getCurrentWorkPlace();
        WorkPlace target = order.getWorkPlace();

        if (current == null) {
            totalTime += core.getGenerators().getTimeMovingIntoStorageDist().sample();

        } else if (!current.equals(target)) {
            totalTime += core.getGenerators().getTimeMovingToAnotherWorkshopDist().sample();
            totalTime += core.getGenerators().getTimeMovingIntoStorageDist().sample();
            totalTime += core.getGenerators().getTimeMovingIntoStorageDist().sample();

        } else {
            totalTime += core.getGenerators().getTimeMovingIntoStorageDist().sample();
            totalTime += core.getGenerators().getTimeMovingIntoStorageDist().sample();
        }

        worker.setCurrentWorkPlace(target);

        totalTime += core.getGenerators().getTimeSpentInStorageDist().sample();

        if(order.getType() == 1) {
            totalTime += core.getGenerators().getCuttingTableDist().sample();
        } else if(order.getType() == 2) {
            totalTime += core.getGenerators().getCuttingChairDist().sample();
        } else if(order.getType() == 3) {
            totalTime += core.getGenerators().getCuttingWardrobeDist().sample();
        }
        return totalTime;

    }

    public static double calculateSecondTime(Order order, FurnitureEventCore core, Worker worker) {
        double totalTime = 0.0;

        WorkPlace current = worker.getCurrentWorkPlace();
        WorkPlace target = order.getWorkPlace();

        if (current == null) {
            totalTime += core.getGenerators().getTimeMovingIntoStorageDist().sample();

        } else if (!current.equals(target)) {
            totalTime += core.getGenerators().getTimeMovingToAnotherWorkshopDist().sample();


        }

        worker.setCurrentWorkPlace(target);


        if(order.getType() == 1) {
            totalTime += core.getGenerators().getColoringTableDist().sample();
        } else if(order.getType() == 2) {
            totalTime += core.getGenerators().getColoringChairDist().sample();
        } else if(order.getType() == 3) {
            totalTime += core.getGenerators().getColoringWardrobeDist().sample();
        }

        return totalTime;

    }

    public static double calculateThird(Order order, FurnitureEventCore core, Worker worker) {
        double totalTime = 0.0;

        WorkPlace current = worker.getCurrentWorkPlace();
        WorkPlace target = order.getWorkPlace();

        if (current == null) {
            totalTime += core.getGenerators().getTimeMovingIntoStorageDist().sample();

        } else if (!current.equals(target)) {
            totalTime += core.getGenerators().getTimeMovingToAnotherWorkshopDist().sample();
        }

        worker.setCurrentWorkPlace(target);
        if(order.getType() == 1) {
            totalTime += core.getGenerators().getAssemblyTableDist().sample();
        } else if(order.getType() == 2) {
            totalTime += core.getGenerators().getAssemblyChairDist().sample();
        } else if(order.getType() == 3) {
            totalTime += core.getGenerators().getAssemblyWardrobeDist().sample();
        }
        return totalTime;
    }
    public static double calculateFourth(Order order, FurnitureEventCore core, Worker worker) {
        double totalTime = 0.0;

        WorkPlace current = worker.getCurrentWorkPlace();
        WorkPlace target = order.getWorkPlace();

        if (current == null) {
            totalTime += core.getGenerators().getTimeMovingIntoStorageDist().sample();
        } else if (!current.equals(target)) {
            totalTime += core.getGenerators().getTimeMovingToAnotherWorkshopDist().sample();
        }

        worker.setCurrentWorkPlace(target);

        totalTime += core.getGenerators().getMontageWardrobeDist().sample();

        return totalTime;
    }


    public static String fromSecondsToTime(double seconds) {
        int secondsInWorkday = 8 * 3600;
        int startHourSeconds = 6 * 3600;

        int secondsInCurrentDay = (int) seconds % secondsInWorkday;
        int shiftedSeconds = secondsInCurrentDay + startHourSeconds;

        int hours = shiftedSeconds / 3600;
        int minutes = (shiftedSeconds % 3600) / 60;
        int secs = shiftedSeconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }





}
