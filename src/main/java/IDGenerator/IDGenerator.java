package IDGenerator;

import java.util.concurrent.atomic.AtomicInteger;

public class IDGenerator {
    private static final IDGenerator instance = new IDGenerator();
    private final AtomicInteger counter = new AtomicInteger();
    private final AtomicInteger counter2 = new AtomicInteger();

    private IDGenerator() {
    }

    public static IDGenerator getInstance() {
        return instance;
    }

    public int getNextOrderId() {
        return counter.incrementAndGet();
    }


    public int getNextPersonId() {
        return counter2.incrementAndGet();
    }
}

