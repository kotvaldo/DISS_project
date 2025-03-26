package Furniture.Entity;

import java.util.ArrayList;
import java.util.LinkedList;

public class WorkPlace {
    private final LinkedList<Order> queueOne;
    private final ArrayList<Worker> workersOne;
    private int stateOne;
    private int activityOne;

    private final ArrayList<Worker> workersTwo;
    private final LinkedList<Order> queueTwo;
    private int stateTwo;
    private int activityTwo;

    private final ArrayList<Worker> workersThree;
    private final LinkedList<Order> queueThree;
    private int stateThree;
    private int activityThree;

    private final LinkedList<Order> queueFour;

    public WorkPlace() {
        queueOne = new LinkedList<>();
        workersOne = new ArrayList<>();
        workersTwo = new ArrayList<>();
        queueThree = new LinkedList<>();
        workersThree = new ArrayList<>();
        queueTwo = new LinkedList<>();
        queueFour = new LinkedList<>();
    }

    public void initWorkers(int countA, int countB, int countC) {
        workersOne.clear();
        workersTwo.clear();
        workersThree.clear();
        for (int i = 0; i < countA; i++) {
            workersOne.add(new Worker(1));
        }
        for (int i = 0; i < countC; i++) {
            workersTwo.add(new Worker(2));
        }
        for (int i = 0; i < countB; i++) {
            workersThree.add(new Worker(3));
        }

    }

    public void clearOrderQueues() {
        queueOne.clear();
        queueTwo.clear();
        queueThree.clear();
    }

    public LinkedList<Order> getQueueOne() {
        return queueOne;
    }

    public LinkedList<Order> getQueuesTwo() {
        return this.queueTwo;
    }
    public LinkedList<Order> getQueuesThree() {
        return queueThree;
    }
    public ArrayList<Worker> getWorkersOne() {
        return this.workersOne;
    }
    public ArrayList<Worker> getWorkersTwo() {
        return this.workersTwo;
    }
    public ArrayList<Worker> getWorkersThree() {
        return workersThree;
    }

    public int getStateOne() {
        return stateOne;
    }

    public void setStateOne(int stateOne) {
        this.stateOne = stateOne;
    }

    public int getActivityOne() {
        return activityOne;
    }

    public void setActivityOne(int activityOne) {
        this.activityOne = activityOne;
    }

    public int getStateTwo() {
        return stateTwo;
    }

    public void setStateTwo(int stateTwo) {
        this.stateTwo = stateTwo;
    }

    public int getActivityTwo() {
        return activityTwo;
    }

    public void setActivityTwo(int activityTwo) {
        this.activityTwo = activityTwo;
    }

    public int getStateThree() {
        return stateThree;
    }

    public void setStateThree(int stateThree) {
        this.stateThree = stateThree;
    }

    public int getActivityThree() {
        return activityThree;
    }

    public void setActivityThree(int activityThree) {
        this.activityThree = activityThree;
    }

    public LinkedList<Order> getQueueFour() {
        return queueFour;
    }
}
