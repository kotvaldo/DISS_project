package GUI;

import Furniture.Enums.SimulationSpeedLimitValues;
import Furniture.FurnitureEventCore;
import Furniture.Observers.SimulationTimeObserver;
import Observer.Subject;

import javax.swing.*;
import java.util.Hashtable;

public class EventSimulationGUI extends AbstractSimulationGUI {
    private final JComboBox comboBox;
    private JLabel label;
    private SimulationTimeObserver observer;
    private FurnitureEventCore core;
    private EventSimulationWorker worker;
    private Subject subject;
    private JSlider speedSlider;
    private JButton pauseButton;
    private JButton unPauseButton;

    public EventSimulationGUI() {
        super("Event Simulation");
        subject = new Subject();
        core = new FurnitureEventCore();
        observer = new SimulationTimeObserver(label);
        subject.attachObserver(observer);
        core.setListener(subject);
        core.setSlowDownSpeed(1.0);
        core.setReplicationCount(1);
        comboBox = new JComboBox();
        pauseButton = new JButton("Pause Simulation");
        this.controlPanel.add(pauseButton);
        pauseButton.addActionListener(e -> {
            core.setPaused(true);
        });
        unPauseButton = new JButton("Unpause Simulation");
        this.controlPanel.add(unPauseButton);
        unPauseButton.addActionListener(e -> {
            core.setPaused(false);
        });

    }

    @Override
    protected void initializeChart() {

    }

    @Override
    protected void setupCustomChart() {

    }

    @Override
    protected void setupCustomInput() {
        label = new JLabel("Simulation Time : 0");
        this.inputPanel.add(label);

        speedSlider = new JSlider(1, 6, 1);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(1, new JLabel("1"));
        labelTable.put(2, new JLabel("10"));
        labelTable.put(3, new JLabel("100"));
        labelTable.put(4, new JLabel("500"));
        labelTable.put(5, new JLabel("1000"));
        labelTable.put(6, new JLabel("Virtual"));
        speedSlider.setLabelTable(labelTable);

        speedSlider.addChangeListener(e -> {
            SimulationSpeedLimitValues speed = SimulationSpeedLimitValues.fromSliderIndex(speedSlider.getValue());
            core.setSlowDownSpeed(speed.getValue());

        });

        this.inputPanel.add(new JLabel("Simulation speed:"));
        this.inputPanel.add(speedSlider);

    }

    @Override
    protected void setupCustomPanel() {

    }

    @Override
    protected void startSimulation() {
        if (worker == null || worker.isDone()) {
            try {
                int replications = Integer.parseInt(replicationsInput.getText());
                core.setReplicationCount(replications);
                if(replications > 1) {
                    core.setSlowMode(false);
                } else if(replications == 1) {
                    core.setSlowMode(true);
                }

                worker = new EventSimulationWorker();
                worker.execute();

                startButton.setEnabled(false);
                stopButton.setEnabled(true);

            } catch(Exception e) {

            }
        }
    }

    @Override
    protected void stopSimulation() {
        core.cancel();
        worker.cancel(true);
        SwingUtilities.invokeLater(() -> {
            label.setText("Simulation Time : 0");
        });

    }

    @Override
    protected void updateStatisticsFromDataset() {

    }

    @Override
    protected void clearStatistics() {

    }

    private class EventSimulationWorker extends SwingWorker<Void, Void> {
        @Override
        protected Void doInBackground() {
            core.runSimulation();
            return null;
        }

        @Override
        protected void done() {
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
        }
    }
}
