package Furniture.Entity;

import Furniture.Enums.OrderStateValues;

public class Order {
    private final int id;
    private final int type;
    private int state;
    private WorkPlace WorkPlace;



    public Order(int id, int type) {
        this.id = id;
        this.type = type;
        this.state = OrderStateValues.ORDER_NEW.getValue();
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
}
