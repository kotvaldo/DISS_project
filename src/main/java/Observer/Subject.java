package Observer;

import State.IState;

import java.util.ArrayList;

public class Subject implements ISubject {
    private final ArrayList<IObserver> observers;
    private IState currentState;

    public Subject() {
        observers = new ArrayList<>();
    }

    @Override
    public void notifyObservers() {
        for (IObserver observer : observers) {
            observer.update(currentState);
        }
    }

    @Override
    public void attachObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detachObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void setState(IState state) {
        this.currentState = state;
    }


}
