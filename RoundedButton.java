import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Component;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.AbstractBorder;

public class RoundedButton extends JButton {
    private static final long serialVersionUID = 1L;

    public RoundedButton(String text) {
        super("<html><center>" + text.replaceAll("\n", "<br>") + "</center></html>");
        setOpaque(true);
        setPreferredSize(new Dimension(100, 40));
        setContentAreaFilled(false);
        setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        setBorder(new RoundedBorder(10));
        setForeground(Color.BLACK);
        setBackground(Color.WHITE);
        setHorizontalAlignment(CENTER); // center the text horizontally
        setVerticalAlignment(CENTER); // center the text vertically
        setFocusPainted(false); // remove the focus border
        setFont(new Font("Arial", Font.BOLD, 16)); // set the font and style of the text
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(getBackground().darker());
        } else if (getModel().isRollover()) {
            g.setColor(getBackground().brighter());
        } else {
            g.setColor(getBackground());
        }
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
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

