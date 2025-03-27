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

    public static String getNameByValue(boolean value) {
        for (WorkerBussyState state : WorkerBussyState.values()) {
            if (state.getValue() == value) {
                return state.name();
            }
        }
        return "UNKNOWN";
    }
}

