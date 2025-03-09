package GUI;

import MonteCarlo.MonteCarlo;
import Strategy.IStrategy;
import javax.swing.*;

public class MonteCarloGUI extends AbstractSimulationGUI {
    private final MonteCarlo monteCarlo;
    private MonteCarloWorker worker;
    private IStrategy strategy;

    public MonteCarloGUI(IStrategy strategy) {
        super("Monte Carlo Simulation");
        this.strategy = strategy;
        monteCarlo = new MonteCarlo();
        monteCarlo.setStrategy(this.strategy);

        monteCarlo.setListener(value -> SwingUtilities.invokeLater(() -> series.add(monteCarlo.getRepCount(), value)));
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

                monteCarlo.setReplicationCount(replications);
                monteCarlo.setBurnCount(burnIn);
                monteCarlo.setUpdateFrequency(updateFrequency);


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
