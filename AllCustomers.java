import javax.swing.JFrame;
import javax.swing.JLabel;

public class AllCustomers {
    JFrame frame = new JFrame();
    JLabel title = new JLabel("ehhlo :)");
    AllCustomers(){
        title.setBounds(300,30,300,40);
        frame.add(title);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
