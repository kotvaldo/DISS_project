package Furniture.Enums;

public enum WorkerPlaceValues {
    STORAGE("Storage"),
    WORKPLACE_A("A"),
    WORKPLACE_B("B"),
    WORKPLACE_C("C");

    private final String name;

    WorkerPlaceValues(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
