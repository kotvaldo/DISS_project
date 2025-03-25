package Furniture.Enums;

public enum PriorityValues {
    SYSTEM_EVENT(1),
    BASIC_EVENT(3),
    IMPORTANT_EVENT(2);

    private final int value;

    PriorityValues(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
