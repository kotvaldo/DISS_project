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
    protected void setUpSupply(int curr_week) {
        if(supplierAFrequency > 0) {
            if (curr_week % supplierAFrequency == 0) {
                if(curr_week <= supplierAOffset) {
                    if (decisionMaker.sample() < supplier1Before.sample()) {
                        added_count_1 += this.suppressorsDeliveryCount;
                        added_count_2 = this.breakPlatesDeliveryCount;
                        added_count_3 = this.headlightsDeliveryCount;
                    }
                } else {
                    if (decisionMaker.sample() < supplier1After.sample()) {
                        added_count_1 += this.suppressorsDeliveryCount;
                        added_count_2 = this.breakPlatesDeliveryCount;
                        added_count_3 = this.headlightsDeliveryCount;
                    }
                }

            }

        }
        if(supplierBFrequency > 0) {
            if (curr_week % supplierBFrequency == 0) {
                if(curr_week <= supplierBOffset) {
                    if (decisionMaker.sample() < supplier2Before.sample()) {
                        added_count_1 += this.suppressorsDeliveryCount;
                        added_count_2 = this.breakPlatesDeliveryCount;
                        added_count_3 = this.headlightsDeliveryCount;
                    }
                } else {
                    if (decisionMaker.sample() < supplier2After.sample()) {
                        added_count_1 += this.suppressorsDeliveryCount;
                        added_count_2 = this.breakPlatesDeliveryCount;
                        added_count_3 = this.headlightsDeliveryCount;
                    }
                }

            }
        }

    }

    public void setSuppressorsDeliveryCount(int suppressorsDeliveryCount) {
        this.suppressorsDeliveryCount = suppressorsDeliveryCount;
    }


    public void setBreakPlatesDeliveryCount(int breakPlatesDeliveryCount) {
        this.breakPlatesDeliveryCount = breakPlatesDeliveryCount;
    }


    public void setHeadlightsDeliveryCount(int headlightsDeliveryCount) {
        this.headlightsDeliveryCount = headlightsDeliveryCount;
    }


    public void setSupplierAFrequency(int supplierAFrequency) {
        this.supplierAFrequency = supplierAFrequency;
    }


    public void setSupplierBFrequency(int supplierBFrequency) {
        this.supplierBFrequency = supplierBFrequency;
    }

    public void setSupplierAOffset(int supplierAOffset) {
        this.supplierAOffset = supplierAOffset;
    }


    public void setSupplierBOffset(int supplierBOffset) {
        this.supplierBOffset = supplierBOffset;
    }
}
