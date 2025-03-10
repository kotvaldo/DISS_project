package GUI;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;

import javax.swing.*;
import java.awt.*;

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

    protected void clearStatistic() {
        meanLabel.setText("Mean: N/A");
        medianLabel.setText("Median: N/A");
        varianceLabel.setText("Variance: N/A");
        stdDevLabel.setText("Standard Deviation: N/A");
        confidenceIntervalLabel95.setText("95% CI: N/A");
        confidenceIntervalLabel90.setText("90% CI: N/A");
    }

    protected abstract void startSimulation();
    protected abstract void stopSimulation();
}
