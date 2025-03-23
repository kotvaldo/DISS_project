package Observer;

import State.IState;

public interface ISubject {
    public void notifyObservers();
    public void attachObserver(IObserver observer);
    public void detachObserver(IObserver observer);
    public void setState(IState state);
}
