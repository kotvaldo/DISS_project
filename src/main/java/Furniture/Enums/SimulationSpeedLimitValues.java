package Furniture.Enums;

public enum SimulationSpeedLimitValues {
    SPEED_1(1),
    SPEED_10(10),
    SPEED_100(100),
    SPEED_500(500),
    SPEED_1000(1000),
    VIRTUAL(0);

    private final double value;

    SimulationSpeedLimitValues(double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public static SimulationSpeedLimitValues fromSliderIndex(int index) {
        return switch (index) {
            case 1 -> SPEED_1;
            case 2 -> SPEED_10;
            case 3 -> SPEED_100;
            case 4 -> SPEED_500;
            case 5 -> SPEED_1000;
            case 6 -> VIRTUAL;
            default -> SPEED_1;
        };
    }
}
