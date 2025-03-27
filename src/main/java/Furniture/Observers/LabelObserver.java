package Furniture.Observers;

import Furniture.FurnitureEventState;
import Observer.IObserver;
import State.IState;
import Utility.Utility;

import javax.swing.*;

public class LabelObserver implements IObserver {
    private final JLabel label;
    private final JLabel dayCountLabel;
    public LabelObserver(JLabel label, JLabel dayCountLabel) {
        this.label = label;
        this.dayCountLabel = dayCountLabel;

    }

    @Override
    public void update(IState state) {
        SwingUtilities.invokeLater(() -> {
            FurnitureEventState furnitureEventState = (FurnitureEventState) state;
            double currSimulation = furnitureEventState.getSimulationTime();

            this.label.setText("Simulation Time: " + Utility.fromSecondsToTime(currSimulation));

            this.dayCountLabel.setText("Day Count : " + (furnitureEventState.getCurrentDay() + 1));
        });

    }
}
