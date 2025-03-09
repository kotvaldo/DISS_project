package GUI;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public abstract class AbstractSimulationGUI extends JFrame {
    protected XYSeries series;
    protected JFreeChart chart;
    protected JTextField replicationsInput;
    protected JTextField burnInInput;
    protected JTextField updateFrequencyInput;
    protected JButton startButton;
    protected JButton stopButton;
    protected JLabel meanLabel;
    protected JLabel medianLabel;
    protected JLabel varianceLabel;
    protected JLabel stdDevLabel;
    protected JLabel confidenceIntervalLabel95;
    protected JLabel confidenceIntervalLabel90;

    protected AbstractSimulationGUI(String title) {
        setTitle(title);
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeChart();
        initializeInputFields();
        initializeButtons();

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Replications:"));
        inputPanel.add(replicationsInput);
        inputPanel.add(new JLabel("Burn-in:"));
        inputPanel.add(burnInInput);
        inputPanel.add(new JLabel("Update-Frequency:"));
        inputPanel.add(updateFrequencyInput);

        JPanel controlPanel = new JPanel();
        controlPanel.add(startButton);
        controlPanel.add(stopButton);

        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(1, 1));

        meanLabel = new JLabel("Mean: N/A");
        medianLabel = new JLabel("Median: N/A");
        varianceLabel = new JLabel("Variance: N/A");
        stdDevLabel = new JLabel("Standard Deviation: N/A");
        confidenceIntervalLabel95 = new JLabel("95% CI: N/A");
        confidenceIntervalLabel90 = new JLabel("90% CI: N/A");

        statsPanel.add(meanLabel);
        statsPanel.add(medianLabel);
        statsPanel.add(varianceLabel);
        statsPanel.add(stdDevLabel);
        statsPanel.add(confidenceIntervalLabel95);
        statsPanel.add(confidenceIntervalLabel90);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.NORTH);
        topPanel.add(statsPanel, BorderLayout.SOUTH);

        getContentPane().add(new ChartPanel(chart), BorderLayout.CENTER);
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(controlPanel, BorderLayout.SOUTH);
    }

    private void initializeChart() {
        series = new XYSeries("Simulation Average");
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        chart = ChartFactory.createXYLineChart("Simulation", "Iterations", "Average Value", dataset);
        chart.getXYPlot().getRangeAxis().setFixedAutoRange(50);
        chart.getXYPlot().getDomainAxis().setAutoRange(true);
    }

    private void initializeInputFields() {
        replicationsInput = new JTextField("1000000", 10);
        burnInInput = new JTextField("100", 10);
        updateFrequencyInput = new JTextField("1000", 10);
    }

    private void initializeButtons() {
        startButton = new JButton("Start Simulation");
        stopButton = new JButton("Stop Simulation");
        stopButton.setEnabled(false);
        startButton.addActionListener(e -> startSimulation());
        stopButton.addActionListener(e -> stopSimulation());
    }



    protected void clearStatistic() {
        meanLabel.setText("Mean: N/A");
        medianLabel.setText("Median: N/A");
        varianceLabel.setText("Variance: N/A");
        stdDevLabel.setText("Standard Deviation: N/A");
        confidenceIntervalLabel95.setText("95% CI: N/A");
        confidenceIntervalLabel90.setText("90% CI: N/A");
    }

    protected void updateStatisticsFromDataset() {
        int count = series.getItemCount();
        if (count == 0) return;

        double sum = 0;
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        double[] values = new double[count];

        for (int i = 0; i < count; i++) {
            double value = series.getY(i).doubleValue();
            values[i] = value;
            sum += value;
            if (value < min) min = value;
            if (value > max) max = value;
        }

        double mean = sum / count;

        Arrays.sort(values);
        double median = (count % 2 == 0) ?
                (values[count / 2 - 1] + values[count / 2]) / 2.0 : values[count / 2];

        double varianceSum = 0;
        for (double value : values) {
            varianceSum += Math.pow(value - mean, 2);
        }
        double variance = varianceSum / count;
        double stdDev = Math.sqrt(variance);

        double z = 1.96;
        double confidenceInterval = z * (stdDev / Math.sqrt(count));

        double lowerBound = mean - confidenceInterval;
        double upperBound = mean + confidenceInterval;

        SwingUtilities.invokeLater(() -> {
            meanLabel.setText("Mean: " + String.format("%.2f", mean));
            medianLabel.setText("Median: " + String.format("%.2f", median));
            varianceLabel.setText("Variance: " + String.format("%.2f", variance));
            stdDevLabel.setText("Standard Deviation: " + String.format("%.2f", stdDev));
            confidenceIntervalLabel95.setText("95% CI: [" + String.format("%.2f", lowerBound) + ", " + String.format("%.2f", upperBound) + "]");
        });
    }

    protected abstract void startSimulation();
    protected abstract void stopSimulation();
}
