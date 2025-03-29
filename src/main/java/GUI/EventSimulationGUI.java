package GUI;

import Furniture.Entity.WorkPlace;
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
    private final JSlider speedSlider; // ← teraz ako final atribút
    private final DefaultTableModel ordersTableModel;
    private final DefaultTableModel workersTableModel;
    private final DefaultTableModel workPlaceTableModel;
    private final JLabel dayCountLabel;
    private final JLabel replicationCountLabel;
    private final JCheckBox slowDownCheckBox;
    private final JLabel simulationSpeedLabel;
    public EventSimulationGUI() {
        super("Event Simulation");
        this.simulationSpeedLabel = new JLabel("Simulation Speed: ");
        Subject subject = new Subject();
        label = new JLabel("Simulation Time : 0");
        this.replicationCountLabel = new JLabel("Replication Count : 0");
        replicationCountLabel.setVisible(false);
        replicationsInput.setVisible(false);
        replicationLabel.setVisible(false);
        core = new FurnitureEventCore();
        dayCountLabel = new JLabel("Day : 0");
        LabelObserver observer = new LabelObserver(label, dayCountLabel, replicationCountLabel);
        subject.attachObserver(observer);
        core.setListener(subject);

        JButton pauseButton = new JButton("Pause Simulation");
        this.controlPanel.add(pauseButton);
        pauseButton.addActionListener(e -> core.setPaused(!core.isPaused()));
        core.setSlowMode(true);

        String[] orderColumns = {"ID", "Type", "State"};
        ordersTableModel = new DefaultTableModel(orderColumns, 0);
        JTable ordersTable = new JTable(ordersTableModel);
        JScrollPane ordersScroll = new JScrollPane(ordersTable);

        String[] workerColumns = {"ID", "Group", "State", "Order_ID", "WorkPlace_ID"};
        workersTableModel = new DefaultTableModel(workerColumns, 0);
        JTable workersTable = new JTable(workersTableModel);
        JScrollPane workersScroll = new JScrollPane(workersTable);

        String[] workerPlaceColumns = {"ID", "State", "Order_ID"};
        workPlaceTableModel = new DefaultTableModel(workerPlaceColumns, 0);
        JTable workPlaceTable = new JTable(workPlaceTableModel);
        JScrollPane workPlaceSroll = new JScrollPane(workPlaceTable);



        JPanel tablePanel = new JPanel(new GridLayout(1, 3));
        tablePanel.add(ordersScroll);
        tablePanel.add(workersScroll);
        tablePanel.add(workPlaceSroll);


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


        TableObserver tableObserver = new TableObserver(ordersTable, workersTable, workPlaceTable);
        subject.attachObserver(tableObserver);

        slowDownCheckBox = new JCheckBox("Slow Down", true);
        slowDownCheckBox.addActionListener(e -> {
            core.setSlowMode(slowDownCheckBox.isSelected());
            ordersScroll.setVisible(slowDownCheckBox.isSelected());
            workersScroll.setVisible(slowDownCheckBox.isSelected());
            workPlaceSroll.setVisible(slowDownCheckBox.isSelected());
            speedSlider.setVisible(slowDownCheckBox.isSelected());
            simulationSpeedLabel.setVisible(slowDownCheckBox.isSelected());
            replicationCountLabel.setVisible(!slowDownCheckBox.isSelected());
            label.setVisible(slowDownCheckBox.isSelected());
            dayCountLabel.setVisible(slowDownCheckBox.isSelected());
            replicationsInput.setVisible(!slowDownCheckBox.isSelected());
            replicationLabel.setVisible(!slowDownCheckBox.isSelected());

        });
        this.statsPanel.add(slowDownCheckBox);
        this.statsPanel.add(label);
        this.statsPanel.add(dayCountLabel);
        this.inputPanel.add(simulationSpeedLabel);
        this.inputPanel.add(speedSlider);
        this.inputPanel.add(replicationCountLabel);
        this.centerPanel.add(tablePanel);

    }

    @Override
    protected void initializeChart() {

    }


    @Override
    protected void setupCustomInput() {


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
                int replicationCount = 0;
                if(slowDownCheckBox.isSelected()) {
                    replicationCount = 1;
                } else {
                    Integer.parseInt(replicationsInput.getText());
                }
                core.setReplicationCount(replicationCount);
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
