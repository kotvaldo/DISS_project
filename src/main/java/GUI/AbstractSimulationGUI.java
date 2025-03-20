package GUI;

import org.apache.commons.math3.distribution.TDistribution;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;

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
    protected JPanel customPanel;
    protected JPanel inputPanel;

    protected AbstractSimulationGUI(String title) {
        setTitle(title);
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeChart();
        initializeInputFields();
        initializeButtons();

        this.inputPanel = new JPanel();
        inputPanel.add(new JLabel("Replications:"));
        inputPanel.add(replicationsInput);
        inputPanel.add(new JLabel("Burn-in:"));
        inputPanel.add(burnInInput);
        inputPanel.add(new JLabel("Update-Frequency:"));
        inputPanel.add(updateFrequencyInput);
        this.setupCustomInput();

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

        this.customPanel = new JPanel();
        this.customPanel.setVisible(false);
        setupCustomPanel();

        getContentPane().add(new ChartPanel(chart), BorderLayout.CENTER);
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(controlPanel, BorderLayout.SOUTH);
        getContentPane().add(this.customPanel, BorderLayout.EAST);
    }


    protected abstract void initializeChart();

    private void initializeInputFields() {
        replicationsInput = new JTextField("1000000", 10);
        burnInInput = new JTextField("1000", 10);
        updateFrequencyInput = new JTextField("1000", 10);
    }

    private void initializeButtons() {
        startButton = new JButton("Start Simulation");
        stopButton = new JButton("Stop Simulation");
        stopButton.setEnabled(false);
        startButton.addActionListener(e -> startSimulation());
        stopButton.addActionListener(e -> stopSimulation());
    }

    protected abstract void setupCustomChart();
    protected abstract void setupCustomInput();
    protected abstract void setupCustomPanel();
    protected abstract void startSimulation();
    protected abstract void stopSimulation();


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
        double[] values = new double[count];

        for (int i = 0; i < count; i++) {
            double value = series.getY(i).doubleValue();
            values[i] = value;
            sum += value;
        }

        double mean = sum / count;

        Arrays.sort(values);
        double median = (count % 2 == 0) ?
                (values[count / 2 - 1] + values[count / 2]) / 2.0 : values[count / 2];

        double varianceSum = 0;
        for (double value : values) {
            varianceSum += Math.pow(value - mean, 2);
        }
        double variance = varianceSum / (count - 1);
        double stdDev = Math.sqrt(variance);

        double z90, z95;
        if (count < 30) {
            int degreesOfFreedom = count - 1;
            TDistribution tDist = new TDistribution(degreesOfFreedom);
            z90 = tDist.inverseCumulativeProbability(0.95);
            z95 = tDist.inverseCumulativeProbability(0.975);
        } else {
            z90 = 1.6449; // Z-score pre 90%
            z95 = 1.9600; // Z-score pre 95%
        }

        double ci90 = z90 * (stdDev / Math.sqrt(count));
        double ci95 = z95 * (stdDev / Math.sqrt(count));

        double lower90 = mean - ci90;
        double upper90 = mean + ci90;
        double lower95 = mean - ci95;
        double upper95 = mean + ci95;

        SwingUtilities.invokeLater(() -> {
            meanLabel.setText("Mean: " + String.format("%.2f", mean));
            medianLabel.setText("Median: " + String.format("%.2f", median));
            varianceLabel.setText("Variance: " + String.format("%.2f", variance));
            stdDevLabel.setText("Standard Deviation: " + String.format("%.2f", stdDev));
            confidenceIntervalLabel95.setText("95% CI: [" + String.format("%.2f", lower95) + ", " + String.format("%.2f", upper95) + "]");
            confidenceIntervalLabel90.setText("90% CI: [" + String.format("%.2f", lower90) + ", " + String.format("%.2f", upper90) + "]");
        });
    }


}
