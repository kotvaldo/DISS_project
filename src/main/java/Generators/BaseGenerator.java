package Generators;

import java.util.Random;

public abstract class BaseGenerator<T extends Number> {
    protected Random baseRandom;
    protected SeedGenerator seedGenerator;
    protected int seed;

    protected BaseGenerator(int seed) {
        seedGenerator = new SeedGenerator();
        this.seed = seed;
        baseRandom = new Random(seed);
    }

    protected BaseGenerator() {
        seedGenerator = new SeedGenerator();
        baseRandom = new Random(nextSeed());
    }
    public abstract T sample();

    protected Integer nextSeed() {
        return this.seedGenerator.sampleSeed();
    }
}
