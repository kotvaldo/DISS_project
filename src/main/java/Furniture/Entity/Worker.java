package Furniture.Entity;

import IDGenerator.IDGenerator;
import Statistics.Utilisation;

public class Worker {
    private final int id;
    private boolean isBusy;
    private final String type;
    private Order order;
    private WorkPlace currentWorkPlace;
    private final Utilisation utilisation = new Utilisation();

    public Worker(String type) {
        this.id = IDGenerator.getInstance().getNextPersonId();
        this.type = type;
        isBusy = false;
        order = null;
        currentWorkPlace = null;
    }

    public boolean getBusy() {
        return isBusy;
    }

    public void setIsBusy(boolean busy) {
        this.isBusy = busy;
        if (!busy) {
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

    public void setOrder(Order orderId, double currentTime) {
        this.order = orderId;
        setIsBusy(orderId != null);
    }


    public WorkPlace getCurrentWorkPlace() {
        return currentWorkPlace;
    }

    public void setCurrentWorkPlace(WorkPlace currentWorkPlace) {
        this.currentWorkPlace = currentWorkPlace;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Worker worker = (Worker) obj;
        return id == worker.id;
    }

    public Utilisation getUtilisation() {
        return utilisation;
    }

    public void clear() {
        order = null;
        currentWorkPlace = null;
        isBusy = false;

    }
}
