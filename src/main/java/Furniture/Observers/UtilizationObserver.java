package Furniture.Observers;

import Furniture.FurnitureEventCore;
import Furniture.FurnitureEventState;
import Observer.IObserver;
import State.IState;

import javax.swing.*;

public class UtilizationObserver implements IObserver {
    private JLabel utilizationALabel;
    private JLabel utilizationAIntervalLabel;

    private JLabel utilizationBLabel;
    private JLabel utilizationBIntervalLabel;

    private JLabel utilizationCLabel;
    private JLabel utilizationCIntervalLabel;

    private JLabel utilizationAllLabel;
    private JLabel utilizationAllIntervalLabel;

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
                utilizationALabel.setText(String.format("Utilization A: %.7f%%", fstate.getUtilisationA().mean()));
                utilizationAIntervalLabel.setText(fstate.getUtilisationA().confidenceInterval());

                utilizationBLabel.setText(String.format("Utilization B: %.7f%%", fstate.getUtilisationB().mean()));
                utilizationBIntervalLabel.setText(fstate.getUtilisationB().confidenceInterval());

                utilizationCLabel.setText(String.format("Utilization C: %.7f%%", fstate.getUtilisationC().mean()));
                utilizationCIntervalLabel.setText(fstate.getUtilisationC().confidenceInterval());

                utilizationAllLabel.setText(String.format("Utilization All: %.7f%%", fstate.getUtilisationAll().mean()));
                utilizationAllIntervalLabel.setText(fstate.getUtilisationAll().confidenceInterval());
            });

        }

    }
}
