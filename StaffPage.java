import java.awt.Font;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StaffPage implements ActionListener{ //used html for better formatting on buttons
    JFrame frame = new JFrame();
    JLabel title = new JLabel("Staff Menu");
    RoundedButton addOrderBtn = new RoundedButton("Add Customer");
    RoundedButton viewAllOrdersBtn = new RoundedButton("View Current \n Orders");
    RoundedButton endDayBtn = new RoundedButton("End Day");
    RoundedButton viewWeeklyBtn = new RoundedButton("View Weekly \n Overview"); 
    RoundedButton viewArchivesBtn = new RoundedButton("View Archives");
    RoundedButton viewAllCustomersBtn = new RoundedButton("View All \n Customers");
    CustomBackButton backBtn = new CustomBackButton("LOG OUT", Color.decode("#920000"));
    JLabel messageLbl = new JLabel();
    LoginDetails loginDetails = new LoginDetails();

    StaffPage(){ //GUI
        //setBounds saves lines - better than using setLocation and setSize
        
        title.setBounds(310, 25, 200, 35);
        title.setFont(new Font(null,Font.BOLD,25)); //fonts can be changed in the final product
        messageLbl.setBounds(220,400,320,35);
        messageLbl.setFont(new Font(null,Font.BOLD,25));
        
        backBtn.setBounds(680,15,80,30);
        backBtn.setFocusable(false);
        backBtn.addActionListener(this);
        frame.add(backBtn);
        
        addOrderBtn.setBounds(115,139,129,74);
        addOrderBtn.setFont(new Font(null,Font.BOLD,13));
        addOrderBtn.setFocusable(false);
        addOrderBtn.addActionListener(this);
        
        viewAllOrdersBtn.setBounds(300,139,129,74);
        viewAllOrdersBtn.setFont(new Font(null,Font.BOLD,13));
        viewAllOrdersBtn.setFocusable(false);
        viewAllOrdersBtn.addActionListener(this);
        
        endDayBtn.setBounds(485,139,129,74);
        endDayBtn.setFont(new Font(null,Font.BOLD,13));
        endDayBtn.setFocusable(false);
        endDayBtn.addActionListener(this);
        
        viewWeeklyBtn.setBounds(115,300,129,74);
        viewWeeklyBtn.setFont(new Font(null,Font.BOLD,13));
        viewWeeklyBtn.setFocusable(false);
        viewWeeklyBtn.addActionListener(this);

        viewArchivesBtn.setBounds(300,300,129,74);
        viewArchivesBtn.setFont(new Font(null,Font.BOLD,13));
        viewArchivesBtn.setFocusable(false);
        viewArchivesBtn.addActionListener(this);
        
        viewAllCustomersBtn.setBounds(485,300,129,74);
        viewAllCustomersBtn.setFont(new Font(null,Font.BOLD,13));
        viewAllCustomersBtn.setFocusable(false);
        viewAllCustomersBtn.addActionListener(this);

        frame.add(title);
        frame.add(messageLbl);
        frame.add(addOrderBtn);
        frame.add(viewAllOrdersBtn);
        frame.add(endDayBtn);
        frame.add(viewWeeklyBtn);
        frame.add(viewArchivesBtn);
        frame.add(viewAllCustomersBtn);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(800,650);
        frame.getContentPane().setBackground(Color.decode("#a4cbfe"));
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==backBtn){
            frame.dispose();
            System.out.println("Logging out of staff account");
            new LoginPage(loginDetails.getLoginDetails());
        }

        if (e.getSource()==addOrderBtn){
            messageLbl.setForeground(Color.red);
            System.out.println("Opening staff menu page");
            frame.dispose();
            new StaffMenuPage();
        }
        
        else if (e.getSource()==viewAllOrdersBtn){
            messageLbl.setForeground(Color.red);
            System.out.println("Opening current orders page");
            frame.dispose();
            new CurrentOrders();
        }
        else if(e.getSource()==endDayBtn){
            messageLbl.setForeground(Color.red);
            System.out.println("Opening end day page");
            frame.dispose();
            new EndDay();
        }
        else if(e.getSource()==viewWeeklyBtn){
            // messageLbl.setForeground(Color.red);
            // messageLbl.setText("Coming Soon!");
            frame.dispose();
            new WeeklyOverview();
        }
        else if(e.getSource()==viewArchivesBtn){
            // messageLbl.setForeground(Color.red);
            // messageLbl.setText("Coming Soon!");
            frame.dispose();
            new Archives();
        }
        else if(e.getSource()==viewAllCustomersBtn){
            //messageLbl.setForeground(Color.red);
            //messageLbl.setText("Coming Soon!");
            frame.dispose();
            new CustomerLoginsPage();
        }
    }
}
