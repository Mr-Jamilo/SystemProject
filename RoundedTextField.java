import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Component;
import java.awt.RenderingHints;
import javax.swing.BorderFactory; 
import javax.swing.JTextField;
import javax.swing.border.AbstractBorder;

public class RoundedTextField extends JTextField {
    private static final long serialVersionUID = 1L;
    private final Color borderColor;

    public RoundedTextField(Color borderColor) {
        super();
        this.borderColor = borderColor;
        setOpaque(false);
        //setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // add some padding
        setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        setBorder(new RoundedBorder(10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        g2d.setColor(borderColor);
        g2d.drawRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        g2d.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
        g2.dispose();
    }

    private static class RoundedBorder extends AbstractBorder {
        private static final long serialVersionUID = 1L;
        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c.getBackground());
            g2.fillRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.setColor(Color.BLACK);
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(4, 8, 4, 8);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = 8;
            insets.top = insets.bottom = 4;
            return insets;
        }
    }
}
