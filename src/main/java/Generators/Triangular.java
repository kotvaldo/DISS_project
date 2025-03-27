package Generators;

public class Triangular extends BaseGenerator<Double> {

    private final Double min;
    private final Double max;
    private final Double mod;

    public Triangular(Double min, Double max ,Double mod) {
        super();
        this.min = min;
        this.mod = mod;
        this.max = max;
    }

    public Triangular(Double min, Double max, Double mod, int seed) {
        super(seed);
        this.min = min;
        this.mod = mod;
        this.max = max;
    }

    @Override
    public Double sample() {
        double random = this.baseRandom.nextDouble();
        double fc = (this.mod - min) / (max - min);

        if (random < fc) {
            return min + Math.sqrt(random * (max - min) * (mod - min));
        } else {
            return max - Math.sqrt((1 - random) * (max - min) * (max - mod));
        }
    }


}
