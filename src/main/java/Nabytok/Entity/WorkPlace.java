package Nabytok.Entity;

import IDGenerator.IDGenerator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class WorkPlace {
    private Queue<Order> queueOne;
    private ArrayList<Worker> workersOne;

    private ArrayList<Worker> workersTwo;
    private Queue<Order> queueTwo;

    private ArrayList<Worker> workersThree;
    private Queue<Order> queueThree;

    public WorkPlace() {
        queueOne = new LinkedList<>();
        workersOne = new ArrayList<>();
        workersTwo = new ArrayList<>();
        queueThree = new LinkedList<>();
        workersThree = new ArrayList<>();
        queueTwo = new LinkedList<>();
    }

    public void setWorkers(int countA, int countB, int countC) {
        workersOne.clear();
        workersTwo.clear();
        workersThree.clear();
        for (int i = 0; i < countA; i++) {
            workersOne.add(new Worker(IDGenerator.getInstance().getNextPersonId(), 1));
        }
        for (int i = 0; i < countB; i++) {
            workersTwo.add(new Worker(IDGenerator.getInstance().getNextPersonId(), 2));
        }

    }

    public void clearOrderQueues() {
        queueOne.clear();
        queueTwo.clear();
        queueThree.clear();
    }

    public Queue<Order> getQueuesOne() {
        return this.queueOne;
    }

    public Queue<Order> getQueuesTwo() {
        return this.queueTwo;
    }
    public Queue<Order> getQueuesThree() {
        return queueThree;
    }

}
