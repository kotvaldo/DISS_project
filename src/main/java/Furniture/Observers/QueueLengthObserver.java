package Furniture.Observers;

import Furniture.FurnitureEventState;
import Observer.IObserver;
import State.IState;

import javax.swing.*;

public class QueueLengthObserver implements IObserver {
    private final JLabel queueLengthLabel;
    private final JLabel queueLength2Label;
    private final JLabel queueLength3Label;
    private final JLabel queueLength4Label;

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
        } else {
            SwingUtilities.invokeLater(() -> {
                fState.getCuttingQLStat().confidenceInterval();
                fState.getAssemblyQLStat().confidenceInterval();
                fState.getMontageQLStat().confidenceInterval();
                fState.getColoringQLStat().confidenceInterval();

                this.queueLengthLabel.setText(String.format("Cutting QL : %.5f  CI: [%.5f ; %.5f]",
                        fState.getCuttingQLStat().mean(),
                        fState.getCuttingQLStat().getLowerBound(),
                        fState.getCuttingQLStat().getUpperBound()));

                this.queueLength2Label.setText(String.format("Coloring QL : %.5f  CI: [%.5f ; %.5f]",
                        fState.getColoringQLStat().mean(),
                        fState.getColoringQLStat().getLowerBound(),
                        fState.getColoringQLStat().getUpperBound()));

                this.queueLength3Label.setText(String.format("Assembly QL : %.5f  CI: [%.5f ; %.5f]",
                        fState.getAssemblyQLStat().mean(),
                        fState.getAssemblyQLStat().getLowerBound(),
                        fState.getAssemblyQLStat().getUpperBound()));

                this.queueLength4Label.setText(String.format("Montage QL : %.5f  CI: [%.5f ; %.5f]",
                        fState.getMontageQLStat().mean(),
                        fState.getMontageQLStat().getLowerBound(),
                        fState.getMontageQLStat().getUpperBound()));
            });

        }
    }
}
