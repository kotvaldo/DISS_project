package Generators;

public class UniformDiscrete extends BaseGenerator<Integer> {
    private final int min;
    private final int max;

    public UniformDiscrete(int min, int max, int seed) {
        super(seed);
        this.min = min;
        this.max = max;


    }

    public UniformDiscrete(int min, int max) {
        super();
        this.min = min;
        this.max = max;
    }


    @Override
    public Integer sample() {
        return this.baseRandom.nextInt(min, max);
    }
}
