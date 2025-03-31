package Furniture.Observers;

import Furniture.FurnitureEventState;
import Observer.IObserver;
import State.IState;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;

import javax.swing.*;

public class GraphObserver implements IObserver {
    private final XYSeries timeOfWorkingSeries;
    private JFreeChart chart;

    public GraphObserver(XYSeries timeOfWorking, JFreeChart chart) {
        this.timeOfWorkingSeries = timeOfWorking;
        this.chart = chart;
    }

    @Override
    public void update(IState state) {
        FurnitureEventState fState = (FurnitureEventState) state;
        if (!fState.isSlowDown()) {
            if(fState.getRepCount() > fState.getBurnRepCount()) {
                SwingUtilities.invokeLater(() -> {
                    int rep = fState.getRepCount();
                    double mean = fState.getAverageTimeOfWorking().mean();
                    timeOfWorkingSeries.add(rep, mean);
                });
                setRangeAxis(timeOfWorkingSeries.getMinY(), timeOfWorkingSeries.getMaxY());
            }

        }
    }

    private void setRangeAxis(double minY, double maxY) {
        SwingUtilities.invokeLater(() -> {
            chart.getXYPlot().getRangeAxis().setRange(minY - 1, maxY + 1);
        });
    }
}
