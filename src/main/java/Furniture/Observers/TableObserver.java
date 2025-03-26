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
            ordersModel.setRowCount(0);
            for (Order order : furnitureState.getAllOrders()) {
                String stav = OrderStateValues.getNameByValue(order.getState());
                ordersModel.addRow(new Object[]{order.getId(), order.getType(), stav});
            }

            workersModel.setRowCount(0);

            for (Worker worker : furnitureState.getWorkersA()) {
                String skupina = "A";
                String stav = WorkerBussyState.getNameByValue(worker.getCurrentState());
                workersModel.addRow(new Object[]{worker.getId(), worker.getType(), worker.getCurrentState()});
            }

            for (Worker worker : furnitureState.getWorkersB()) {
                String skupina = "B";
                String stav = WorkerBussyState.getNameByValue(worker.getCurrentState());
                workersModel.addRow(new Object[]{worker.getId(), worker.getType(), worker.getCurrentState()});
            }

            for (Worker worker : furnitureState.getWorkersC()) {

                workersModel.addRow(new Object[]{worker.getId(), worker.getType(), worker.getCurrentState()});
            }
        });


    }
}
