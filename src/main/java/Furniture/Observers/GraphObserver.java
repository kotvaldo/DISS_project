package Furniture.Observers;

import Furniture.FurnitureEventState;
import Observer.IObserver;
import State.IState;
import org.jfree.data.xy.XYSeries;

import javax.swing.*;

public class GraphObserver implements IObserver {
    XYSeries series;
    public GraphObserver(XYSeries series) {
        this.series = series;
    }

    @Override
    public void update(IState state) {
        FurnitureEventState fState = (FurnitureEventState) state;
        if(fState.isSlowDown()) {
            SwingUtilities.invokeLater(() -> {


            });
        } else {
            SwingUtilities.invokeLater(() -> {


            });
        }
    }
}
