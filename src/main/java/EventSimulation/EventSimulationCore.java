package EventSimulation;

import SimulationCore.SimulationCore;

import java.util.PriorityQueue;

public abstract class EventSimulationCore extends SimulationCore {
    protected PriorityQueue<Event> events;
    protected double simulationTime;
    protected double endTime;

    protected boolean isFastMode;
    protected double slowDownSpeed;
    private boolean generateSystemEvent;

    protected boolean paused;


    protected EventSimulationCore() {
        events = new PriorityQueue<>();
        state = null;
    }


    @Override
    protected void experiment() {
        while (!events.isEmpty() && !this.isCancelled) {
            Event event = events.poll();
            if(event.getTime() < simulationTime) {
                throw new RuntimeException("This cannot happen!");
            }
            this.simulationTime = event.getTime();

            if(isFastMode) {
                dataHandling();
            }
            if(!isFastMode && generateSystemEvent) {
                generateSystemEvent = false;
            } else if(isFastMode && !generateSystemEvent) {
                generateSystemEvent = true;
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
        generateSystemEvent = false;
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

    public boolean isFastMode() {
        return isFastMode;
    }

    public void setFastMode(boolean fastMode) {
        isFastMode = fastMode;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
