/*package GUI;

import FlyWeightFactory.FlyWeightStrategyFactory;
import MonteCarlo.MonteCarloCore;
import MonteCarlo.MonteCarloGraphObserver;
import Strategy.CustomStrategy;
import Strategy.IStrategy;
import org.apache.commons.math3.distribution.TDistribution;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;

import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class MonteCarloGUI extends AbstractSimulationGUI {
    private final MonteCarloCore monteCarloCore;
    private MonteCarloWorker worker;
    private final FlyWeightStrategyFactory flyWeightStrategyFactory;
    private JComboBox<String> strategyComboBox;

    private JTextField product1Field, product2Field, product3Field;
    private JTextField supplierAFrequency;
    private JTextField supplierBFrequency;
    private JTextField supplierAOffset;
    private JTextField supplierBOffset;
    private JCheckBox supplierAAllowed;
    private JCheckBox supplierBAllowed;
    private JButton barChartButton;
    DefaultCategoryDataset barChartDataset;
    protected JLabel meanLabel;
    protected JLabel medianLabel;
    protected JLabel varianceLabel;
    protected JLabel stdDevLabel;
    protected JLabel confidenceIntervalLabel95;
    protected JLabel confidenceIntervalLabel90;


    public MonteCarloGUI() {
        super("Monte Carlo Simulation");
        monteCarloCore = new MonteCarloCore();
        flyWeightStrategyFactory = new FlyWeightStrategyFactory();


        barChartDataset = new DefaultCategoryDataset();
        meanLabel = new JLabel("Mean: N/A");
        medianLabel = new JLabel("Median: N/A");
        varianceLabel = new JLabel("Variance: N/A");
        stdDevLabel = new JLabel("Standard Deviation: N/A");
        confidenceIntervalLabel95 = new JLabel("95% CI: N/A");
        confidenceIntervalLabel90 = new JLabel("90% CI: N/A");

        this.statsPanel.add(meanLabel);
        this.statsPanel.add(medianLabel);
        this.statsPanel.add(varianceLabel);
        this.statsPanel.add(stdDevLabel);
        this.statsPanel.add(confidenceIntervalLabel95);
        this.statsPanel.add(confidenceIntervalLabel90);

        MonteCarloGraphObserver monteCarloGraphObserver = new MonteCarloGraphObserver(this.series, this.chart);
        this.subject.attachObserver(monteCarloGraphObserver);
        this.monteCarloCore.setListener(subject);
        inputPanel.add(new JLabel("Burn-in:"));
        inputPanel.add(burnInInput);
        inputPanel.add(new JLabel("Update-Frequency:"));
        inputPanel.add(updateFrequencyInput);
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
        strategyComboBox.addActionListener(_ -> this.customPanel.setVisible("Custom".equals(strategyComboBox.getSelectedItem())));
        inputPanel.add(strategyComboBox);

        barChartButton = new JButton("Show Analysis");
        barChartButton.addActionListener(_ -> {
            barChartDataset.clear();

            ArrayList<Double> costData = new ArrayList<>(monteCarloCore.getDailyCostsCopy());
            ArrayList<Double> fineData = new ArrayList<>(monteCarloCore.getDailyFineCostsCopy());

            DefaultCategoryDataset lineDataset = new DefaultCategoryDataset();

            for (int i = 0; i < costData.size(); i++) {
                double cost = costData.get(i);
                barChartDataset.addValue(cost, "Cost", String.valueOf(i + 1));

                if (i < fineData.size()) {
                    double fine = fineData.get(i);
                    lineDataset.addValue(fine, "Fine", String.valueOf(i + 1));
                }
            }

            showSeparateCharts(barChartDataset, lineDataset);
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

        // Suppressors Delivery Count
        gbc.gridx = 0; gbc.gridy = 0;
        customPanel.add(new JLabel("Suppressors Delivery Count:"), gbc);
        gbc.gridx = 1;
        product1Field = new JTextField(5);
        customPanel.add(product1Field, gbc);

        // Break Plates Delivery Count
        gbc.gridx = 0; gbc.gridy = 1;
        customPanel.add(new JLabel("Break Plates Delivery Count:"), gbc);
        gbc.gridx = 1;
        product2Field = new JTextField(5);
        customPanel.add(product2Field, gbc);

        // Headlights Delivery Count
        gbc.gridx = 0; gbc.gridy = 2;
        customPanel.add(new JLabel("Headlights Delivery Count:"), gbc);
        gbc.gridx = 1;
        product3Field = new JTextField(5);
        customPanel.add(product3Field, gbc);

        // Supplier A
        gbc.gridx = 0; gbc.gridy = 3;
        customPanel.add(new JLabel("Supplier A frequency (Every 'x' week):"), gbc);
        gbc.gridx = 1;
        supplierAFrequency = new JTextField(5);
        supplierAFrequency.setEnabled(false);
        customPanel.add(supplierAFrequency, gbc);


        //Supplier A Allowed
        gbc.gridx = 2;
        supplierAAllowed = new JCheckBox("Allowed");
        customPanel.add(supplierAAllowed, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        customPanel.add(new JLabel("Offset A:"), gbc);
        gbc.gridx = 1;
        supplierAOffset = new JTextField(5);
        supplierAOffset.setEnabled(false);
        customPanel.add(supplierAOffset, gbc);

        // Supplier B
        gbc.gridx = 0; gbc.gridy = 5;
        customPanel.add(new JLabel("Supplier B frequency (Every 'x' week):"), gbc);
        gbc.gridx = 1;
        supplierBFrequency = new JTextField(5);
        supplierBFrequency.setEnabled(false); // Disabled by default
        customPanel.add(supplierBFrequency, gbc);

        //Supplier B Allowed
        gbc.gridx = 2;
        supplierBAllowed = new JCheckBox("Allowed");
        customPanel.add(supplierBAllowed, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        customPanel.add(new JLabel("Offset B:"), gbc);
        gbc.gridx = 1;
        supplierBOffset = new JTextField(5);
        supplierBOffset.setEnabled(false);
        customPanel.add(supplierBOffset, gbc);

        supplierAAllowed.addActionListener(_ -> {
            boolean enabled = supplierAAllowed.isSelected();
            supplierAFrequency.setEnabled(enabled);
            supplierAOffset.setEnabled(enabled);
        });

        supplierBAllowed.addActionListener(_ -> {
            boolean enabled = supplierBAllowed.isSelected();
            supplierBFrequency.setEnabled(enabled);
            supplierBOffset.setEnabled(enabled);
        });

        customPanel.setVisible(false);
    }


    @Override
    protected void startSimulation() {
        if (worker == null || worker.isDone()) {
            chart.clearSubtitles();
            series.clear();
            this.clearStatistics();
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
                    if(supplierAAllowed.isSelected()) {
                        customStrategy.setSupplierAFrequency(Integer.parseInt(supplierAFrequency.getText()));
                        customStrategy.setSupplierAOffset(Integer.parseInt(supplierAOffset.getText()));
                    }
                    if(supplierBAllowed.isSelected()) {
                        customStrategy.setSupplierBFrequency(Integer.parseInt(supplierBFrequency.getText()));
                        customStrategy.setSupplierBOffset(Integer.parseInt(supplierBOffset.getText()));
                    }
                    customStrategy.setSupplier1Allowed(supplierAAllowed.isSelected());
                    customStrategy.setSupplier2Allowed(supplierBAllowed.isSelected());
                    monteCarloCore.setStrategy(customStrategy);
                } else {
                    IStrategy strategy = flyWeightStrategyFactory.getStrategy(strategyString);
                    monteCarloCore.setStrategy(strategy);
                }
                monteCarloCore.setReplicationCount(replications);
                monteCarloCore.setBurnCount(burnIn);
                monteCarloCore.setUpdateFrequency(updateFrequency);


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
            monteCarloCore.cancel();
            this.updateStatisticsFromDataset();


        }
    }

    private void showSeparateCharts(DefaultCategoryDataset barDataset, DefaultCategoryDataset lineDataset) {

        JFreeChart costChart = ChartFactory.createBarChart(
                "Daily Cost Analysis",
                "Days",
                "Cost (€)",
                barDataset
        );

        CategoryPlot costPlot = costChart.getCategoryPlot();
        BarRenderer costRenderer = new BarRenderer();
        costRenderer.setSeriesPaint(0, Color.BLUE);
        costPlot.setRenderer(costRenderer);

        CategoryAxis costXAxis = costPlot.getDomainAxis();
        costXAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
        costXAxis.setTickLabelFont(new Font("SansSerif", Font.BOLD, 9));


        JFreeChart fineChart = ChartFactory.createLineChart(
                "Weekly Fine Analysis",
                "Days",
                "Fine (€)",
                lineDataset
        );

        CategoryPlot finePlot = fineChart.getCategoryPlot();
        LineAndShapeRenderer fineRenderer = new LineAndShapeRenderer();
        fineRenderer.setSeriesPaint(0, Color.RED);
        fineRenderer.setSeriesStroke(0, new BasicStroke(2.0f));
        finePlot.setRenderer(fineRenderer);

        CategoryAxis fineXAxis = finePlot.getDomainAxis();
        fineXAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        JFrame costFrame = new JFrame("Cost Chart");
        costFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        costFrame.setSize(800, 600);
        costFrame.add(new ChartPanel(costChart));
        costFrame.setVisible(true);

        JFrame fineFrame = new JFrame("Fine Chart");
        fineFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fineFrame.setSize(800, 600);
        fineFrame.add(new ChartPanel(fineChart));
        fineFrame.setVisible(true);
    }



    @Override
    protected void clearStatistics() {
        meanLabel.setText("Mean: N/A");
        medianLabel.setText("Median: N/A");
        varianceLabel.setText("Variance: N/A");
        stdDevLabel.setText("Standard Deviation: N/A");
        confidenceIntervalLabel95.setText("95% CI: N/A");
        confidenceIntervalLabel90.setText("90% CI: N/A");
    }
    @Override
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

    private class MonteCarloWorker extends SwingWorker<Void, Void> {
        @Override
        protected Void doInBackground() {
            monteCarloCore.runSimulation();
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
*/