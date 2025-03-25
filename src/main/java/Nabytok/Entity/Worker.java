package Nabytok.Entity;

public class Worker {
    private final int id;
    private int currentState;
    private final int type;

    public Worker(int id, int type) {
        this.id = id;
        this.type = type;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }
}
