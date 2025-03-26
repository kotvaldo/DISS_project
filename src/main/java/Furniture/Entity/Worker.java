package Furniture.Entity;

import IDGenerator.IDGenerator;
import Furniture.Enums.WorkerBussyState;

public class Worker {
    private final int id;
    private boolean currentState;
    private final int type;

    public Worker(int type) {
        this.id = IDGenerator.getInstance().getNextPersonId();
        this.type = type;
        currentState = false;
    }

    public boolean getCurrentState() {
        return currentState;
    }

    public void setCurrentState(boolean bussy) {
        this.currentState = bussy;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }
}
