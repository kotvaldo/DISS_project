package org.example;

import SimulationCore.SimulationCore;

import java.util.Random;

import static java.lang.Math.sin;

public class BuffonNeedle extends SimulationCore {
    Random randY;
    Random randAlfa;
    int d;
    int l;
    double sum = 0.0;
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
    protected void beforeSimulation() {
        this.sum = 0.0;
    }

    @Override
    protected void afterSimulation() {

    }


}