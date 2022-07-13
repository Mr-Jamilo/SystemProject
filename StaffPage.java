import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StaffPage implements ActionListener{ //used html for better formatting on buttons
    JFrame frame = new JFrame();
    JLabel title = new JLabel("Staff Menu");
    JButton addOrderBtn = new JButton("<html><center>Add Customer<br />Order</center></html>");
    JButton viewAllOrdersBtn = new JButton("<html><center>View Current<br />Orders</center></html>");
    JButton endDayBtn = new JButton("<html><center>End Day</center></html>");
    JButton viewWeeklyBtn = new JButton("<html><center>View Weekly<br />Overview</center></html>"); 
    JButton viewArchivesBtn = new JButton("<html><center>View Archives</center></html>");
    JButton viewAllCustomersBtn = new JButton("<html><center>View All<br />Customers</center></html>");
    
    StaffPage(){ //GUI
        //setBounds saves lines - better than setLocation and setSize
        
        title.setBounds(290, 50, 200, 35);
        title.setFont(new Font(null,Font.BOLD,25)); //fonts can be changed in the final product
        
        addOrderBtn.setBounds(115,139,129,74);
        addOrderBtn.setFont(new Font(null,Font.BOLD,13));
        addOrderBtn.setFocusable(false);
        
        viewAllOrdersBtn.setBounds(300,139,129,74);
        viewAllOrdersBtn.setFont(new Font(null,Font.BOLD,13));
        viewAllOrdersBtn.setFocusable(false);
        
        endDayBtn.setBounds(485,139,129,74);
        endDayBtn.setFont(new Font(null,Font.BOLD,13));
        endDayBtn.setFocusable(false);
        
        viewWeeklyBtn.setBounds(115,300,129,74);
        viewWeeklyBtn.setFont(new Font(null,Font.BOLD,13));
        viewWeeklyBtn.setFocusable(false);

        viewArchivesBtn.setBounds(300,300,129,74);
        viewArchivesBtn.setFont(new Font(null,Font.BOLD,13));
        viewArchivesBtn.setFocusable(false);
        
        viewAllCustomersBtn.setBounds(485,300,129,74);
        viewAllCustomersBtn.setFont(new Font(null,Font.BOLD,13));
        viewAllCustomersBtn.setFocusable(false);

        frame.add(title);
        frame.add(addOrderBtn);
        frame.add(viewAllOrdersBtn);
        frame.add(endDayBtn);
        frame.add(viewWeeklyBtn);
        frame.add(viewArchivesBtn);
        frame.add(viewAllCustomersBtn);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        // TODO add action listeners to the buttons 
        
    }
}
