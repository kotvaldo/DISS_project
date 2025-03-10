package GUI;

import FlyWeightFactory.FlyWeightStrategyFactory;
import MonteCarlo.MonteCarlo;
import Strategy.CustomStrategy;
import Strategy.IStrategy;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class MonteCarloGUI extends AbstractSimulationGUI {
    private final MonteCarlo monteCarlo;
    private MonteCarloWorker worker;
    private final FlyWeightStrategyFactory flyWeightStrategyFactory;
    private JComboBox<String> strategyComboBox;

    private JTextField product1Field, product2Field, product3Field;
    private JTextField supplierAFrequency;
    private JTextField supplierBFrequency;
    private JTextField supplierAOffset;
    private JTextField supplierBOffset;
    private JButton barChartButton;
    private final JFreeChart barChart;
    DefaultCategoryDataset barChartDataset;


    public MonteCarloGUI() {
        super("Monte Carlo Simulation");
        monteCarlo = new MonteCarlo();
        flyWeightStrategyFactory = new FlyWeightStrategyFactory();
        monteCarlo.setListener(value ->
                SwingUtilities.invokeLater(() -> {
                    series.add(monteCarlo.getRepCount(), value);
                    updateChartRange();
                })
        );
        barChartDataset = new DefaultCategoryDataset();
        barChart = ChartFactory.createBarChart(
                "Weekly Cost Graph",
                "Week",
                "Cost",
                barChartDataset
        );
    }


    @Override
    protected void initializeChart() {
        series = new XYSeries("Simulation Average");
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        chart = ChartFactory.createXYLineChart("Simulation", "Iterations", "Average Value", dataset);
        chart.getXYPlot().getDomainAxis().setAutoRange(true);
        chart.getXYPlot().getRangeAxis().setFixedAutoRange(500);
    }

    @Override
    protected void setupCustomChart() {

    }

    @Override
    protected void setupCustomInput() {
        this.strategyComboBox = new JComboBox<>();
        strategyComboBox.addItem("StrategyA");
        strategyComboBox.addItem("StrategyB");
        strategyComboBox.addItem("StrategyC");
        strategyComboBox.addItem("StrategyD");
        strategyComboBox.addItem("Custom");
        strategyComboBox.addActionListener(e -> {
            this.customPanel.setVisible("Custom".equals(strategyComboBox.getSelectedItem()));
        });
        inputPanel.add(strategyComboBox);

        barChartButton = new JButton("Show Bar Chart");
        barChartButton.addActionListener(e -> {
            barChartDataset.clear();
            ArrayList<Double> costData = new ArrayList<>(monteCarlo.getWeeklyCostsCopy());

            for (int i = 0; i < costData.size(); i++) {
                double cost = costData.get(i);
                barChartDataset.addValue(cost, "Cost", String.valueOf(i + 1));
            }

            showBarChart();
        });
        barChartButton.setEnabled(false);

        inputPanel.add(barChartButton);

    }


    @Override
    protected void setupCustomPanel() {
        customPanel = new JPanel();
        customPanel.setLayout(new GridBagLayout());
        customPanel.setBorder(BorderFactory.createTitledBorder("Custom Settings"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        customPanel.add(new JLabel("Suppressors Delivery Count:"), gbc);
        gbc.gridx = 1;
        product1Field = new JTextField(5);
        customPanel.add(product1Field, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        customPanel.add(new JLabel("Break Plates Delivery Count:"), gbc);
        gbc.gridx = 1;
        product2Field = new JTextField(5);
        customPanel.add(product2Field, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        customPanel.add(new JLabel("Headlights Delivery Count:"), gbc);
        gbc.gridx = 1;
        product3Field = new JTextField(5);
        customPanel.add(product3Field, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        customPanel.add(new JLabel("Supplier A frequency (Every 'x' week):"), gbc);
        gbc.gridx = 1;
        supplierAFrequency = new JTextField(5);
        customPanel.add(supplierAFrequency, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        customPanel.add(new JLabel("Offset A:"), gbc);
        gbc.gridx = 1;
        supplierAOffset = new JTextField(5);
        customPanel.add(supplierAOffset, gbc);

        // Section: Supplier B
        gbc.gridx = 0; gbc.gridy = 5;
        customPanel.add(new JLabel("Supplier B frequency (Every 'x' week):"), gbc);
        gbc.gridx = 1;
        supplierBFrequency = new JTextField(5);
        customPanel.add(supplierBFrequency, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        customPanel.add(new JLabel("Offset B:"), gbc);
        gbc.gridx = 1;
        supplierBOffset = new JTextField(5);
        customPanel.add(supplierBOffset, gbc);

        customPanel.setVisible(false);
    }


    @Override
    protected void startSimulation() {
        if (worker == null || worker.isDone()) {
            chart.clearSubtitles();
            series.clear();
            this.clearStatistic();
            try {
                int replications = Integer.parseInt(replicationsInput.getText());
                int burnIn = Integer.parseInt(burnInInput.getText());
                int updateFrequency = Integer.parseInt(updateFrequencyInput.getText());
                String strategyString = Objects.requireNonNull(strategyComboBox.getSelectedItem()).toString();
                if("Custom".equals(strategyString))
                {
                    CustomStrategy customStrategy = (CustomStrategy) flyWeightStrategyFactory.getStrategy(strategyString);
                    customStrategy.setSuppressorsDeliveryCount(Integer.parseInt(product1Field.getText()));
                    customStrategy.setBreakPlatesDeliveryCount(Integer.parseInt(product2Field.getText()));
                    customStrategy.setHeadlightsDeliveryCount(Integer.parseInt(product3Field.getText()));
                    customStrategy.setSupplierAFrequency(Integer.parseInt(supplierAFrequency.getText()));
                    customStrategy.setSupplierBFrequency(Integer.parseInt(supplierBFrequency.getText()));
                    customStrategy.setSupplierAOffset(Integer.parseInt(supplierAOffset.getText()));
                    customStrategy.setSupplierBOffset(Integer.parseInt(supplierBOffset.getText()));
                    monteCarlo.setStrategy(customStrategy);
                } else {
                    IStrategy strategy = flyWeightStrategyFactory.getStrategy(strategyString);
                    monteCarlo.setStrategy(strategy);
                }
                monteCarlo.setReplicationCount(replications);
                monteCarlo.setBurnCount(burnIn);
                monteCarlo.setUpdateFrequency(updateFrequency);


                worker = new MonteCarloWorker();
                worker.execute();

                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                barChartButton.setEnabled(true);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    protected void stopSimulation() {
        if (worker != null && !worker.isDone()) {
            worker.cancel(true);
            monteCarlo.cancel();
            this.updateStatisticsFromDataset();


        }
    }

    private void showBarChart() {
        int width = 900;
        int height = 400;

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(width, height));

        JFrame chartFrame = new JFrame("Bar Chart");
        chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chartFrame.setSize(width, height);
        chartFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        chartFrame.setLocationRelativeTo(null);
        chartFrame.getContentPane().add(chartPanel);
        chartFrame.setVisible(true);

        CategoryAxis xAxis = barChart.getCategoryPlot().getDomainAxis();
        xAxis.setTickLabelFont(new Font("SansSerif", Font.BOLD, 9));
        xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
    }

    private void updateChartRange() {
        XYPlot plot = chart.getXYPlot();
        ValueAxis rangeAxis = plot.getRangeAxis();

        double minY = series.getMinY();
        double maxY = series.getMaxY();

        rangeAxis.setRange(minY - 10, maxY + 10);
    }


    private class MonteCarloWorker extends SwingWorker<Void, Void> {
        @Override
        protected Void doInBackground() {
            monteCarlo.runSimulation();
            return null;
        }

        @Override
        protected void done() {
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            updateStatisticsFromDataset();
        }
    }
}
