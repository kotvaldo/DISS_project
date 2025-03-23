package SimulationCore;

import Statistics.Statistic;

public interface UpdateListener {
    void onUpdate(double value);
    void onUpdateData(Statistic statistic);
}
