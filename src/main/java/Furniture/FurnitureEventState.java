package Furniture;

import Furniture.Entity.Order;
import Furniture.Entity.WorkPlace;
import Furniture.Entity.Worker;
import State.IState;

import java.util.ArrayList;

public class FurnitureEventState implements IState {
    private double simulationTime = 0;
    private ArrayList<Order> allOrders;
    private ArrayList<Worker> workersA;
    private ArrayList<Worker> workersC;
    private ArrayList<Worker> workersB;
    private ArrayList<WorkPlace> workPlaces;
    private boolean slowDown;
    private int repCount;
    private double avgTimeOfWorking;
    private double avgQueueCutting;
    private double avgQueueColoring;
    private double avgQueueAssembly;
    private double avgQueueMontage;



    private int currentDay = 0;

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

    public int getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
    }

    public ArrayList<WorkPlace> getWorkPlaces() {
        return workPlaces;
    }

    public void setWorkPlaces(ArrayList<WorkPlace> workPlaces) {
        this.workPlaces = workPlaces;
    }

    public boolean isSlowDown() {
        return slowDown;
    }

    public void setSlowDown(boolean slowDown) {
        this.slowDown = slowDown;
    }

    public int getRepCount() {
        return repCount;
    }

    public void setRepCount(int repCount) {
        this.repCount = repCount;
    }

    public double getAvgTimeOfWorking() {
        return avgTimeOfWorking;
    }

    public void setAvgTimeOfWorking(double avgTimeOfWorking) {
        this.avgTimeOfWorking = avgTimeOfWorking;
    }

    public double getAvgQueueCutting() {
        return avgQueueCutting;
    }

    public void setAvgQueueCutting(double avgQueueCutting) {
        this.avgQueueCutting = avgQueueCutting;
    }

    public double getAvgQueueColoring() {
        return avgQueueColoring;
    }

    public void setAvgQueueColoring(double avgQueueColoring) {
        this.avgQueueColoring = avgQueueColoring;
    }

    public double getAvgQueueAssembly() {
        return avgQueueAssembly;
    }

    public void setAvgQueueAssembly(double avgQueueAssembly) {
        this.avgQueueAssembly = avgQueueAssembly;
    }

    public double getAvgQueueMontage() {
        return avgQueueMontage;
    }

    public void setAvgQueueMontage(double avgQueueMontage) {
        this.avgQueueMontage = avgQueueMontage;
    }
}
