package Furniture.Observers;

import Furniture.FurnitureEventState;
import Observer.IObserver;
import State.IState;

import javax.swing.*;
import Furniture.Entity.Order;
import Furniture.Entity.Worker;
import Furniture.Enums.OrderStateValues;
import Furniture.Enums.WorkerBussyState;

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

            // Vymazanie všetkých riadkov z orders tabuľky
            while (ordersModel.getRowCount() > 0) {
                ordersModel.removeRow(0);
            }

            for (Order order : ordersSnapshot) {
                String stateOfOrder = OrderStateValues.getNameByValue(order.getState());
                   ordersModel.addRow(new Object[]{
                            order.getId(),
                            order.getType(),
                            stateOfOrder
                    });
            }

            // Vymazanie všetkých riadkov z workers tabuľky
            while (workersModel.getRowCount() > 0) {
                workersModel.removeRow(0);
            }

            ArrayList<Worker> snapshot = new ArrayList<>();
            snapshot.addAll(furnitureState.getWorkersA());
            snapshot.addAll(furnitureState.getWorkersB());
            snapshot.addAll(furnitureState.getWorkersC());

            for (Worker worker : snapshot) {
                String group = worker.getType();
                String stateOfWorker = WorkerBussyState.getNameByValue(worker.getCurrentState());
                String orderID = worker.getOrder() != null ? "Order : " + worker.getOrder().getId() : "No Order";
                workersModel.addRow(new Object[]{
                        worker.getId(),
                        group,
                        stateOfWorker,
                        orderID
                });
            }
        });
    }

}
