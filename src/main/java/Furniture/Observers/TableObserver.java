package Furniture.Observers;

import Furniture.FurnitureEventCore;
import Furniture.FurnitureEventState;
import Observer.IObserver;
import State.IState;

import javax.swing.*;
import Furniture.Entity.Order;
import Furniture.Entity.Worker;
import Furniture.Enums.OrderStateValues;
import Furniture.Enums.WorkerBussyState;
import Furniture.FurnitureEventState;
import Observer.IObserver;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class TableObserver implements IObserver {
    private final JTable ordersTable;
    private final JTable workersTable;
    private final DefaultTableModel ordersModel;
    private final DefaultTableModel workersModel;

    public TableObserver(JTable ordersTable, JTable workersTable) {
        this.ordersTable = ordersTable;
        this.workersTable = workersTable;
        this.ordersModel = (DefaultTableModel) ordersTable.getModel();
        this.workersModel = (DefaultTableModel) workersTable.getModel();
    }

    @Override
    public void update(IState state) {
        FurnitureEventState furnitureState = (FurnitureEventState) state;

        SwingUtilities.invokeLater(() -> {
            ArrayList<Order> ordersSnapshot = new ArrayList<>(furnitureState.getAllOrders());

            ordersModel.setRowCount(0);
            for (Order order : ordersSnapshot) {
                String stav = OrderStateValues.getNameByValue(order.getState());
                ordersModel.addRow(new Object[]{
                        order.getId(),
                        order.getType(),
                        stav
                });
            }

            ArrayList<Worker> workersSnapshot = new ArrayList<>();
            workersSnapshot.addAll(furnitureState.getWorkersA());
            workersSnapshot.addAll(furnitureState.getWorkersB());
            workersSnapshot.addAll(furnitureState.getWorkersC());

            workersModel.setRowCount(0);
            for (Worker worker : workersSnapshot) {
                String skupina = worker.getType();
                String stav = WorkerBussyState.getNameByValue(worker.getCurrentState());
                workersModel.addRow(new Object[]{
                        worker.getId(),
                        skupina,
                        stav
                });
            }
        });
    }

}
