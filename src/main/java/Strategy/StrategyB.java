package Strategy;

import Generators.EmpiricContinuous;
import Generators.EmpiricData;
import Generators.UniformContinuous;

import java.util.ArrayList;
import java.util.Arrays;

public class StrategyB extends Strategy {

    public StrategyB() {
        super();

    }
    public double algorithm(double totalCost) {
        for (int i = 0; i < 30; i++) {
            int curr_day = i + 1;
            int added_count_1 = 0, added_count_2 = 0, added_count_3 = 0;

            System.out.println("------ Deň " + curr_day + " ------");

            double decisionValue = decisionMaker.sample();
            double supplierProbability = (curr_day <= 15) ? supplier2Before.sample() : supplier2After.sample();

            System.out.println("Decision Maker hodnota: " + decisionValue);
            System.out.println("Pravdepodobnosť dodávateľa " + ((curr_day <= 15) ? "pred 15. dňom" : "po 15. dni") + ": " + supplierProbability);

            if (decisionValue < supplierProbability) {
                added_count_1 = this.SUPRESSORS_BUY_COUNT;
                added_count_2 = this.BREAK_PLATES_BUY_COUNT;
                added_count_3 = this.HEADLIGHTS_BUY_COUNT;

              System.out.println("Nákup: " + added_count_1 + " suppressors, " + added_count_2 + " break plates, " + added_count_3 + " headlights");
            } else {
              System.out.println("Nákup sa neuskutočnil.");
            }

            this.suppressors += added_count_1;
            this.breakPlates += added_count_2;
            this.headlights += added_count_3;

            System.out.println("Stav skladu po nákupe: " + this.suppressors + " suppressors, " + this.breakPlates + " break plates, " + this.headlights + " headlights");

            totalCost += this.suppressors * 4 * this.SUPPRESSORS_PRICE;
            totalCost += this.breakPlates * 4 * this.BREAK_PLATES_PRICE;
            totalCost += this.headlights * 4 * this.HEADLIGHTS_PRICE;

            int current_demand1 = this.demand1Dist.sample();
            int current_demand2 = this.demand2Dist.sample();
            int current_demand3 = this.demand3Dist.sample();

            System.out.println("Dopyt: " + current_demand1 + " suppressors, " + current_demand2 + " break plates, " + current_demand3 + " headlights");

            this.suppressors -= current_demand1;
            this.breakPlates -= current_demand2;
            this.headlights -= current_demand3;

            double penalty = 0;

            if (this.suppressors < 0) {
                double itemPenalty = Math.abs(this.suppressors) * FINE_FOR_ONE;
                penalty += itemPenalty;
              System.out.println("Pokuta za suppressors: " + itemPenalty);
                this.suppressors = 0;
            }
            if (this.breakPlates < 0) {
                double itemPenalty = Math.abs(this.breakPlates) * FINE_FOR_ONE;
                penalty += itemPenalty;
            System.out.println("Pokuta za break plates: " + itemPenalty);
                this.breakPlates = 0;
            }
            if (this.headlights < 0) {
                double itemPenalty = Math.abs(this.headlights) * FINE_FOR_ONE;
                penalty += itemPenalty;
          System.out.println("Pokuta za headlights: " + itemPenalty);
                this.headlights = 0;
            }

            totalCost += penalty;

            System.out.println("Celkové náklady po dni " + curr_day + ": " + totalCost);
        }
        return totalCost;
    }




}
