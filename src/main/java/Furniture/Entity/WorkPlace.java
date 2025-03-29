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
        if(order == null) {
            isBussy = false;
        } else {
            isBussy = true;
        }
        this.order = order;
    }

    public boolean isBussy() {
        return isBussy;
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
