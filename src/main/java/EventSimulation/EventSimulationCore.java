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
        //System.out.println("Simulácia inicializovaná.");
    }

    @Override
    protected void experiment() {
        //System.out.println("Spúšťam experiment...");
        //System.out.println("Start Size: " + events.size());
        while (!events.isEmpty() && !this.isCancelled && simulationTime < endTime) {
            //System.out.println(events.size() + " events arrived");
            Event event = events.poll();

            if (event.getTime() < simulationTime) {
                throw new RuntimeException("Toto by sa nemalo stať!");
            }

            this.simulationTime = event.getTime();
            /*System.out.println("Spracovaný event: " + event.getClass().getSimpleName() +
                    " | Čas: " + simulationTime);*/

            event.Execute();
            if(isSlowMode) {
                dataHandling();
            }
            //System.out.println(events.size() + " events arrived");
            //dataHandling();
            //System.out.println(slowDownSpeed);
            if (!isSlowMode && isGeneratedFirstSystemEvent) {
                //System.out.println("Prechádzam z pomalého režimu do rýchleho.");
                isGeneratedFirstSystemEvent = false;
            } else if (isSlowMode && !isGeneratedFirstSystemEvent) {
                //System.out.println("Generujem systémový event pre pomalý režim.");
                isGeneratedFirstSystemEvent = true;
                double newTime = slowDownSpeed / frequencyOfUpdate;
                newTime += this.simulationTime;
                if(newTime < endTime) {
                    events.add(new SystemEvent(newTime, PriorityValues.SYSTEM_EVENT.getValue(), this));
                }
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
        this.actualRepCount++;
        /*System.out.println("RepCount " + actualRepCount);
        System.out.println("Event Size: " + events.size());
        System.out.println("SlowMode : " + isSlowMode);*/
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
