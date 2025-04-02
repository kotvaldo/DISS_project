package GUI.Models;

import Furniture.Entity.WorkPlace;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class WorkPlacesTableModel extends AbstractTableModel {

    private final String[] columns = {"WorkPlace ID", "Busy State", "Order ID", "Curr_Activity"};
    private List<WorkPlace> workPlaces;

    public WorkPlacesTableModel(List<WorkPlace> workPlaces) {
        this.workPlaces = workPlaces;
    }

    public void setWorkPlaces(List<WorkPlace> workPlaces) {
        this.workPlaces = workPlaces;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return workPlaces.size();
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
        WorkPlace workPlace = workPlaces.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> workPlace.getId();
            case 1 -> workPlace.isBusy() ? "Busy" : "Available";
            case 2 -> workPlace.getOrder() != null ? workPlace.getOrder().getId() : "No Order";
            case 3 -> workPlace.getActivity();
            default -> null;
        };
    }
}
