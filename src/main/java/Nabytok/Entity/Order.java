package Nabytok.Entity;

public class Order {
    private final int id;
    private final int type;
    private int state;

    public Order(int id, int type) {
        this.id = id;
        this.type = type;
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
}
