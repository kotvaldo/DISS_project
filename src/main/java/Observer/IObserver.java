package Observer;

import State.IState;
import Statistics.Statistic;

public interface IObserver {

    public void update(IState state);
}
