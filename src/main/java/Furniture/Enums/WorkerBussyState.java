package Furniture.Enums;

public enum WorkerBussyState {
    BUSY_WORKER(true),
    NON_BUSY_WORKER(false);

    private final boolean isBusy;

    WorkerBussyState(boolean isBusyWorker) {
        this.isBusy = isBusyWorker;
    }

    public boolean getValue() {
        return isBusy;
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

