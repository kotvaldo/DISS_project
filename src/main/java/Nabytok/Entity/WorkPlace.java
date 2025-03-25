package Nabytok.Entity;

import java.util.ArrayList;
import java.util.LinkedList;

public class WorkPlace {
    private LinkedList<Order> queueOne;
    private ArrayList<Worker> workersOne;

    private ArrayList<Worker> workersTwo;
    private LinkedList<Order> queueTwo;

    private ArrayList<Worker> workersThree;
    private LinkedList<Order> queueThree;

    public WorkPlace() {
        queueOne = new LinkedList<>();
        workersOne = new ArrayList<>();
        workersTwo = new ArrayList<>();
        queueThree = new LinkedList<>();
        workersThree = new ArrayList<>();
        queueTwo = new LinkedList<>();
    }

    public void initWorkers(int countA, int countB, int countC) {
        workersOne.clear();
        workersTwo.clear();
        workersThree.clear();
        for (int i = 0; i < countA; i++) {
            workersOne.add(new Worker(1));
        }
        for (int i = 0; i < countB; i++) {
            workersTwo.add(new Worker(2));
        }
        for (int i = 0; i < countC; i++) {
            workersTwo.add(new Worker(2));
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

}
