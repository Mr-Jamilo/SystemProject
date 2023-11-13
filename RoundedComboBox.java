import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

import java.awt.*;

public class RoundedComboBox extends JComboBox<Object> {

    public RoundedComboBox(Object[] items) {
        super(items);
        setUI(new RoundedComboBoxUI());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder());
    }

    private static class RoundedComboBoxUI extends BasicComboBoxUI {

        private Color borderColor = Color.BLACK; // Change border color here

        @Override
        protected JButton createArrowButton() {
            JButton button = new JButton();
            button.setBorder(BorderFactory.createEmptyBorder());
            ImageIcon arrowIcon = new ImageIcon(getClass().getResource("/images/arrow.png")); // Load your arrow icon
            Image scaledImage = arrowIcon.getImage().getScaledInstance(12, 12, Image.SCALE_SMOOTH); // Resize the image
            button.setIcon(new ImageIcon(scaledImage));
            return button;
        }

        @Override
        protected ComboPopup createPopup() {
            return new RoundedComboPopup(comboBox);
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c.getBackground());
            g2.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 10, 10); // Use c parameter to access component width and height
            g2.setColor(c.getForeground());
            super.paint(g2, c);
            g2.setColor(borderColor);
            g2.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, 10, 10); // Use c parameter to access component width and height
            g2.dispose();
        }



        private static class RoundedComboPopup extends BasicComboPopup {

            public RoundedComboPopup(JComboBox<Object> comboBox) {
                super(comboBox);
                setOpaque(false);
                setBackground(comboBox.getBackground());
                setBorder(BorderFactory.createEmptyBorder());
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(getForeground());
                super.paintComponent(g2);
                g2.dispose();
            }
        }
    }
}
