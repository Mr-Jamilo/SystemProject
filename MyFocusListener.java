import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.event.*;

public class MyFocusListener implements FocusListener {
    @Override
    public void focusGained(FocusEvent e) {
        JTextField field = (JTextField) e.getSource();
        JPasswordField passField = (JPasswordField) e.getSource();
        field.setText("");
        passField.setText("");
    }

    @Override
    public void focusLost(FocusEvent e) {
        // Do nothing
    }
}
