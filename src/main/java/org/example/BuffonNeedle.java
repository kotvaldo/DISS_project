package org.example;

import Generators.UniformContinuous;
import SimulationCore.SimulationCore;

import java.util.Random;

import static java.lang.Math.sin;

public class BuffonNeedle extends SimulationCore {
    int d;
    int l;
    UniformContinuous randY;
    UniformContinuous randAlfa;
    double sum = 0.0;
    public BuffonNeedle(int d, int l) {
        this.d = d;
        this.l = l;
        randY = new UniformContinuous(0, d);
        randAlfa = new UniformContinuous(0, 180);
    }

    @Override
    protected void experiment() {
        double y = randY.sample();
        double alfa = randAlfa.sample();
        double radians = Math.toRadians(alfa);
        double a = (l * sin(radians));
        if((y + a) >= d){
            this.sum++;
        }
    }

    @Override
    protected void beforeRunSimulation() {
        this.sum = 0.0;
    }

    public void setRepCount(int repCount){
        this.repCount = repCount;
    }
    @Override
    protected void afterRunSimulation() {
        double result =  2*l/(d*(sum/repCount));
        System.out.println("PI > " + result);
    }

    @Override
    protected void beforeSimulation() {
    }

    @Override
    protected void afterSimulation() {
    }


}