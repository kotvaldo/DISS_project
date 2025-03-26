package MonteCarlo;

import Observer.IObserver;
import State.IState;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;

import javax.swing.*;

public class MonteCarloGraphObserver implements IObserver {
    private final XYSeries dataset;
    private final JFreeChart chart;


    public MonteCarloGraphObserver(XYSeries series, JFreeChart chart) {
        dataset = series;
        this.chart = chart;
    }


    @Override
    public void update(IState state) {
        MonteCarloState monteCarloState = (MonteCarloState) state;
        SwingUtilities.invokeLater(() -> {
            dataset.add(monteCarloState.getRepCount(), monteCarloState.getAverage());
            updateChartRange();
        });

    }

    private void updateChartRange() {
        XYPlot plot = chart.getXYPlot();
        ValueAxis rangeAxis = plot.getRangeAxis();

        double minY = dataset.getMinY();
        double maxY = dataset.getMaxY();

        rangeAxis.setRange(minY - 1 , maxY + 1);
    }
}
