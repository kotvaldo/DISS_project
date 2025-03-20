package Generators;

import java.util.Random;

public class SeedGenerator {
    private Random random;
    public SeedGenerator() {
        random = new Random();
    }

    public Integer sampleSeed() {
        return random.nextInt();
    }



}
