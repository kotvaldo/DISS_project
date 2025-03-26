package Furniture;

import Furniture.Entity.Worker;
import State.IState;

import java.util.ArrayList;

public class FurnitureEventState implements IState {
    private double simulationTime = 0;

    public FurnitureEventState() {


    }


    public double getSimulationTime() {
        return simulationTime;
    }

    public void setSimulationTime(double simulationTime) {
        this.simulationTime = simulationTime;
    }
}
