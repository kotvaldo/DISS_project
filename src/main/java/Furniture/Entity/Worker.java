package Furniture.Entity;

import IDGenerator.IDGenerator;

public class Worker {
    private final int id;
    private boolean isBusy;
    private final String type;
    private Order order;
    private WorkPlace currentWorkPlace;

    private double totalBusyTime;
    private double lastStartBusyTime;

    public Worker(String type) {
        this.id = IDGenerator.getInstance().getNextPersonId();
        this.type = type;
        isBusy = false;
        order = null;
        currentWorkPlace = null;
        totalBusyTime = 0.0;
        lastStartBusyTime = 0.0;
    }

    public boolean getBusy() {
        return isBusy;
    }

    public void setIsBusy(boolean busy, double currentTime) {
        if (this.isBusy && !busy) {
            totalBusyTime += currentTime - lastStartBusyTime;
        }
        if (!this.isBusy && busy) {
            lastStartBusyTime = currentTime;
        }
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
        setIsBusy(orderId != null, currentTime);
    }


    public WorkPlace getCurrentWorkPlace() {
        return currentWorkPlace;
    }

    public void setCurrentWorkPlace(WorkPlace currentWorkPlace) {
        this.currentWorkPlace = currentWorkPlace;
    }

    public double getTotalBusyTime() {
        return totalBusyTime;
    }

    public void setTotalBusyTime(double totalBusyTime) {
        this.totalBusyTime = totalBusyTime;
    }

    public double getLastStartBusyTime() {
        return lastStartBusyTime;
    }

    public void setLastStartBusyTime(double lastStartBusyTime) {
        this.lastStartBusyTime = lastStartBusyTime;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Worker worker = (Worker) obj;
        return id == worker.id;
    }
}
