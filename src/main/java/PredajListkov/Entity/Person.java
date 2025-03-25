package PredajListkov.Entity;

import IDGenerator.IDGenerator;

public class Person {
    private int id;
    private boolean bussy = false;

    Person() {
        this.id = IDGenerator.getInstance().getNextPersonId();

    }
}
