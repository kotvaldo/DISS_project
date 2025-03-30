package Furniture.Observers;

import Furniture.FurnitureEventState;
import Observer.IObserver;
import State.IState;
import Utility.Utility;

import javax.swing.*;

public class LabelObserver implements IObserver {
    private final JLabel label;
    private final JLabel dayCountLabel;
    private final JLabel repCountLabel;
    private final JLabel averageTimeOfWorking;
    private final JLabel countOfNonStarted;
    public LabelObserver(JLabel label, JLabel dayCountLabel, JLabel replicationCountLabel,  JLabel countOfNonStarted, JLabel averageTimeOfWorking) {
        this.label = label;
        this.dayCountLabel = dayCountLabel;
        this.repCountLabel = replicationCountLabel;
        this.averageTimeOfWorking = averageTimeOfWorking;
        this.countOfNonStarted = countOfNonStarted;

    }

    @Override
    public void update(IState state) {
        SwingUtilities.invokeLater(() -> {
            FurnitureEventState furnitureEventState = (FurnitureEventState) state;
            if(furnitureEventState.isSlowDown()) {
                double currSimulation = furnitureEventState.getSimulationTime();

                this.label.setText("Simulation Time: " + Utility.fromSecondsToTime(currSimulation));

                this.dayCountLabel.setText("Day Count : " + (furnitureEventState.getCurrentDay() + 1));

            } else {
                this.repCountLabel.setText("Rep Count : " + furnitureEventState.getRepCount());
                averageTimeOfWorking.setText("Average time of working : " +  furnitureEventState.getAverageTimeOfWorking().mean());
                countOfNonStarted.setText("Average non-started orders : " + Math.round(furnitureEventState.getNewOrderOnEnd().mean()));
            }

        });

    }
}
