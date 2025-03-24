package EventSimulation;

import SimulationCore.SimulationCore;

public class SystemEvent extends Event {

    public SystemEvent(double time, int priority, SimulationCore simulationCore) {
        super(time, priority, simulationCore);
    }

    @Override
    public void Execute() {
        EventSimulationCore simCore = (EventSimulationCore) simulationCore;
        long sleepTime = (long) 997 / simCore.frequency;

        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException _) {

        }

        if(simCore.isSlowMode) {
            this.time = (simCore.slowDownSpeed / simCore.frequency) + simCore.simulationTime;
            simCore.events.add(this);
        }

        if(simCore.getState() != null) {
            simCore.dataHandling();
        }

    }
}
