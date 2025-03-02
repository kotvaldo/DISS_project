package Generators;

import Utility.Pair;

public class EmpiricData<T> {
    private Pair<T,T> interval;
    private double probability;
    private int seed = -1;



    public EmpiricData(T min, T max, double probability) {
        this.interval = new Pair<>(min,max);
        this.probability = probability;

    }

    public EmpiricData(T min, T max, double probability, int seed) {
        this.interval = new Pair<>(min,max);
        this.probability = probability;
        this.seed = seed;

    }

    public Pair<T, T> getInterval() {
        return interval;
    }

    public void setInterval(Pair<T, T> interval) {
        this.interval = interval;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }
}
