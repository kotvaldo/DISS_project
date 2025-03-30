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
    public LabelObserver(JLabel label, JLabel dayCountLabel, JLabel replicationCountLabel) {
        this.label = label;
        this.dayCountLabel = dayCountLabel;
        this.repCountLabel = replicationCountLabel;

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
            }

        });

    }
}
