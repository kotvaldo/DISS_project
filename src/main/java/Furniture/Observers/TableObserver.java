package Furniture.Observers;

import Furniture.Entity.Order;
import Furniture.Entity.WorkPlace;
import Furniture.Entity.Worker;
import Furniture.FurnitureEventState;
import GUI.Models.*;
import Observer.IObserver;
import State.IState;

import javax.swing.*;
import java.util.ArrayList;

public class TableObserver implements IObserver {

    private final OrdersTableModel ordersModel;
    private final WorkersTableModel workersModel;
    private final WorkPlacesTableModel workPlacesModel;

    public TableObserver(OrdersTableModel ordersModel, WorkersTableModel workersModel, WorkPlacesTableModel workPlacesModel) {
        this.ordersModel = ordersModel;
        this.workersModel = workersModel;
        this.workPlacesModel = workPlacesModel;
    }

    @Override
    public void update(IState state) {
        FurnitureEventState furnitureState = (FurnitureEventState) state;

        if (furnitureState.isSlowDown()) {
            SwingUtilities.invokeLater(() -> {
                ArrayList<Order> ordersSnapshot = new ArrayList<>(furnitureState.getAllOrders());

                ArrayList<Worker> workersSnapshot = new ArrayList<>();
                workersSnapshot.addAll(furnitureState.getWorkersA());
                workersSnapshot.addAll(furnitureState.getWorkersB());
                workersSnapshot.addAll(furnitureState.getWorkersC());

                ArrayList<WorkPlace> workPlacesSnapshot = new ArrayList<>(furnitureState.getWorkPlaces());

                ordersModel.setOrders(ordersSnapshot);
                workersModel.setWorkers(workersSnapshot);
                workPlacesModel.setWorkPlaces(workPlacesSnapshot);
            });
        }
    }
}
