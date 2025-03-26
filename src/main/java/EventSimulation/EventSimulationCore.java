package EventSimulation;

import Furniture.Enums.PresetSimulationValues;
import Furniture.Enums.PriorityValues;
import SimulationCore.SimulationCore;

import java.util.PriorityQueue;

public abstract class EventSimulationCore extends SimulationCore {
    protected PriorityQueue<Event> events;
    protected double simulationTime;
    protected double endTime = PresetSimulationValues.END_OF_SIMULATION.getValue();

    protected boolean isSlowMode;
    protected double slowDownSpeed;
    protected boolean isGeneratedFirstSystemEvent;


    protected boolean paused;


    protected EventSimulationCore() {
        events = new PriorityQueue<>();
        state = null;
        //System.out.println("Simulácia inicializovaná.");
    }

    @Override
    protected void experiment() {
        //System.out.println("Spúšťam experiment...");
        while (!events.isEmpty() && !this.isCancelled && simulationTime <= endTime) {
            //System.out.println(events.size() + " events arrived");
            Event event = events.poll();

            /*if (event.getTime() < simulationTime) {
                throw new RuntimeException("Toto by sa nemalo stať!");
            }*/

            this.simulationTime = event.getTime();
            /*System.out.println("Spracovaný event: " + event.getClass().getSimpleName() +
                    " | Čas: " + simulationTime);*/

            event.Execute();

            //dataHandling();
            //System.out.println(slowDownSpeed);
            if (!isSlowMode && isGeneratedFirstSystemEvent) {
                //System.out.println("Prechádzam z pomalého režimu do rýchleho.");
                isGeneratedFirstSystemEvent = false;
            } else if (isSlowMode && !isGeneratedFirstSystemEvent) {
                //System.out.println("Generujem systémový event pre pomalý režim.");
                isGeneratedFirstSystemEvent = true;
                double timeNew = 1 + this.simulationTime;
                Event systemEvent = new SystemEvent(timeNew, PriorityValues.SYSTEM_EVENT.getValue(), this);
                this.events.add(systemEvent);
            }

            if (paused) {
                //System.out.println("Simulácia pozastavená.");
                dataHandling();

                while (paused) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                       //System.out.println("Simulácia bola prerušená počas pauzy.");
                    }
                }
               // System.out.println("Simulácia obnovená.");
            }
        }
       // System.out.println("Experiment skončil.");
        isGeneratedFirstSystemEvent = false;
    }


    @Override
    protected abstract void beforeRunSimulation();

    @Override
    protected abstract void afterRunSimulation();

    @Override
    protected abstract void beforeSimulation();

    @Override
    protected abstract void afterSimulation();

    protected abstract void dataHandling();

    public double getSimulationTime() {
        return simulationTime;
    }
    public double getEndTime() {
        return endTime;
    }
    public PriorityQueue<Event> getEvents() {
        return events;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public void setSimulationTime(double simulationTime) {
        this.simulationTime = simulationTime;
    }

    public double getSlowDownSpeed() {
        return slowDownSpeed;
    }

    public void setSlowDownSpeed(double slowDownSpeed) {
        this.slowDownSpeed = slowDownSpeed;
    }

    public boolean isSlowMode() {
        return isSlowMode;
    }

    public void setSlowMode(boolean slowMode) {
        isSlowMode = slowMode;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }


    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
    public void getEndTime(int endTime) {
        this.endTime = endTime;
    }
    public void addEvent(Event event) {
        events.add(event);
    }
}
