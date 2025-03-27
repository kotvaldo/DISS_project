package PredajListkov;

import State.IState;

public class EventStanokState implements IState {
      private int averagePocetLudi = 0;
      private int averageDlzkaRadu = 0;
      private int averageCasVObchode = 0;

    public EventStanokState() {


    }


    public int getAveragePocetLudi() {
        return averagePocetLudi;
    }

    public void setAveragePocetLudi(int averagePocetLudi) {
        this.averagePocetLudi = averagePocetLudi;
    }

    public int getAverageDlzkaRadu() {
        return averageDlzkaRadu;
    }

    public void setAverageDlzkaRadu(int averageDlzkaRadu) {
        this.averageDlzkaRadu = averageDlzkaRadu;
    }

    public int getAverageCasVObchode() {
        return averageCasVObchode;
    }

    public void setAverageCasVObchode(int averageCasVObchode) {
        this.averageCasVObchode = averageCasVObchode;
    }
}
