package Furniture;

import Furniture.Entity.Order;
import Furniture.Entity.WorkPlace;
import Furniture.Entity.Worker;
import State.IState;
import Statistics.Average;

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
    private Average average;
    private int queueCutting;
    private int queueColoring;
    private int queueAssembly;
    private int queueMontage;



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


    public int getQueueCutting() {
        return queueCutting;
    }

    public void setQueueCutting(int queueCutting) {
        this.queueCutting = queueCutting;
    }

    public int getQueueColoring() {
        return queueColoring;
    }

    public void setQueueColoring(int queueColoring) {
        this.queueColoring = queueColoring;
    }

    public int getQueueAssembly() {
        return queueAssembly;
    }

    public void setQueueAssembly(int queueAssembly) {
        this.queueAssembly = queueAssembly;
    }

    public int getQueueMontage() {
        return queueMontage;
    }

    public void setQueueMontage(int queueMontage) {
        this.queueMontage = queueMontage;
    }

    public Average getAverageTimeOfWorking() {
        return average;
    }

    public void setAverageTimeOfWorking(Average average) {
        this.average = average;
    }
}
