package EventSimulation;

import SimulationCore.SimulationCore;

import java.util.PriorityQueue;

public abstract class EventSimulationCore extends SimulationCore {
    protected PriorityQueue<Event> events;
    protected double simulationTime;
    protected double endTime;

    protected boolean isSlowMode;
    protected double slowDownSpeed = 1;
    protected boolean isGeneratedFirstSystemEvent;


    protected boolean paused;
    protected int frequency = 5;


    protected EventSimulationCore() {
        events = new PriorityQueue<>();
        state = null;
    }


    @Override
    protected void experiment() {
        while (!events.isEmpty() && !this.isCancelled && simulationTime <= endTime) {

            Event event = events.poll();
            if(event.getTime() < simulationTime) {
                throw new RuntimeException("This cannot happen!");
            }
            this.simulationTime = event.getTime();

            if(isSlowMode) {
                dataHandling();
            }
            if(!isSlowMode && isGeneratedFirstSystemEvent) {
                isGeneratedFirstSystemEvent = false;
            } else if(isSlowMode && !isGeneratedFirstSystemEvent) {
                isGeneratedFirstSystemEvent = true;
                double timeNew = (slowDownSpeed / frequency) + this.simulationTime;
                Event systemEvent = new SystemEvent(timeNew, 3, this);
                this.events.add(systemEvent);
            }



            if(paused) {
                dataHandling();
                if(this.state != null) {
                    this.listener.setState(this.state);
                    this.listener.notifyObservers();
                }
                while(paused) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                    }
                }
            }

        }
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

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
    public int getFrequency() {
        return frequency;
    }
    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }
    public void getEndTime(int endTime) {
        this.endTime = endTime;
    }
}
