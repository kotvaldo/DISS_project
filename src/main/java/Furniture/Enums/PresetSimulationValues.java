package Furniture.Enums;

public enum PresetSimulationValues {
    END_OF_SIMULATION(60*60*8.0*249),
    START_SIMULATION_TIME(0.0);
    private final Double value;

    PresetSimulationValues(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }
}
