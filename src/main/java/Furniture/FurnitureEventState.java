package Furniture;

import Furniture.Entity.Order;
import Furniture.Entity.Worker;
import State.IState;

import java.util.ArrayList;

public class FurnitureEventState implements IState {
    private double simulationTime = 0;
    private ArrayList<Order> allOrders;
    private ArrayList<Worker> workersA;
    private ArrayList<Worker> workersC;
    private ArrayList<Worker> workersB;
    public FurnitureEventState() {


    }


    public double getSimulationTime() {
        return simulationTime;
    }

    public void setSimulationTime(double simulationTime) {
        this.simulationTime = simulationTime;
    }

    public ArrayList<Order> getAllOrders() {
        return allOrders;
    }

    public void setAllOrders(ArrayList<Order> allOrders) {
        this.allOrders = allOrders;
    }

    public ArrayList<Worker> getWorkersA() {
        return workersA;
    }

    public void setWorkersA(ArrayList<Worker> workersA) {
        this.workersA = workersA;
    }

    public ArrayList<Worker> getWorkersC() {
        return workersC;
    }

    public void setWorkersC(ArrayList<Worker> workersC) {
        this.workersC = workersC;
    }

    public ArrayList<Worker> getWorkersB() {
        return workersB;
    }

    public void setWorkersB(ArrayList<Worker> workersB) {
        this.workersB = workersB;
    }
}
