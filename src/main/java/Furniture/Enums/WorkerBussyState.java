package Furniture.Enums;

public enum WorkerBussyState {
    BUSSY_WORKER(true),
    NON_BUSSY_WORKER(false);

    private final boolean isBussy;

    WorkerBussyState(boolean isBussyWorker) {
        this.isBussy = isBussyWorker;
    }
    public boolean getValue() {
        return isBussy;
    }
}
