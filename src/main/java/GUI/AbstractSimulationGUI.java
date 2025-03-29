package GUI;

import Observer.Subject;
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
    protected Subject subject;
    protected JPanel customPanel;
    protected JPanel inputPanel;
    protected JPanel statsPanel;
    protected JLabel replicationLabel;
    protected JPanel controlPanel;
    protected JPanel centerPanel;

    protected AbstractSimulationGUI(String title) {
        setTitle(title);
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeChart();
        initializeInputFields();
        initializeButtons();

        this.inputPanel = new JPanel();
        replicationLabel = new JLabel("Replication: ");
        inputPanel.add(replicationLabel);
        inputPanel.add(replicationsInput);
        this.setupCustomInput();

        this.subject = new Subject();
        this.centerPanel = new JPanel();
        controlPanel = new JPanel();
        controlPanel.add(startButton);
        controlPanel.add(stopButton);

        statsPanel = new JPanel();
        statsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));




        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.NORTH);
        topPanel.add(statsPanel, BorderLayout.SOUTH);

        this.customPanel = new JPanel();
        this.customPanel.setVisible(false);
        setupCustomPanel();

        //getContentPane().add(new ChartPanel(chart), BorderLayout.CENTER);
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        getContentPane().add(controlPanel, BorderLayout.SOUTH);
        getContentPane().add(this.customPanel, BorderLayout.EAST);
    }


    protected abstract void initializeChart();

    private void initializeInputFields() {
        replicationsInput = new JTextField("1", 10);
        burnInInput = new JTextField("1000", 10);
        updateFrequencyInput = new JTextField("1000", 10);
    }

    private void initializeButtons() {
        startButton = new JButton("Start Simulation");
        stopButton = new JButton("Stop Simulation");
        stopButton.setEnabled(false);
        startButton.addActionListener(_ -> startSimulation());
        stopButton.addActionListener(_ -> stopSimulation());
    }

    protected abstract void setupCustomInput();
    protected abstract void setupCustomPanel();
    protected abstract void startSimulation();
    protected abstract void stopSimulation();




}
