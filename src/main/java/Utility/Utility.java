package Utility;

import Furniture.Entity.Order;
import Furniture.Entity.WorkPlace;
import Furniture.Entity.Worker;
import Furniture.Enums.WorkerBussyState;
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
            totalTime += core.getTimeInStorageDist().sample();

        } else if (!current.equals(target)) {
            totalTime += core.getTimeMovingToAnotherWorkshopDist().sample();
            totalTime += core.getTimeInStorageDist().sample();
            totalTime += core.getTimeInStorageDist().sample();

        } else {
            totalTime += core.getTimeInStorageDist().sample();
            totalTime += core.getTimeInStorageDist().sample();
        }
        worker.setCurrentWorkPlace(target);

        totalTime += core.getTimeInStorageDist().sample();
        if(order.getType() == 1) {
            totalTime += core.getCuttingTypeOneDist().sample();
        } else if(order.getType() == 2) {
            totalTime += core.getCuttingTypeTwoDist().sample();
        } else if(order.getType() == 3) {
            totalTime += core.getCuttingTypeThreeDist().sample();
        }
        return totalTime;

    }

    public static double calculateSecondTime(Order order, FurnitureEventCore core, Worker worker) {
        double totalTime = 0.0;

        WorkPlace current = worker.getCurrentWorkPlace();
        WorkPlace target = order.getWorkPlace();

        if (current == null) {
            totalTime += core.getTimeInStorageDist().sample();

        } else if (!current.equals(target)) {
            totalTime += core.getTimeMovingToAnotherWorkshopDist().sample();


        }

        worker.setCurrentWorkPlace(target);


        if(order.getType() == 1) {
            totalTime += core.getColoringTypeOneDist().sample();
        } else if(order.getType() == 2) {
            totalTime += core.getColoringTypeTwoDist().sample();
        } else if(order.getType() == 3) {
            totalTime += core.getColoringTypeThreeDist().sample();
        }
        totalTime += core.getTimeMovingToAnotherWorkshopDist().sample();
        return totalTime;

    }

    public static double calculateThird(Order order, FurnitureEventCore core, Worker worker) {
        double totalTime = 0.0;

        WorkPlace current = worker.getCurrentWorkPlace();
        WorkPlace target = order.getWorkPlace();

        if (current == null) {
            totalTime += core.getTimeInStorageDist().sample();

        } else if (!current.equals(target)) {
            totalTime += core.getTimeMovingToAnotherWorkshopDist().sample();


        }

        worker.setCurrentWorkPlace(target);
        if(order.getType() == 1) {
            totalTime += core.getAssemblyTypeOneDist().sample();
        } else if(order.getType() == 2) {
            totalTime += core.getAssemblyTypeTwoDist().sample();
        } else if(order.getType() == 3) {
            totalTime += core.getAssemblyTypeThreeDist().sample();
        }
        totalTime += core.getTimeMovingToAnotherWorkshopDist().sample();
        return totalTime;
    }
    public static double calculateFourth(Order order, FurnitureEventCore core, Worker worker) {
        double totalTime = 0.0;

        WorkPlace current = worker.getCurrentWorkPlace();
        WorkPlace target = order.getWorkPlace();

        if (current == null) {
            totalTime += core.getTimeInStorageDist().sample();

        } else if (!current.equals(target)) {
            totalTime += core.getTimeMovingToAnotherWorkshopDist().sample();
        }

        worker.setCurrentWorkPlace(target);
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
