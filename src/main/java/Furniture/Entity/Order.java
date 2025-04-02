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


    private double queueCuttingEnterTime = -1.0;
    private double queueCuttingLeaveTime = -1.0;

    private double queueColoringEnterTime = -1.0;
    private double queueColoringLeaveTime = -1.0 ;

    private double queueAssemblyEnterTime = -1.0;
    private double queueAssemblyLeaveTime= -1.0;

    private double queueMontageEnterTime= -1.0;
    private double queueMontageLeaveTime= -1.0;



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

        if (this.getWorkPlace() != null) {
            String stateName = OrderStateValues.getNameByValue(state);
                this.getWorkPlace().setActivity(stateName);
        }
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

    public double getQueueCuttingEnterTime() {
        return queueCuttingEnterTime;
    }

    public void setQueueCuttingEnterTime(double queueCuttingEnterTime) {
        this.queueCuttingEnterTime = queueCuttingEnterTime;
    }

    public double getQueueCuttingLeaveTime() {
        return queueCuttingLeaveTime;
    }

    public void setQueueCuttingLeaveTime(double queueCuttingLeaveTime) {
        this.queueCuttingLeaveTime = queueCuttingLeaveTime;
    }

    public double getQueueColoringEnterTime() {
        return queueColoringEnterTime;
    }

    public void setQueueColoringEnterTime(double queueColoringEnterTime) {
        this.queueColoringEnterTime = queueColoringEnterTime;
    }

    public double getQueueColoringLeaveTime() {
        return queueColoringLeaveTime;
    }

    public void setQueueColoringLeaveTime(double queueColoringLeaveTime) {
        this.queueColoringLeaveTime = queueColoringLeaveTime;
    }

    public double getQueueAssemblyEnterTime() {
        return queueAssemblyEnterTime;
    }

    public void setQueueAssemblyEnterTime(double queueAssemblyEnterTime) {
        this.queueAssemblyEnterTime = queueAssemblyEnterTime;
    }

    public double getQueueAssemblyLeaveTime() {
        return queueAssemblyLeaveTime;
    }

    public void setQueueAssemblyLeaveTime(double queueAssemblyLeaveTime) {
        this.queueAssemblyLeaveTime = queueAssemblyLeaveTime;
    }

    public double getQueueMontageEnterTime() {
        return queueMontageEnterTime;
    }

    public void setQueueMontageEnterTime(double queueMontageEnterTime) {
        this.queueMontageEnterTime = queueMontageEnterTime;
    }

    public double getQueueMontageLeaveTime() {
        return queueMontageLeaveTime;
    }

    public void setQueueMontageLeaveTime(double queueMontageLeaveTime) {
        this.queueMontageLeaveTime = queueMontageLeaveTime;
    }
}
