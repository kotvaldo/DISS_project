package Generators;

public class Exponential extends BaseGenerator<Double> {
    private final Double avg;

    public Exponential(Double avg) {
        super();
        this.avg = avg;
    }

    public Exponential(Double avg, int seed) {
        super(seed);
        this.avg = avg;
    }

    @Override
    public Double sample() {
        double random = this.baseRandom.nextDouble();
        return -Math.log(1.0 - random) * avg;

    }


}
