package GUI;

import Furniture.Enums.SimulationSpeedLimitValues;
import Furniture.FurnitureEventCore;
import Furniture.Observers.LabelObserver;
import Furniture.Observers.TableObserver;
import Observer.Subject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Dictionary;
import java.util.Hashtable;

public class EventSimulationGUI extends AbstractSimulationGUI {
    private JLabel label;
    private final FurnitureEventCore core;
    private EventSimulationWorker worker;
    private JSlider speedSlider;
    private final DefaultTableModel ordersTableModel;
    private final DefaultTableModel workersTableModel;
    private final JLabel dayCountLabel;


    public EventSimulationGUI() {
        super("Event Simulation");
        Subject subject = new Subject();
        core = new FurnitureEventCore();
        dayCountLabel = new JLabel("Day : 0");
        LabelObserver observer = new LabelObserver(label, dayCountLabel);
        subject.attachObserver(observer);
        core.setListener(subject);
        core.setReplicationCount(1);
        JButton pauseButton = new JButton("Pause Simulation");
        this.controlPanel.add(pauseButton);
        pauseButton.addActionListener(e -> {
            core.setPaused(!core.isPaused());
        });


        String[] orderColumns = {"ID", "Type", "State"};
        ordersTableModel = new DefaultTableModel(orderColumns, 0);
        JTable ordersTable = new JTable(ordersTableModel);
        JScrollPane ordersScroll = new JScrollPane(ordersTable);
        String[] workerColumns = {"ID", "Group", "State", "Order_ID"};
        workersTableModel = new DefaultTableModel(workerColumns, 0); // prázdne dáta
        JTable workersTable = new JTable(workersTableModel);
        JScrollPane workersScroll = new JScrollPane(workersTable);

        JPanel tablePanel = new JPanel(new GridLayout(1, 2));
        tablePanel.add(ordersScroll);
        tablePanel.add(workersScroll);

        this.centerPanel.add(tablePanel);
        TableObserver tableObserver = new TableObserver(ordersTable, workersTable);
        subject.attachObserver(tableObserver);
        this.inputPanel.add(dayCountLabel);
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
        label.setHorizontalAlignment(SwingConstants.CENTER);
        this.inputPanel.add(label);

        speedSlider = new JSlider(1, 8, 1);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);
        speedSlider.setMajorTickSpacing(1);
        speedSlider.setMinorTickSpacing(1);
        speedSlider.setPreferredSize(new Dimension(300, 50));


        Dictionary<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(1, new JLabel("1"));
        labelTable.put(2, new JLabel("10"));
        labelTable.put(3, new JLabel("100"));
        labelTable.put(4, new JLabel("500"));
        labelTable.put(5, new JLabel("1K"));
        labelTable.put(6, new JLabel("10K"));
        labelTable.put(7, new JLabel("36K"));
        labelTable.put(8, new JLabel("100K"));
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
                SwingUtilities.invokeLater(() -> {
                    dayCountLabel.setText("Day : 0");
                    label.setText("Simulation Time : 0");
                    ordersTableModel.setRowCount(0);
                    ordersTableModel.fireTableDataChanged();

                    workersTableModel.setRowCount(0);
                    workersTableModel.fireTableDataChanged();
                });
                int replicationCount = Integer.parseInt(replicationsInput.getText());
                core.setReplicationCount(replicationCount);
                core.setSlowMode(replicationCount == 1);
                SimulationSpeedLimitValues speed = SimulationSpeedLimitValues.fromSliderIndex(speedSlider.getValue());
                core.setSlowDownSpeed(speed.getValue());
                worker = new EventSimulationWorker();
                worker.execute();

                startButton.setEnabled(false);
                stopButton.setEnabled(true);

            } catch (Exception e) {

            }
        }
    }

    @Override
    protected void stopSimulation() {
        core.cancel();
        worker.cancel(true);

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
