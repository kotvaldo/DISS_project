package Observer;

import State.IState;

public interface IObserver {

    void update(IState state);
}
