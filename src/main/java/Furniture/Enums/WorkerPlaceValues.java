package Furniture.Enums;

public enum WorkerPlaceValues {
    STORAGE("Storage"),
    WORKPLACE_A("Workplace_A"),
    WORKPLACE_B("Workplace_B"),
    WORKPLACE_C("Workplace_C");

    private final String name;

    WorkerPlaceValues(String name) {
        this.name = name;
    }

    public String getValue() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
