package GUI.Models;

import Statistics.Average;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class UtilisationTableModel extends AbstractTableModel {

    private final ArrayList<Average> averagesA = new ArrayList<>();
    private final ArrayList<Average> averagesB = new ArrayList<>();
    private final ArrayList<Average> averagesC = new ArrayList<>();

    public UtilisationTableModel(ArrayList<Average> allUtilisationsWorkersA, ArrayList<Average> allUtilisationsWorkersB, ArrayList<Average> allUtilisationsWorkersC) {
        setData(allUtilisationsWorkersA, allUtilisationsWorkersB, allUtilisationsWorkersC);
    }

    public void setData(ArrayList<Average> allUtilisationsWorkersA, ArrayList<Average> allUtilisationsWorkersB, ArrayList<Average> allUtilisationsWorkersC) {
        averagesA.clear();
        averagesB.clear();
        averagesC.clear();

        averagesA.addAll(allUtilisationsWorkersA);
        averagesB.addAll(allUtilisationsWorkersB);
        averagesC.addAll(allUtilisationsWorkersC);

        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return averagesA.size() + averagesB.size() + averagesC.size();
    }

    @Override
    public int getColumnCount() {
        return 3; // ID | Average | Confidence Interval
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex < averagesA.size()) {
            return switch (columnIndex) {
                case 0 -> "A" + (rowIndex + 1);
                case 1 -> String.format("%.4f", averagesA.get(rowIndex).mean());
                case 2 -> averagesA.get(rowIndex).confidenceInterval();
                default -> null;
            };
        } else if (rowIndex < averagesA.size() + averagesB.size()) {
            int index = rowIndex - averagesA.size();
            return switch (columnIndex) {
                case 0 -> "B" + (index + 1);
                case 1 -> String.format("%.4f", averagesB.get(index).mean());
                case 2 -> averagesB.get(index).confidenceInterval();
                default -> null;
            };
        } else {
            int index = rowIndex - averagesA.size() - averagesB.size();
            return switch (columnIndex) {
                case 0 -> "C" + (index + 1);
                case 1 -> String.format("%.4f", averagesC.get(index).mean());
                case 2 -> averagesC.get(index).confidenceInterval();
                default -> null;
            };
        }
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Worker";
            case 1 -> "Average Utilisation";
            case 2 -> "Confidence Interval";
            default -> super.getColumnName(column);
        };
    }
}
