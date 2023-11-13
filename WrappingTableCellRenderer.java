import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;
import javax.swing.*; 



public class WrappingTableCellRenderer extends DefaultTableCellRenderer {
    public WrappingTableCellRenderer() {
        setWrapStyleWord(true);
        setLineWrap(true);
    }

    private void setWrapStyleWord(boolean b) {
    }

    private void setLineWrap(boolean b) {
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        JTextArea textArea = new JTextArea();
        textArea.setText((String) value);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        

        if (isSelected && column == 0) {
            textArea.setBackground(table.getSelectionBackground());
        } else {
            textArea.setBackground(table.getBackground());
        }
        return textArea;
    }
}


