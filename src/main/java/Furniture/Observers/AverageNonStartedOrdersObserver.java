package Furniture.Observers;

import Furniture.FurnitureEventState;
import Observer.IObserver;
import State.IState;

import javax.swing.*;

public class AverageNonStartedOrdersObserver implements IObserver {
    private final JLabel nonStartedLabel;
    private final JLabel nonStartedIntervalLabel;

    public AverageNonStartedOrdersObserver(JLabel nonStartedLabel, JLabel nonStartedIntervalLabel) {
        this.nonStartedLabel = nonStartedLabel;
        this.nonStartedIntervalLabel = nonStartedIntervalLabel;

    }
    @Override
    public void update(IState state) {

        FurnitureEventState s = (FurnitureEventState) state;

        if(!s.isSlowDown()) {
            SwingUtilities.invokeLater(() -> {
                nonStartedLabel.setText("Average Non-Started Orders: " + String.format("%.2f", s.getNewOrderOnEnd().mean()));
                nonStartedIntervalLabel.setText("CI: [" + s.getNewOrderOnEnd().confidenceInterval());
            });
        }

    }

}
