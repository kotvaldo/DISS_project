package Nabytok.Enums;

public enum OrderStateValues {
    WAITING_IN_QUEUE_1(1),
    PROCESSING_PREPARING(2),
    WAITING_IN_QUEUE_2(3),
    PROCESSING_COLORING(4),
    WAITING_IN_QUEUE_3(5),
    PROCESSING_COMPUTING(6),
    PROCESSING_FITTINGS(7),
    ORDER_DONE(8);

    private final Integer value;

    OrderStateValues(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
