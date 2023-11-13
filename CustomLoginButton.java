import javax.swing.*;
import java.awt.*;

public class CustomLoginButton extends RoundedButton {
    private final JLabel label;

    public CustomLoginButton(String text, Color background){
        super("");
        setBackground(background);
        setLayout(new BorderLayout());
        label = new JLabel(text, SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        add(label, BorderLayout.CENTER);
    }

    @Override
    public void setBackground(Color background){
        super.setBackground(background);
        if (label != null){
            label.setOpaque(true);
            label.setBackground(background);
        }
    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (getModel().isArmed()){
            g2.setColor(getBackground().darker());
        } else if (getModel().isRollover()){
            g2.setColor(getBackground().brighter());
        } else{
            g2.setColor(getBackground());
        }
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
        g2.dispose();

        // Draw border
        g.setColor(Color.BLACK);
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
    }
}
