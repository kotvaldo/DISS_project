package Furniture.Enums;

public enum WorkerStateValues {
    BUSSY_WORKER(true),
    NON_BUSSY_WORKER(false);

    private final boolean isBussy;

    WorkerStateValues(boolean isBussyWorker) {
        this.isBussy = isBussyWorker;
    }
    public boolean getValue() {
        return isBussy;
    }
}
