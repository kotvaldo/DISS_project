package Furniture.Observers;

import Furniture.FurnitureEventState;
import Observer.IObserver;
import State.IState;

import javax.swing.*;

public class QueueLengthObserver implements IObserver {
    private JLabel queueLengthLabel;
    private JLabel queueLength2Label;
    private JLabel queueLength3Label;
    private JLabel queueLength4Label;

    public QueueLengthObserver(JLabel queueLengthLabel, JLabel queue2LengthLabel, JLabel queue3LengthLabel, JLabel queue4LengthLabel) {
        this.queueLengthLabel = queueLengthLabel;
        this.queueLength2Label = queue2LengthLabel;
        this.queueLength3Label = queue3LengthLabel;
        this.queueLength4Label = queue4LengthLabel;
    }
    @Override
    public void update(IState state) {
        FurnitureEventState fState = (FurnitureEventState) state;
        if(fState.isSlowDown()) {
            SwingUtilities.invokeLater(() -> {
               this.queueLengthLabel.setText("Cutting QL : " + fState.getQueueCutting());
               this.queueLength3Label.setText("Assembly QL : " + fState.getQueueAssembly());
               this.queueLength4Label.setText("Montage QL : " + fState.getQueueMontage());
               this.queueLength2Label.setText("Coloring QL : " + fState.getQueueColoring());
            });
        }
    }
}
