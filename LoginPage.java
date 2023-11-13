import java.awt.Font;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Base64;

public class LoginPage implements ActionListener{
    
    JFrame frame = new JFrame();
    //Button loginBtn = new JButton("LOGIN");
    CustomLoginButton loginBtn = new CustomLoginButton("LOGIN", Color.decode("#51c151"));
    JButton newloginBtn = new JButton("<html><u>Sign Up Here</u></html>");
    RoundedTextField emailTf = new RoundedTextField(Color.BLACK);
    RoundedPasswordField passTf = new RoundedPasswordField(Color.BLACK);
    JLabel title = new JLabel("Welcome To The Golden Lodge");
    JLabel newLoginLbl = new JLabel("New Customer?");
    JLabel messageLbl = new JLabel();
    TextPrompt emailSearchPrompt = new TextPrompt("Email", emailTf);
    TextPrompt passwordSearchPrompt = new TextPrompt("Password", passTf);
    HashMap<String,String> loginDetails = new HashMap<String,String>();
    
    LoginPage(HashMap<String,String> loginDetailGLB){
        loginDetails = loginDetailGLB;
        title.setBounds(170,50,450,40);
        title.setFont(new Font(null,Font.BOLD,30));
        
        messageLbl.setBounds(275,380,280,35);
        messageLbl.setFont(new Font(null,Font.BOLD,15));
        
        emailSearchPrompt.setFont(new Font(null,Font.PLAIN,15));
        emailSearchPrompt.setForeground(Color.GRAY);
        emailTf.setBounds(255,150,260,40);
        emailTf.setFont(new Font(null,Font.PLAIN,15));
        emailTf.setText("Staff@gmail.com");
        
        passwordSearchPrompt.setFont(new Font(null,Font.PLAIN,15));
        passwordSearchPrompt.setForeground(Color.GRAY);
        passTf.setBounds(255,210,260,40);
        passTf.setFont(new Font(null,Font.PLAIN,15));
        passTf.setText("abc123");
        
        newLoginLbl.setBounds(270,330,275,35);
        newLoginLbl.setFont(new Font(null,Font.BOLD,15));
        
        newloginBtn.setBounds(370,335,140,25);
        newloginBtn.setFont(new Font(null, Font.BOLD, 15));
        newloginBtn.setForeground(Color.decode("#1464f6"));
        newloginBtn.setOpaque(false);
        newloginBtn.setContentAreaFilled(false);
        newloginBtn.setBorderPainted(false);
        newloginBtn.setFocusable(false);
        newloginBtn.addActionListener(this);
        
        loginBtn.setBounds(240,275,285,40);
        loginBtn.setFocusable(false);
        loginBtn.addActionListener(this);
        
        //frame.add(title);
        frame.add(messageLbl);
        frame.add(newLoginLbl);
        frame.add(emailTf);
        frame.add(passTf);
        frame.add(loginBtn);
        frame.add(newloginBtn);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(800,650);
        frame.getContentPane().setBackground(Color.decode("#a4cbfe"));
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
    } 

    
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==loginBtn){
            String userEmail = emailTf.getText();
            String userPass = String.valueOf(passTf.getPassword());
            byte[] encodedEmail = Base64.getEncoder().encode(userEmail.getBytes());
            byte[] encodedPass = Base64.getEncoder().encode(userPass.getBytes());
            
            if(loginDetails.containsKey(new String(encodedEmail))){
                if(userEmail.equals("Staff@gmail.com") && loginDetails.get(new String(encodedEmail)).equals(new String(encodedPass))){
                    System.out.println("Logged in with the email: " + new String(encodedEmail) + " and password: " + new String(encodedPass));
                    messageLbl.setForeground(Color.green);
                    messageLbl.setText("logged in :)");
                    frame.dispose();
                    new StaffPage();
                }
                else if(loginDetails.get(new String(encodedEmail)).equals(new String(encodedPass))){
                    System.out.println("Logged in with the email: " + new String(encodedEmail) + " and password: " + new String(encodedPass));
                    messageLbl.setForeground(Color.green);
                    messageLbl.setText("logged in :)");
                    frame.dispose();
                    new MenuPage(new String(encodedEmail),new String(encodedPass));
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
        new NewCustPage(loginDetails.getLoginDetails());
    }
}
