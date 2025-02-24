package org.example;

import java.util.Random;

import static java.lang.Math.sin;

public class BuffonNeedle extends SimulationCore {
    Random randY;
    Random randAlfa;
    int d;
    int l;
    public BuffonNeedle(Random randY, Random randAlfa, int d, int l) {
        this.randAlfa = randAlfa;
        this.randY = randY;
        this.d = d;
        this.l = l;
    }

    @Override
    protected void experiment() {
        double y = randY.nextDouble() * d;
        double alfa = randAlfa.nextDouble() * 180;
        double radians = Math.toRadians(alfa);
        double a = (l * sin(radians));
        if((y + a) >= d){
            this.sum++;
        }
    }

    @Override
    protected double calculateResult(int repCount) {
        return 2*l/(d*(sum/repCount));
    }

}