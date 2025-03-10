package Strategy;

public class CustomStrategy extends Strategy {
    private int suppressorsDeliveryCount;
    private int breakPlatesDeliveryCount;
    private int headlightsDeliveryCount;
    private int supplierAFrequency;
    private int supplierBFrequency;
    private int supplierAOffset;
    private int supplierBOffset;

    public CustomStrategy() {
        super();
    }

    @Override
    public double algorithm(double totalCost) {
        this.clearAll();
        for (int i = 0; i < 30; i++) {
            int curr_day = i + 1;
            this.added_count_1 = 0;
            this.added_count_2 = 0;
            this.added_count_3 = 0;

            setUpSupply(curr_day);

            this.suppressors += added_count_1;
            this.breakPlates += added_count_2;
            this.headlights += added_count_3;

            totalCost += this.suppressors * 4 * this.SUPPRESSORS_PRICE;
            totalCost += this.breakPlates * 4 * this.BREAK_PLATES_PRICE;
            totalCost += this.headlights * 4 * this.HEADLIGHTS_PRICE;

            int current_demand1 = this.demand1Dist.sample();
            int current_demand2 = this.demand2Dist.sample();
            int current_demand3 = this.demand3Dist.sample();

            this.suppressors -= current_demand1;
            this.breakPlates -= current_demand2;
            this.headlights -= current_demand3;

            double penalty = 0.0;
            if (this.suppressors < 0) {
                penalty += Math.abs(this.suppressors) * FINE_FOR_ONE;
                this.suppressors = 0;
            }
            if (this.breakPlates < 0) {
                penalty += Math.abs(this.breakPlates) * FINE_FOR_ONE;
                this.breakPlates = 0;
            }
            if (this.headlights < 0) {
                penalty += Math.abs(this.headlights) * FINE_FOR_ONE;
                this.headlights = 0;
            }
            totalCost += penalty;

            totalCost += this.suppressors * 3 * this.SUPPRESSORS_PRICE;
            totalCost += this.breakPlates * 3 * this.BREAK_PLATES_PRICE;
            totalCost += this.headlights * 3 * this.HEADLIGHTS_PRICE;
        }
        return totalCost;
    }

    @Override
    protected void setUpSupply(int curr_week) {
    }

    public int getSuppressorsDeliveryCount() {
        return suppressorsDeliveryCount;
    }

    public void setSuppressorsDeliveryCount(int suppressorsDeliveryCount) {
        this.suppressorsDeliveryCount = suppressorsDeliveryCount;
    }

    public int getBreakPlatesDeliveryCount() {
        return breakPlatesDeliveryCount;
    }

    public void setBreakPlatesDeliveryCount(int breakPlatesDeliveryCount) {
        this.breakPlatesDeliveryCount = breakPlatesDeliveryCount;
    }

    public int getHeadlightsDeliveryCount() {
        return headlightsDeliveryCount;
    }

    public void setHeadlightsDeliveryCount(int headlightsDeliveryCount) {
        this.headlightsDeliveryCount = headlightsDeliveryCount;
    }

    public int getSupplierAFrequency() {
        return supplierAFrequency;
    }

    public void setSupplierAFrequency(int supplierAFrequency) {
        this.supplierAFrequency = supplierAFrequency;
    }

    public int getSupplierBFrequency() {
        return supplierBFrequency;
    }

    public void setSupplierBFrequency(int supplierBFrequency) {
        this.supplierBFrequency = supplierBFrequency;
    }

    public int getSupplierAOffset() {
        return supplierAOffset;
    }

    public void setSupplierAOffset(int supplierAOffset) {
        this.supplierAOffset = supplierAOffset;
    }

    public int getSupplierBOffset() {
        return supplierBOffset;
    }

    public void setSupplierBOffset(int supplierBOffset) {
        this.supplierBOffset = supplierBOffset;
    }
}
