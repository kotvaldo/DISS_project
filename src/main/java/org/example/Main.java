package org.example;


import Generators.EmpiricContinuous;
import Generators.EmpiricData;
import Generators.EmpiricDiscrete;
import Generators.SeedGenerator;
import SimulationCore.MonteCarlo;
import SimulationCore.SimulationManager;
import Strategy.StrategyA;
import Strategy.StrategyB;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class Main {
    public static void main(String[] args) {


        /*ArrayList<EmpiricData<Double>> list = new ArrayList<>();
        EmpiricData<Double> priklad = new EmpiricData<>(0.1, 0.2, 0.2);
        EmpiricData<Double> priklad2 = new EmpiricData<>(0.2, 0.4, 0.2);
        EmpiricData<Double> priklad3 = new EmpiricData<>(0.4, 1000.0, 0.6);
        list.add(priklad);
        list.add(priklad2);
        list.add(priklad3);

        EmpiricContinuous empiricContinuous = new EmpiricContinuous(list,0);
        EmpiricContinuous empiricContinuous2 = new EmpiricContinuous(list,0);

        System.out.println(empiricContinuous.sample());
        System.out.println(empiricContinuous2.sample());
        System.out.println(SeedGenerator.sampleSeed());
        System.out.println(SeedGenerator.sampleSeed());
        System.out.println(SeedGenerator.sampleSeed());

        ArrayList<EmpiricData<Integer>> list2 = new ArrayList<>();
        list2.add(new EmpiricData<>(1, 2, 0.3));
        list2.add(new EmpiricData<>(2, 3, 0.3));
        list2.add(new EmpiricData<>(4, 5, 0.3)); // Sum = 0.9 (invalid)
        EmpiricDiscrete empiricDiscrete = new EmpiricDiscrete(list2,0);
        empiricDiscrete.sample();

        System.out.println(empiricDiscrete.sample());*/


        SimulationManager simulationManager = new SimulationManager(1);
        StrategyB strategyB = new StrategyB();
        StrategyA strategyA = new StrategyA();
        MonteCarlo monteCarlo = new MonteCarlo();
        monteCarlo.setStrategy(strategyB);
        monteCarlo.setReplicationCount(100000);
        //simulationManager.startSimulation(buffonNeedle);
       /* BuffonNeedle buffonNeedle1 = new BuffonNeedle(10,5);
        buffonNeedle1.setRepCount(100000000);*/
        simulationManager.startSimulation(monteCarlo);
        simulationManager.stopAllSimulations();

    }
}



