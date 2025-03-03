package Generators;

public class UniformContinuous extends BaseGenerator<Double> {

    private final double min;
    private final double max;

    public UniformContinuous(double min, double max, int seed) {
        super(seed);
        this.min = min;
        this.max = max;
    }

    public UniformContinuous(double min, double max) {
        super();
        this.min = min;
        this.max = max;
    }

    @Override
    public Double sample() {
        return min + (max - min) * this.baseRandom.nextDouble();
    }
}
