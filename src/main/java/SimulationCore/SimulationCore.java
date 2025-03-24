package SimulationCore;

import Observer.ISubject;
import Observer.Subject;
import State.IState;

public abstract class SimulationCore {

    protected long repCount;
    protected int actualRepCount;
    protected boolean isCancelled = false;
    protected ISubject listener;
    protected IState state;


    public void runSimulation() {
        beforeRunSimulation();
        for(int i = 0; i < repCount; i++) {
            if(isCancelled) {
                break;
            }
            beforeSimulation();
            experiment();
            afterSimulation();
        }
        afterRunSimulation();
    }
    protected abstract void experiment();
    protected abstract void beforeRunSimulation();
    protected abstract void afterRunSimulation();
    protected abstract void beforeSimulation();
    protected abstract void afterSimulation();

    public void setListener(ISubject listener) {
        this.listener = listener;
    }
    public ISubject getListener() {
         return listener;
    }
    public IState getState() {
        return state;
    }
}
