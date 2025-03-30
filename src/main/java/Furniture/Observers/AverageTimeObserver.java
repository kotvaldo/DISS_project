package Furniture.Observers;

import Furniture.FurnitureEventState;
import Observer.IObserver;
import State.IState;

import javax.swing.*;

public class AverageTimeObserver implements IObserver {
    private final JLabel timeOfWorkLabel;
    private final JLabel timeOfWorkIntervalLabel;

    public AverageTimeObserver(JLabel timeOfWorkLabel, JLabel timeOfWorkIntervalLabel) {
        this.timeOfWorkLabel = timeOfWorkLabel;
        this.timeOfWorkIntervalLabel = timeOfWorkIntervalLabel;

    }
    @Override
    public void update(IState state) {

        FurnitureEventState s = (FurnitureEventState) state;

        if(!s.isSlowDown()) {
            timeOfWorkLabel.setText("Average Time of Work: " + String.format("%.2f", s.getAverageTimeOfWorking().mean()));
            timeOfWorkIntervalLabel.setText("CI: [" + s.getAverageTimeOfWorking().confidenceInterval());
        }

    }

}
