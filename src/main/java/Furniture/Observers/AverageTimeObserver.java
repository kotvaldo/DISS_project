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

        if (!s.isSlowDown()) {
            SwingUtilities.invokeLater(() -> {
                s.getAverageTimeOfWorking().confidenceInterval();
                timeOfWorkLabel.setText("Average Time of Work (s): " + String.format("%.2f", s.getAverageTimeOfWorking().mean()) + "     Average Time of Work (h): " + String.format("%.2f", s.getAverageTimeOfWorking().mean() / 3600.0));
                timeOfWorkIntervalLabel.setText("CI(s): [" + s.getAverageTimeOfWorking().confidenceInterval() + "     CI(h):" + String.format("[ %.2f, %.2f ]", s.getAverageTimeOfWorking().getLowerBound() / 3600.0, s.getAverageTimeOfWorking().getUpperBound() / 3600.0));
            });

        }

    }

}
