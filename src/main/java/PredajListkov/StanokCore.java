package PredajListkov;

import EventSimulation.EventSimulationCore;
import Generators.Exponential;

public class StanokCore extends EventSimulationCore {

    private Exponential prichod;
    private Exponential casObsluhy;

    public StanokCore() {
        prichod = new Exponential(0.5);
        casObsluhy = new Exponential(0.5);
    }

    @Override
    protected void beforeRunSimulation() {

    }

    @Override
    protected void afterRunSimulation() {

    }

    @Override
    protected void beforeSimulation() {

    }

    @Override
    protected void afterSimulation() {

    }

    @Override
    protected void dataHandling() {

    }

    public Exponential getPrichod() {
        return prichod;
    }

    public void setPrichod(Exponential prichod) {
        this.prichod = prichod;
    }

    public Exponential getCasObsluhy() {
        return casObsluhy;
    }

    public void setCasObsluhy(Exponential casObsluhy) {
        this.casObsluhy = casObsluhy;
    }
}
