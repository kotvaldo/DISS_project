package Furniture.Entity;

import IDGenerator.IDGenerator;

public class Worker {
    private final int id;
    private boolean currentState;
    private final String type;
    private Order order;
    private WorkPlace currentWorkPlace;

    public Worker(String type) {
        this.id = IDGenerator.getInstance().getNextPersonId();
        this.type = type;
        currentState = false;
        order = null;
        currentWorkPlace = null;
    }

    public boolean getCurrentState() {
        return currentState;
    }

    public void setCurrentState(boolean bussy) {
        this.currentState = bussy;
        if(!bussy) {
           order = null;
        }
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Order getOrderId() {
        return order;
    }

    public void setOrder(Order orderId) {
        if(orderId == null) {
            setCurrentState(false);
        } else {
            this.order = orderId;
            this.setCurrentState(true);
        }

    }

    public WorkPlace getCurrentWorkPlace() {
        return currentWorkPlace;
    }

    public void setCurrentWorkPlace(WorkPlace currentWorkPlace) {
        this.currentWorkPlace = currentWorkPlace;
    }
}

