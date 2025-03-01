package Generators;

import java.util.Random;

public abstract class BaseGenerator<T> extends Random {
    protected Random baseRandom;
    protected int seed;

    protected BaseGenerator(int seed) {
        this.seed = seed;
        baseRandom = new Random(seed);
    }

    protected BaseGenerator() {
        baseRandom = new Random(nextSeed());
    }
    public abstract T sample();

    protected Integer nextSeed() {
        return SeedGenerator.sampleSeed();
    }
}
