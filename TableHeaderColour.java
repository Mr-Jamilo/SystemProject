import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TableHeaderColour extends DefaultTableCellRenderer {

  // Override the getTableCellRendererComponent method
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        c.setBackground(Color.decode("#44A2F8")); // Set the background color of the table header to red
        return c;
    }
}
