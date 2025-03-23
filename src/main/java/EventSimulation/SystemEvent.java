package EventSimulation;

import SimulationCore.SimulationCore;

public class SystemEvent extends Event {

    public SystemEvent(double time, int priority, SimulationCore simulationCore) {
        super(time, priority, simulationCore);
    }

    @Override
    public void Execute() {


    }
}
