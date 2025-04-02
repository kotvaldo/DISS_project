package Furniture.Entity;

import Furniture.Enums.OrderStateValues;

public class Order {
    private final int id;
    private final int type;
    private int state;
    private WorkPlace WorkPlace;
    private final double arrivalTime;
    private double endTime;
    private double timeOfWork;


    public Order(int id, int type, double arrivalTime) {
        this.id = id;
        this.type = type;
        this.arrivalTime = arrivalTime;
        this.state = OrderStateValues.ORDER_NEW.getValue();
        this.timeOfWork = 0.0;
        this.endTime = 0.0;
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Order order = (Order) obj;
        return id == order.id;
    }


    public WorkPlace getWorkPlace() {
        return WorkPlace;
    }

    public void setWorkPlace(WorkPlace workPlace) {
        WorkPlace = workPlace;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public double getTimeOfWorkArrivalAndEnd() {
        return endTime - arrivalTime;
    }

    public void addTimeOfWork(double timeOfWork) {
        this.timeOfWork += timeOfWork;
    }

    public double getTimeOfWork() {
        return timeOfWork;
    }

}
