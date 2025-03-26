package Furniture.Entity;

import IDGenerator.IDGenerator;
import Furniture.Enums.WorkerBussyState;

public class Worker {
    private final int id;
    private boolean currentState;
    private final String type;
    private int orderId;

    public Worker(String type) {
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

    public String getType() {
        return type;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}

