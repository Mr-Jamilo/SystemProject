//TODO write logins to logins.csv

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class NewCustPage implements ActionListener{
    JFrame frame = new JFrame();
    JLabel emailLBL = new JLabel("Email:");
    JLabel passLBL = new JLabel("Password:");
    JLabel ConpassLBL = new JLabel("Confirm Password:");
    JTextField emailTf = new JTextField();
    JPasswordField passTf = new JPasswordField();
    JPasswordField ConpassTf = new JPasswordField();
    JButton signUpBtn = new JButton("SIGN UP");
    JLabel messageLbl = new JLabel();
    HashMap<String,String> loginDetails = new HashMap<String,String>();
    JButton backBtn = new JButton("<html><center>BACK</center></html>");

    NewCustPage(HashMap<String, String> hashMap){
        
        messageLbl.setBounds(230,280,380,35);
        messageLbl.setFont(new Font(null,Font.BOLD,25));
        emailLBL.setBounds(200,100,75,25);
        passLBL.setBounds(168,130,75,25);
        ConpassLBL.setBounds(112,160,140,25);

        backBtn.setBounds(700,20,70,30);
        backBtn.setFocusable(false);
        backBtn.addActionListener(this);
        
        emailTf.setBounds(275,100,200,25);
        passTf.setBounds(275,130,200,25);
        ConpassTf.setBounds(275,160,200,25);

        signUpBtn.setBounds(275,250,200,35);
        signUpBtn.setFocusable(false);
        signUpBtn.addActionListener(this);

        frame.add(messageLbl);
        frame.add(emailLBL);
        frame.add(passLBL);
        frame.add(ConpassLBL);
        frame.add(backBtn);
        frame.add(emailTf);
        frame.add(passTf);
        frame.add(ConpassTf);        
        frame.add(signUpBtn);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e){
        if (e.getSource()==backBtn){
            frame.dispose();
            new LoginPage(null);
        }
        
        if (e.getSource()==signUpBtn){
            String userEmail = emailTf.getText();
            String userPass = String.valueOf(passTf.getPassword());
            String ConuserPass = String.valueOf(ConpassTf.getPassword());
            
            if(userEmail.contains(".com") && userEmail.contains("@")){
                messageLbl.setForeground(Color.green);
                messageLbl.setText("valid email :)");


                /////////////////////////////
                
                if(ConuserPass.equals(userPass)){
                    messageLbl.setForeground(Color.green);
                    messageLbl.setText("passwords match :)");
                    frame.dispose();
                    loginDetails.put(userEmail, ConuserPass);
                    new LoginPage(loginDetails);
                }
                else{
                    messageLbl.setForeground(Color.red);
                    messageLbl.setText("passwords don't match :(");
                }
            }
            else{
                messageLbl.setForeground(Color.red);
                messageLbl.setText("invalid email :(");
            }
        }
    }
}