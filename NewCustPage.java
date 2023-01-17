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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class NewCustPage implements ActionListener{
    JFrame frame = new JFrame();
    JTextField firstnameTextField = new JTextField();
    TextPrompt firstnameTextPrompt = new TextPrompt("First Name", firstnameTextField);
    JTextField lastnameTextField = new JTextField();
    TextPrompt lastnameTextPrompt = new TextPrompt("Last Name", lastnameTextField);
    JTextField emailTf = new JTextField();
    TextPrompt emailTextPrompt = new TextPrompt("Email", emailTf);
    JPasswordField passTf = new JPasswordField();
    TextPrompt passTextPrompt = new TextPrompt("Password", passTf);
    JPasswordField ConpassTf = new JPasswordField();
    TextPrompt ConpassTextPrompt = new TextPrompt("Confirm Password", ConpassTf);
    JButton signUpBtn = new JButton("SIGN UP");
    JLabel messageLbl = new JLabel();
    HashMap<String,String> loginDetails = new HashMap<String,String>();
    LoginDetails logDetails = new LoginDetails();
    JButton backBtn = new JButton("<html><center>BACK</center></html>");
    int highestCustomerID = 0;
    String[] templine;
    String line = "";

    NewCustPage(HashMap<String, String> hashMap){
        
        messageLbl.setBounds(230,280,380,35);
        messageLbl.setFont(new Font(null,Font.BOLD,25));

        backBtn.setBounds(700,20,70,30);
        backBtn.setFocusable(false);
        backBtn.addActionListener(this);
        
        firstnameTextField.setBounds(275,70,95,25);
        lastnameTextField.setBounds(380,70,95,25);
        emailTf.setBounds(275,100,200,25);
        passTf.setBounds(275,130,200,25);
        ConpassTf.setBounds(275,160,200,25);

        signUpBtn.setBounds(275,250,200,35);
        signUpBtn.setFocusable(false);
        signUpBtn.addActionListener(this);

        frame.add(messageLbl);
        frame.add(backBtn);
        frame.add(firstnameTextField);
        frame.add(lastnameTextField);
        frame.add(emailTf);
        frame.add(passTf);
        frame.add(ConpassTf);        
        frame.add(signUpBtn);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,650);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e){
        if (e.getSource()==backBtn){
            frame.dispose();
            System.out.println("Going back to previous page");
            new LoginPage(logDetails.getLoginDetails());
        }
        
        if (e.getSource()==signUpBtn){
            getCustomerID();
            
            String userFirstName = firstnameTextField.getText();
            String userLastName = lastnameTextField.getText();
            String userEmail = emailTf.getText();
            String userPass = String.valueOf(passTf.getPassword());
            String ConuserPass = String.valueOf(ConpassTf.getPassword());
            loginDetails.put(userEmail, ConuserPass);

            if((userEmail.contains(".com") || userEmail.contains(".co.uk")) && userEmail.contains("@")){
                messageLbl.setForeground(Color.green);
                messageLbl.setText("valid email :)");
                
                if(loginDetails.containsKey(userEmail)){
                    System.out.println("check for email already exists");
                    messageLbl.setForeground(Color.red);
                    messageLbl.setText("email already exists :(");
                } else{
                    
                    if(ConuserPass.equals(userPass)){
                        messageLbl.setForeground(Color.green);
                        messageLbl.setText("passwords match :)");
                        System.out.println("Added " + userEmail + " and " + ConuserPass + " to hashtable");
                        frame.dispose();
                        new LoginPage(loginDetails);
                        
                        try (FileWriter fw = new FileWriter("logins.csv", true)) {
                            fw.append(highestCustomerID + "," + userFirstName + "," + userLastName + "," + userEmail + "," + userPass + "," + "0" + "\n");
                            fw.flush();
                            fw.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    } else{
                        messageLbl.setForeground(Color.red);
                        messageLbl.setText("passwords don't match :(");
                    }
                }
            } else{
                messageLbl.setForeground(Color.red);
                messageLbl.setText("invalid email :(");
            }
        }
    }

    public int getCustomerID(){
        try (BufferedReader bR = new BufferedReader(new FileReader("logins.csv"))) {
            while((line = bR.readLine()) != null){
                templine = line.split(",");
                    
                try{
                    String strtemphighestID = templine[0];
                    if (highestCustomerID < (Integer.parseInt(strtemphighestID))){
                        highestCustomerID = Integer.parseInt(strtemphighestID);
                    }
                } catch(Exception e){
                    
                    System.out.println("file is empty");
                }
            }
            highestCustomerID = highestCustomerID + 1;
            //System.out.println(highestCustomerID);
            bR.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(highestCustomerID + " number 1");
        return highestCustomerID;
    }
}