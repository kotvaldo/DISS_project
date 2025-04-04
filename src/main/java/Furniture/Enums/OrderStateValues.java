package Furniture.Enums;
public enum OrderStateValues {
    ORDER_NEW(0),
    WAITING_IN_QUEUE_1(1),
    PROCESSING_CUTTING(2),
    WAITING_IN_QUEUE_2(3),
    PROCESSING_COLORING(4),
    WAITING_IN_QUEUE_3(5),
    PROCESSING_ASSEMBLY(6),
    PROCESSING_MONTAGE(7),
    WAITING_IN_QUEUE_4(8),
    ORDER_DONE(9),
    ACCESSING_NEW_ORDER(10);

    private final Integer value;

    OrderStateValues(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static String getNameByValue(int value) {
        for (OrderStateValues state : values()) {
            if (state.getValue() == value) {
                return state.name();
            }
        }
        return "UNKNOWN_STATE";
    }

    @Override
    public String toString() {
        return name();
    }
}
