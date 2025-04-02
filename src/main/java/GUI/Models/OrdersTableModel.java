package GUI.Models;

import Furniture.Entity.Order;
import Furniture.Enums.OrderStateValues;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class OrdersTableModel extends AbstractTableModel {

    private final String[] columns = {"Order ID", "Type", "State", "Process Time"};
    private List<Order> orders;

    public OrdersTableModel(List<Order> orders) {
        this.orders = orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return orders.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Order order = orders.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> "Order ID: " + order.getId();
            case 1 -> "Type: " + order.getType();
            case 2 -> OrderStateValues.getNameByValue(order.getState());
            case 3 -> order.getTimeOfWorkArrivalAndEnd() < 0 ? -1 : order.getTimeOfWorkArrivalAndEnd();
            default -> null;
        };
    }
}
