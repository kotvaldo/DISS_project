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

    protected int frequencyOfUpdate = 21;

    protected boolean paused;


    protected EventSimulationCore() {
        events = new PriorityQueue<>();
        state = null;
    }

    @Override
    protected void experiment() {
        while (!events.isEmpty() && !this.isCancelled && simulationTime < endTime) {
            Event event = events.poll();
           // System.out.println(events.size());
            if (event.getTime() < simulationTime) {
                System.out.println("Simulation time: " + event.getTime());
                System.out.println("Vlákno: " + Thread.currentThread().getName());
                throw new RuntimeException("Toto by sa nemalo stať!");

            }

            this.simulationTime = event.getTime();

            event.Execute();
            if(isSlowMode) {
                dataHandling();
            }
            if (!isSlowMode && isGeneratedFirstSystemEvent) {
                isGeneratedFirstSystemEvent = false;
            } else if (isSlowMode && !isGeneratedFirstSystemEvent) {
                isGeneratedFirstSystemEvent = true;
                double newTime = slowDownSpeed / frequencyOfUpdate;
                newTime += this.simulationTime;
                if(newTime < endTime) {
                    events.add(new SystemEvent(newTime, PriorityValues.SYSTEM_EVENT.getValue(), this));
                }
            }

            if (paused) {
                dataHandling();

                while (paused) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                    }
                }

            }
        }
        isGeneratedFirstSystemEvent = false;
        this.actualRepCount++;
    }


    @Override
    protected abstract void beforeAllReplications();

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
