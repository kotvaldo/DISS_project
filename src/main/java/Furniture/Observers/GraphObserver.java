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
    private XYSeries intervalLower;
    private XYSeries intervalUpper;

    public GraphObserver(XYSeries timeOfWorking, JFreeChart chart, XYSeries intervalLower, XYSeries intervalUpper) {
        this.timeOfWorkingSeries = timeOfWorking;
        this.chart = chart;
        this.intervalLower = intervalLower;
        this.intervalUpper = intervalUpper;
    }

    @Override
    public void update(IState state) {
        FurnitureEventState fState = (FurnitureEventState) state;
        if (!fState.isSlowDown()) {
            if (fState.getRepCount() > fState.getBurnRepCount()) {
                if (fState.getRepCount() % 2 == 0) {

                    SwingUtilities.invokeLater(() -> {
                        int rep = fState.getRepCount();
                        double mean = fState.getAverageTimeOfWorking().mean();
                        timeOfWorkingSeries.add(rep, mean);
                        fState.getAverageTimeOfWorking().confidenceInterval();
                        intervalLower.add(rep, fState.getAverageTimeOfWorking().getLowerBound());
                        intervalUpper.add(rep, fState.getAverageTimeOfWorking().getUpperBound());
                    });
                    setRangeAxis(intervalLower.getMinY(), intervalUpper.getMaxY());
                }
            }


        }
    }

    private void setRangeAxis(double minY, double maxY) {
        SwingUtilities.invokeLater(() -> {
            chart.getXYPlot().getRangeAxis().setRange(minY - 1, maxY + 1);
        });
    }
}
