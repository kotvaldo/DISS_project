package PredajListkov.Events;

import EventSimulation.Event;
import PredajListkov.Entity.Person;
import SimulationCore.SimulationCore;


public class EventKoniecObsluhy extends Event {
    private Person person;


    protected EventKoniecObsluhy(double time, int priority, SimulationCore simulationCore, Person person) {
        super(time, priority, simulationCore);
        this.person = person;
    }

    @Override
    public void Execute() {

    }
}
