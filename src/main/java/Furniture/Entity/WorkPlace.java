package Furniture.Entity;

import IDGenerator.IDGenerator;

public class WorkPlace {
    private final int id;
    private Order order;
    private boolean isBusy;


    public WorkPlace() {
        this.id = IDGenerator.getInstance().getNextWorkplaceId();
        isBusy = false;
        order = null;
    }


    public Order getOrder() {
        return order;

    }

    public void setOrder(Order order) {
        this.order = order;
        this.isBusy = order != null;
    }

    public boolean isBusy() {
        return isBusy;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        WorkPlace workplace = (WorkPlace) obj;
        return id == workplace.id;
    }

    public void setBusy(boolean busy) {
        if(!busy) {
            this.order = null;

        }
        isBusy = busy;
    }

    public int getId() {
        return id;
    }

}
