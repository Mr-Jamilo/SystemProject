import java.awt.Font;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Point;

//login page

public class LoginPage implements ActionListener{

    JFrame frame = new JFrame();
    //JPanel loginPanel = new JPanel();
    JButton loginBtn = new JButton("LOGIN");
    JButton newloginBtn = new JButton("New Customer? Sign Up Here");
    JTextField emailTf = new JTextField("Customer1@gmail.com");
    JPasswordField passTf = new JPasswordField("4321");
    JLabel title = new JLabel("Welcome To The Golden Lodge");
    JLabel emailLbl = new JLabel("Email:");
    JLabel passLbl = new JLabel("Password:");
    JLabel messageLbl = new JLabel();
    HashMap<String,String> loginDetails = new HashMap<String,String>();

    LoginPage(HashMap<String,String> loginDetailGLB){
        loginDetails = loginDetailGLB;
        title.setBounds(300,30,300,40);
        emailLbl.setBounds(200,100,75,25);
        passLbl.setBounds(200,150,75,25);
        
        messageLbl.setBounds(275,280,280,35);
        messageLbl.setFont(new Font(null,Font.BOLD,25));
        
        emailTf.setBounds(275,100,200,25);
        passTf.setBounds(275,150,200,25);
        
        newloginBtn.setBounds(200,195,275,25);
        newloginBtn.setFocusable(false);
        newloginBtn.addActionListener(this);
        
        loginBtn.setBounds(200,230,275,35);
        loginBtn.setFocusable(false);
        loginBtn.addActionListener(this);
        
        //frame.add(title);
        frame.add(emailLbl);
        frame.add(passLbl);
        frame.add(messageLbl);
        frame.add(emailTf);
        frame.add(passTf);
        frame.add(loginBtn);
        frame.add(newloginBtn);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(800,650);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
    } 

    
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==loginBtn){
            String userEmail = emailTf.getText();
            String userPass = String.valueOf(passTf.getPassword());


            if(loginDetails.containsKey(userEmail)){
                if(userEmail.equals("Staff@gmail.com") && loginDetails.get(userEmail).equals(userPass)){
                    System.out.println("Logged in with the email: " + userEmail + " and password: " + userPass);
                    messageLbl.setForeground(Color.green);
                    messageLbl.setText("logged in :)");
                    frame.dispose();
                    new StaffPage();
                }
                else if(loginDetails.get(userEmail).equals(userPass)){
                    System.out.println("Logged in with the email: " + userEmail + " and password: " + userPass);
                    messageLbl.setForeground(Color.green);
                    messageLbl.setText("logged in :)");
                    frame.dispose();
                    new MenuPage();
                }
                else{
                    messageLbl.setForeground(Color.red);
                    messageLbl.setText("wrong password");
                }
            }
            else{
                messageLbl.setForeground(Color.red);
                messageLbl.setText("email not found");
            }
        }

        if(e.getSource()==newloginBtn){
            System.out.println("Navigating to New Customer page");
            messageLbl.setText("new customer!");
            frame.dispose();
            openNewCustPage();
        }
    }

    public void openNewCustPage(){
        LoginDetails loginDetails = new LoginDetails();
        new NewCustPage(loginDetails.getLoginDetail());
    }
}
