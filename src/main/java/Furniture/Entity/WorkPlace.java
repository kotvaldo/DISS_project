package Furniture.Entity;

import java.util.ArrayList;
import java.util.LinkedList;

public class WorkPlace {
    private final LinkedList<Order> queueOne;
    private final ArrayList<Worker> workersA;
    private int stateOne;
    private int activityOne;

    private final ArrayList<Worker> workersC;
    private final LinkedList<Order> queueTwo;
    private int stateTwo;
    private int activityTwo;

    private final ArrayList<Worker> workersB;
    private final LinkedList<Order> queueThree;
    private int stateThree;
    private int activityThree;

    private final LinkedList<Order> queueFour;

    public WorkPlace() {
        queueOne = new LinkedList<>();
        workersA = new ArrayList<>();
        workersC = new ArrayList<>();
        queueThree = new LinkedList<>();
        workersB = new ArrayList<>();
        queueTwo = new LinkedList<>();
        queueFour = new LinkedList<>();
    }

    public void initWorkers(int countA, int countB, int countC) {
        workersA.clear();
        workersC.clear();
        workersB.clear();
        for (int i = 0; i < countA; i++) {
            workersA.add(new Worker("A"));
        }
        for (int i = 0; i < countC; i++) {
            workersC.add(new Worker("C"));
        }
        for (int i = 0; i < countB; i++) {
            workersB.add(new Worker("B"));
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
    public ArrayList<Worker> getWorkersA() {
        return this.workersA;
    }
    public ArrayList<Worker> getWorkersC() {
        return this.workersC;
    }
    public ArrayList<Worker> getWorkersB() {
        return workersB;
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
