package Furniture.Enums;

public enum SimulationSpeedLimitValues {
    SPEED_1(1.0),
    SPEED_10(10.0),
    SPEED_100(100.0),
    SPEED_500(500.0),
    SPEED_1000(1000.0),
    SPEED_10000(10000.0),
    SPEED_36000(36000.0),
    SPEED_300000(300000.0);


    private final double value;

    SimulationSpeedLimitValues(double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }

    public static SimulationSpeedLimitValues fromSliderIndex(int index) {
        return switch (index) {
            case 2 -> SPEED_10;
            case 3 -> SPEED_100;
            case 4 -> SPEED_500;
            case 5 -> SPEED_1000;
            case 6 -> SPEED_10000;
            case 7 -> SPEED_36000;
            case 8 -> SPEED_300000;
            default -> SPEED_1;
        };
    }
}
