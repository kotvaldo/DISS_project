package SimulationCore;

import org.jfree.data.category.DefaultCategoryDataset;

public abstract class SimulationCore {
    double sum = 0.0;
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    public double runSimulation(int repCount) {
        this.sum = 0.0;
        this.dataset.clear();
        for(int i = 0; i < repCount; i++) {
            experiment();
            dataset.addValue(calculateResult(i), "PI", Integer.toString(i));
        }
        return calculateResult(repCount);
    }
    protected abstract void experiment();
    protected abstract double calculateResult(int repCount);

}
