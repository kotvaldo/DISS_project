package org.example;

import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        int expCount = 100000000;
        Random rand = new Random();
        Random rand2 = new Random();
        int l = 5;
        int d = 10;
        BuffonNeedle buffonNeedle = new BuffonNeedle(rand, rand2, d, l);
        double result = buffonNeedle.runSimulation(expCount);
        System.out.println(result);
    }
}