package Furniture.Observers;

import Furniture.Entity.WorkPlace;
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
    private final JTable workPlaceTable;
    private final DefaultTableModel ordersModel;
    private final DefaultTableModel workersModel;
    private final DefaultTableModel workPlaceModel;

    public TableObserver(JTable ordersTable, JTable workersTable, JTable workPlaceTable) {
        this.ordersTable = ordersTable;
        this.workersTable = workersTable;
        this.ordersModel = (DefaultTableModel) ordersTable.getModel();
        this.workersModel = (DefaultTableModel) workersTable.getModel();
        this.workPlaceTable = workPlaceTable;
        this.workPlaceModel = (DefaultTableModel) workPlaceTable.getModel();

    }

    @Override
    public void update(IState state) {
        FurnitureEventState furnitureState = (FurnitureEventState) state;

        if(furnitureState.isSlowDown()) {
            SwingUtilities.invokeLater(() -> {
                ArrayList<Order> ordersSnapshot = new ArrayList<>(furnitureState.getAllOrders());

                ordersModel.setRowCount(0);

                workersModel.setRowCount(0);

                workPlaceModel.setRowCount(0);


                for (Order order : ordersSnapshot) {
                    String stateOfOrder = OrderStateValues.getNameByValue(order.getState());
                    ordersModel.addRow(new Object[]{
                            "Order ID : " + order.getId(),
                            "Type : " +order.getType(),
                            stateOfOrder
                    });
                }



                ArrayList<Worker> snapshot = new ArrayList<>();
                snapshot.addAll(furnitureState.getWorkersA());
                snapshot.addAll(furnitureState.getWorkersB());
                snapshot.addAll(furnitureState.getWorkersC());

                for (Worker worker : snapshot) {
                    String group = worker.getType();
                    String stateOfWorker = WorkerBussyState.getNameByValue(worker.getCurrentState());
                    String orderID = worker.getOrder() != null ? "" + worker.getOrder().getId() : "No Order";
                    String workPlaceID;
                    if(worker.getCurrentWorkPlace() != null) {
                        workPlaceID = "" + worker.getCurrentWorkPlace().getId();
                    } else {
                        workPlaceID = "Storage";
                    }
                    workersModel.addRow(new Object[]{
                            "Worker ID: " + worker.getId(),
                            group,
                            stateOfWorker,
                            orderID,
                            workPlaceID
                    });
                }

                ArrayList<WorkPlace> snapShotWorkPlaces = new ArrayList<>(furnitureState.getWorkPlaces());


                for (WorkPlace workPlace : snapShotWorkPlaces) {
                    String id = String.valueOf("WorkPlace ID: " + workPlace.getId());
                    String busyState = (workPlace.isBusy() ? "Busy" : "Available");
                    String orderId;
                    if(workPlace.getOrder() != null){
                        orderId = "Order ID : " + workPlace.getOrder().getId();

                    } else {
                        orderId = "No Order";
                    }
                    String activity = workPlace.getActivity();
                    workPlaceModel.addRow(new Object[]{
                            id, busyState, orderId,activity
                    });
                }
            });
        }

    }

}
