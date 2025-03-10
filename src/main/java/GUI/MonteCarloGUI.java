package GUI;

import FlyWeightFactory.FlyWeightStrategyFactory;
import MonteCarlo.MonteCarlo;
import org.apache.commons.math3.distribution.TDistribution;
import org.jfree.chart.ChartFactory;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class MonteCarloGUI extends AbstractSimulationGUI {
    private final MonteCarlo monteCarlo;
    private MonteCarloWorker worker;
    private final FlyWeightStrategyFactory flyWeightStrategyFactory;
    private JComboBox<String> strategyComboBox;

    private JTextField product1Field, product2Field, product3Field;
    private JComboBox<Integer> supplierCountComboBox;
    private JTextField[] supplierFrequencyFields;

    public MonteCarloGUI() {
        super("Monte Carlo Simulation");
        monteCarlo = new MonteCarlo();
        flyWeightStrategyFactory = new FlyWeightStrategyFactory();
        monteCarlo.setListener(value -> SwingUtilities.invokeLater(() -> series.add(monteCarlo.getRepCount(), value)));
    }


    @Override
    protected void initializeChart() {
        series = new XYSeries("Simulation Average");
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        chart = ChartFactory.createXYLineChart("Simulation", "Iterations", "Average Value", dataset);
        chart.getXYPlot().getRangeAxis().setFixedAutoRange(150);
        chart.getXYPlot().getDomainAxis().setAutoRange(true);
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
    }

    @Override
    protected void setupCustomPanel() {
        customPanel = new JPanel();
        customPanel.setLayout(new GridLayout(6, 2, 5, 5));
        customPanel.setBorder(BorderFactory.createTitledBorder("Custom Settings"));

        // Section: Number of Products
        customPanel.add(new JLabel("Number of products 1:"));
        product1Field = new JTextField("0", 5);
        customPanel.add(product1Field);

        customPanel.add(new JLabel("Number of products 2:"));
        product2Field = new JTextField("0", 5);
        customPanel.add(product2Field);

        customPanel.add(new JLabel("Number of products 3:"));
        product3Field = new JTextField("0", 5);
        customPanel.add(product3Field);

        customPanel.add(new JLabel("Supplier A frequency (per week):"));
        JTextField supplierAFrequency = new JTextField("1", 5);
        customPanel.add(supplierAFrequency);

        customPanel.add(new JLabel("Supplier B frequency (per week):"));
        JTextField supplierBFrequency = new JTextField("1", 5);
        customPanel.add(supplierBFrequency);

        customPanel.add(new JLabel("Supplier A frequency (per month):"));
        JTextField supplierAFrequencyPerMonth = new JTextField("1", 5);
        customPanel.add(supplierAFrequencyPerMonth);

        customPanel.add(new JLabel("Supplier B frequency (per month):"));
        JTextField supplierBFrequencyPerMonth = new JTextField("1", 5);
        customPanel.add(supplierBFrequencyPerMonth);

        customPanel.setVisible(false);
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
                String strategy = strategyComboBox.getSelectedItem().toString();

                monteCarlo.setReplicationCount(replications);
                monteCarlo.setBurnCount(burnIn);
                monteCarlo.setUpdateFrequency(updateFrequency);
                monteCarlo.setStrategy(flyWeightStrategyFactory.getStrategy(strategy));

                worker = new MonteCarloWorker();
                worker.execute();

                startButton.setEnabled(false);
                stopButton.setEnabled(true);
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
