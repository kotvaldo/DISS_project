package Furniture.Entity;

import IDGenerator.IDGenerator;

import java.util.ArrayList;
import java.util.LinkedList;

public class WorkPlace {
    private final int id;
    private Order order;
    private boolean isBussy;

    public WorkPlace() {
        this.id = IDGenerator.getInstance().getNextWorkplaceId();
        isBussy = false;
        order = null;
    }


    public Order getOrder() {
        return order;

    }

    public void setOrder(Order order) {
        this.order = order;
        this.isBussy = order != null;
    }

    public boolean isBussy() {
        return isBussy;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        WorkPlace workplace = (WorkPlace) obj;
        return id == workplace.id;
    }

    public void setBussy(boolean bussy) {
        if(!bussy) {
            this.order = null;

        }
        isBussy = bussy;
    }

    public int getId() {
        return id;
    }
}
