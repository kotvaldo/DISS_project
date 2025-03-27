package Furniture.Observers;

import Furniture.FurnitureEventState;
import Observer.IObserver;
import State.IState;

import javax.swing.*;

public class SimulationTimeObserver implements IObserver {
    private final JLabel label;
    public SimulationTimeObserver(JLabel label) {
        this.label = label;

    }

    @Override
    public void update(IState state) {
        SwingUtilities.invokeLater(() -> {
            FurnitureEventState furnitureEventState = (FurnitureEventState) state;
            this.label.setText("Simulation Time: " + furnitureEventState.getSimulationTime());
        });

    }
}
