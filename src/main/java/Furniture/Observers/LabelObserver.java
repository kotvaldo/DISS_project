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
    private final JLabel countOfFinished;
    private final JLabel countOfAll;

    public LabelObserver(JLabel label, JLabel dayCountLabel, JLabel replicationCountLabel, JLabel countOfFinishedOrders, JLabel countOfAllOrders) {
        this.label = label;
        this.dayCountLabel = dayCountLabel;
        this.repCountLabel = replicationCountLabel;
        this.countOfFinished = countOfFinishedOrders;
        this.countOfAll = countOfAllOrders;

    }

    @Override
    public void update(IState state) {
        SwingUtilities.invokeLater(() -> {
            FurnitureEventState furnitureEventState = (FurnitureEventState) state;
            if(furnitureEventState.isSlowDown()) {
                double currSimulation = furnitureEventState.getSimulationTime();

                this.label.setText("Simulation Time: " + Utility.fromSecondsToTime(currSimulation));

                this.dayCountLabel.setText("Day Count : " + (furnitureEventState.getCurrentDay()));
                this.countOfFinished.setText("Finished Orders : " + furnitureEventState.getCountOfFinishedOrders());
                this.countOfAll.setText("All Orders : " + furnitureEventState.getCountOfAllOrders());

            } else {
                this.repCountLabel.setText("Rep Count : " + furnitureEventState.getRepCount());
                this.countOfFinished.setText("Finished : " + String.format("%.4f", furnitureEventState.getAvgFinishedOrders().mean()) + " , "+ furnitureEventState.getAvgFinishedOrders().confidenceInterval());
                this.countOfAll.setText("All : " + String.format("%.4f", furnitureEventState.getAvgAllOrders().mean()) + " , " + furnitureEventState.getAvgAllOrders().confidenceInterval());
            }

        });

    }
}
