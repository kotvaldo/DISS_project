package Generators;

import java.util.Random;

public class SeedGenerator {
    private static final Random random = new Random();
    private SeedGenerator() {}

    public static Integer sampleSeed() {
        random.setSeed(System.currentTimeMillis());
        return random.nextInt();
    }

}
