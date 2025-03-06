package org.example;


import Generators.EmpiricContinuous;
import Generators.EmpiricData;
import Generators.EmpiricDiscrete;
import Generators.SeedGenerator;
import SimulationCore.SimulationManager;

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
        ArrayList<EmpiricData<Integer>> list = new ArrayList<>();
        list.add(new EmpiricData<>(1, 3, 0.2));
        list.add(new EmpiricData<>(3, 5, 0.2));
        list.add(new EmpiricData<>(5, 7, 0.6));

        EmpiricDiscrete empiricDiscrete = new EmpiricDiscrete(list);
        int count_1 = 0;
        int count_2 = 0;
        int count_3 = 0;
        for (int i = 0; i < 100000; i++) {
            int value = empiricDiscrete.sample();

            if(value >= 1 && value < 3) count_1++;
            if(value >= 3 && value < 5) count_2++;
            if(value >= 5 && value < 7) count_3++;


        }

        System.out.println((double)count_1/100000);
        System.out.println((double)count_2/100000);
        System.out.println((double)count_3/100000);

        SimulationManager simulationManager = new SimulationManager(1);

        BuffonNeedle buffonNeedle = new BuffonNeedle(10,5);
        buffonNeedle.setRepCount(10000000);
        simulationManager.startSimulation(buffonNeedle);
    }
}



