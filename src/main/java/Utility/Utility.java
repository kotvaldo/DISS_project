package Utility;

import java.util.ArrayList;
import java.util.Collections;

public class Utility {
    private Utility() {

    }

    public static ArrayList<Integer> exponentialSmoothing(ArrayList<Integer> demandHistory, double alpha) {
        ArrayList<Integer> predictions = new ArrayList<>();

        if (demandHistory.isEmpty()) return predictions;

        double smoothedValue = demandHistory.getFirst();
        predictions.add((int) Math.round(smoothedValue));

        for (int i = 1; i < demandHistory.size(); i++) {
            int actualDemand = demandHistory.get(i);
            smoothedValue = alpha * actualDemand + (1 - alpha) * smoothedValue;
            predictions.add((int) Math.round(smoothedValue));
        }

        return predictions;
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



}
