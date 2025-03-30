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

    public Order getOrder() {
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Worker worker = (Worker) obj;
        return id == worker.id;
    }

    public WorkPlace getCurrentWorkPlace() {
        return currentWorkPlace;
    }

    public void setCurrentWorkPlace(WorkPlace currentWorkPlace) {
        this.currentWorkPlace = currentWorkPlace;
       /* if(currentWorkPlace != null) {
            if(type.equals("A")) {
                currentWorkPlace.setActivity("Cutting");
            } else if (type.equals("B")) {
                currentWorkPlace.setActivity("Assembly");
            }
        }
*/
    }
}

