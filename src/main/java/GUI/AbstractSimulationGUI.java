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
    protected JLabel burnLabel;
    protected JPanel controlPanel;
    protected JPanel centerPanel;

    protected AbstractSimulationGUI(String title) {
        setTitle(title);
        setSize(1500, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.centerPanel = new JPanel();
        initializeChart();
        initializeInputFields();
        initializeButtons();
        this.inputPanel = new JPanel();
        replicationLabel = new JLabel("Replication: ");
        burnLabel = new JLabel("Burn rep: ");
        burnLabel.setVisible(false);
        burnInInput.setVisible(false);
        inputPanel.add(replicationLabel);
        inputPanel.add(replicationsInput);
        inputPanel.add(burnLabel);
        inputPanel.add(burnInInput);

        //inputPanel.add(updateFrequencyInput);
        this.setupCustomInput();

        this.subject = new Subject();
        controlPanel = new JPanel();
        controlPanel.add(startButton);
        controlPanel.add(stopButton);

        statsPanel = new JPanel();
        statsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));



        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.NORTH);
        topPanel.add(statsPanel, BorderLayout.SOUTH);


        setupCustomPanel();

        //getContentPane().add(new ChartPanel(chart), BorderLayout.CENTER);
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(centerPanel, BorderLayout.CENTER);
        getContentPane().add(controlPanel, BorderLayout.SOUTH);
        getContentPane().add(this.customPanel, BorderLayout.EAST);
    }


    protected abstract void initializeChart();

    private void initializeInputFields() {
        replicationsInput = new JTextField("1000", 10);
        burnInInput = new JTextField("20", 10);
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
