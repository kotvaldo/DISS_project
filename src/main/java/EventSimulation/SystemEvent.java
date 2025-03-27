package EventSimulation;

import SimulationCore.SimulationCore;

public class SystemEvent extends Event {

    public SystemEvent(double time, int priority, SimulationCore simulationCore) {
        super(time, priority, simulationCore);
    }

    @Override
    public void Execute() {
        EventSimulationCore simCore = (EventSimulationCore) simulationCore;
        if (simCore.slowDownSpeed != 0.0) {
            long sleepTime = (long) (1000 / simCore.frequencyOfUpdate);
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException _) {

            }
        }

        if (simCore.isSlowMode) {

            double newTime = simCore.slowDownSpeed / simCore.frequencyOfUpdate;
            newTime += this.time;
            if(this.time <= simCore.endTime) {
                simCore.events.add(new SystemEvent(newTime, this.priority, simulationCore));
            }

        }

        if (simCore.getState() != null) {
            simCore.dataHandling();
        }

    }
}
