package Furniture.Enums;

public enum PresetSimulationValues {
    END_OF_SIMULATION(1000000.0),
    START_TIME_WORKING(60.0*60*6),
    END_TIME_WORKING(60.0*60*14),
    START_SIMULATION_TIME(0.0);
    private final Double value;

    PresetSimulationValues(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }
}
