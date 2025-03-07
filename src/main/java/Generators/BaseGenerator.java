package Generators;

import java.util.Random;

public abstract class BaseGenerator<T extends Number> {
    protected Random baseRandom;
    protected SeedGenerator seedGenerator;
    protected int seed;

    protected BaseGenerator(int seed) {
        this.seed = seed;
        baseRandom = new Random(seed);
        seedGenerator = new SeedGenerator();
    }

    protected BaseGenerator() {
        baseRandom = new Random(nextSeed());
    }
    public abstract T sample();

    protected Integer nextSeed() {
        return SeedGenerator.sampleSeed();
    }
}
