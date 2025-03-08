package GUI;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

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

    protected AbstractSimulationGUI(String title) {
        setTitle(title);
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeChart();
        initializeInputFields();
        initializeButtons();
        initializeStatsPanel();


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

        getContentPane().add(new ChartPanel(chart), BorderLayout.CENTER);
        getContentPane().add(inputPanel, BorderLayout.NORTH);
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

    private void initializeStatsPanel() {
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(1, 1));

        meanLabel = new JLabel("Mean: N/A");
        statsPanel.add(meanLabel);

       /* medianLabel = new JLabel("Median: N/A");
        varianceLabel = new JLabel("Variance: N/A");
        stdDevLabel = new JLabel("Standard Deviation: N/A");
        statsPanel.add(medianLabel);
        statsPanel.add(varianceLabel);
        statsPanel.add(stdDevLabel);*/

        getContentPane().add(statsPanel, BorderLayout.NORTH);
    }

    protected abstract void startSimulation();
    protected abstract void stopSimulation();
}
