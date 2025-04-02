package Furniture.Observers;

import Furniture.FurnitureEventState;
import Observer.IObserver;
import State.IState;

import javax.swing.*;

public class UtilizationObserver implements IObserver {
    private final JLabel utilizationALabel;
    private final JLabel utilizationAIntervalLabel;

    private final JLabel utilizationBLabel;
    private final JLabel utilizationBIntervalLabel;

    private final JLabel utilizationCLabel;
    private final JLabel utilizationCIntervalLabel;

    private final JLabel utilizationAllLabel;
    private final JLabel utilizationAllIntervalLabel;

    public UtilizationObserver(
            JLabel utilizationALabel, JLabel utilizationAIntervalLabel,
            JLabel utilizationBLabel, JLabel utilizationBIntervalLabel,
            JLabel utilizationCLabel, JLabel utilizationCIntervalLabel,
            JLabel utilizationAllLabel, JLabel utilizationAllIntervalLabel
    ) {
        this.utilizationALabel = utilizationALabel;
        this.utilizationAIntervalLabel = utilizationAIntervalLabel;

        this.utilizationBLabel = utilizationBLabel;
        this.utilizationBIntervalLabel = utilizationBIntervalLabel;

        this.utilizationCLabel = utilizationCLabel;
        this.utilizationCIntervalLabel = utilizationCIntervalLabel;

        this.utilizationAllLabel = utilizationAllLabel;
        this.utilizationAllIntervalLabel = utilizationAllIntervalLabel;
    }

    @Override
    public void update(IState state) {
        FurnitureEventState fstate = (FurnitureEventState) state;
        if(!fstate.isSlowDown()) {
            SwingUtilities.invokeLater(() -> {
                utilizationALabel.setText(String.format("Utilization A: %.7f%%", fstate.getUtilisationA().mean() * 100));
                utilizationAIntervalLabel.setText(fstate.getUtilisationA().confidenceInterval());

                utilizationBLabel.setText(String.format("Utilization B: %.7f%%", fstate.getUtilisationB().mean() * 100));
                utilizationBIntervalLabel.setText(fstate.getUtilisationB().confidenceInterval());

                utilizationCLabel.setText(String.format("Utilization C: %.7f%%", fstate.getUtilisationC().mean() * 100));
                utilizationCIntervalLabel.setText(fstate.getUtilisationC().confidenceInterval());

                utilizationAllLabel.setText(String.format("Utilization All: %.7f%%", fstate.getUtilisationAll().mean() * 100));
                utilizationAllIntervalLabel.setText(fstate.getUtilisationAll().confidenceInterval());
            });

        }

    }
}
