package Furniture.Entity;

import Furniture.Enums.WorkerPlaceValues;
import IDGenerator.IDGenerator;
import Furniture.Enums.WorkerBussyState;

public class Worker {
    private final int id;
    private boolean currentState;
    private final String type;
    private int orderId;
    private String currentWorkPlace;

    public Worker(String type) {
        this.id = IDGenerator.getInstance().getNextPersonId();
        this.type = type;
        currentState = false;
        orderId = -1;
        switch (type) {
            case "A" -> currentWorkPlace = WorkerPlaceValues.WORKPLACE_A.getValue();
            case "B" -> currentWorkPlace = WorkerPlaceValues.WORKPLACE_B.getValue();
            case "C" -> currentWorkPlace = WorkerPlaceValues.WORKPLACE_C.getValue();
        }
    }

    public boolean getCurrentState() {
        return currentState;
    }

    public void setCurrentState(boolean bussy) {
        this.currentState = bussy;
        if(!bussy) {
            orderId = -1;
        }
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
        if(orderId < 0) {
            setCurrentState(false);
        } else {
            this.orderId = orderId;
            this.setCurrentState(true);
        }

    }

    public String getCurrentWorkPlace() {
        return currentWorkPlace;
    }

    public void setCurrentWorkPlace(String currentWorkPlace) {
        this.currentWorkPlace = currentWorkPlace;
    }
}

