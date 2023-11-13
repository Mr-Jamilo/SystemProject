//DONE write logins to logins.csv

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class NewCustPage implements ActionListener{
    JFrame frame = new JFrame();
    JLabel title = new JLabel("Create New Account");
    RoundedTextField firstnameTextField = new RoundedTextField(Color.BLACK);
    TextPrompt firstnameTextPrompt = new TextPrompt("First Name", firstnameTextField);
    RoundedTextField lastnameTextField = new RoundedTextField(Color.BLACK);
    TextPrompt lastnameTextPrompt = new TextPrompt("Last Name", lastnameTextField);
    RoundedTextField emailTf = new RoundedTextField(Color.BLACK);
    TextPrompt emailTextPrompt = new TextPrompt("Email", emailTf);
    RoundedPasswordField passTf = new RoundedPasswordField(Color.BLACK);
    TextPrompt passTextPrompt = new TextPrompt("Password", passTf);
    RoundedPasswordField ConpassTf = new RoundedPasswordField(Color.BLACK);
    TextPrompt ConpassTextPrompt = new TextPrompt("Confirm Password", ConpassTf);
    CustomLoginButton signUpBtn = new CustomLoginButton("SIGN UP", Color.decode("#51c151"));
    JLabel messageLbl = new JLabel();
    HashMap<String,String> loginDetails = new HashMap<String,String>();
    LoginDetails logDetails = new LoginDetails();
    CustomBackButton backBtn = new CustomBackButton("BACK", Color.decode("#920000"));
    int highestCustomerID = 0;
    String[] templine;
    String line = "";

    NewCustPage(HashMap<String, String> hashMap){
        title.setBounds(240,50,450,40);
        title.setFont(new Font(null,Font.BOLD,30));
        
        messageLbl.setBounds(250,500,380,35);
        messageLbl.setFont(new Font(null,Font.BOLD,25));

        backBtn.setBounds(670,50,70,30);
        backBtn.setFocusable(false);
        backBtn.addActionListener(this);
        
        firstnameTextField.setBounds(255,130,125,40);
        firstnameTextField.setFont(new Font(null,Font.PLAIN,15));
        firstnameTextPrompt.setFont(new Font(null,Font.PLAIN,15));
        firstnameTextPrompt.setForeground(Color.GRAY);
        
        lastnameTextField.setBounds(390,130,125,40);
        lastnameTextField.setFont(new Font(null,Font.PLAIN,15));
        lastnameTextPrompt.setFont(new Font(null,Font.PLAIN,15));
        lastnameTextPrompt.setForeground(Color.GRAY);
        
        emailTf.setBounds(255,190,260,40);
        emailTf.setFont(new Font(null,Font.PLAIN,15));
        emailTextPrompt.setFont(new Font(null,Font.PLAIN,15));
        emailTextPrompt.setForeground(Color.GRAY);
        
        passTf.setBounds(255,250,260,40);
        passTf.setFont(new Font(null,Font.PLAIN,15));
        passTextPrompt.setForeground(Color.GRAY);
        passTextPrompt.setFont(new Font(null,Font.PLAIN,15));

        ConpassTf.setBounds(255,310,260,40);
        ConpassTf.setFont(new Font(null,Font.PLAIN,15));
        ConpassTextPrompt.setForeground(Color.GRAY);
        ConpassTextPrompt.setFont(new Font(null,Font.PLAIN,15));
        
        signUpBtn.setBounds(243,370,285,40);
        signUpBtn.setFocusable(false);
        signUpBtn.addActionListener(this);

        frame.add(title);
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
        frame.getContentPane().setBackground(Color.decode("#a4cbfe"));
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
            String userFirstName = firstnameTextField.getText();
            String userLastName = lastnameTextField.getText();
            String userEmail = emailTf.getText();
            String userPass = String.valueOf(passTf.getPassword());
            String ConuserPass = String.valueOf(ConpassTf.getPassword());
            if(userFirstName.equals("")){ //checks if first name is null
                messageLbl.setForeground(Color.RED);
                messageLbl.setText("Invalid first name");
            } else if(userLastName.equals("")){ //checks if last name is null
                messageLbl.setForeground(Color.RED);
                messageLbl.setText("Invalid last name");
            } else{
                if((userEmail.contains(".com") || userEmail.contains(".co.uk")) && userEmail.contains("@")){ //checks if the email is valid
                    messageLbl.setForeground(Color.green);
                    messageLbl.setText("valid email");
                    
                    if(loginDetails.containsKey(userEmail)){ //checks if the email is already on the system
                        System.out.println("check for email already exists");
                        messageLbl.setForeground(Color.red);
                        messageLbl.setText("email already exists");
                    } else{
                        
                        if(ConuserPass.equals(userPass)){ //checks if the passwords match then writes the details into file
                            getCustomerID();
                            messageLbl.setForeground(Color.green);
                            messageLbl.setText("passwords match");
                            System.out.println("Added " + userEmail + " and " + ConuserPass + " to hashtable");
                            try (FileWriter fw = new FileWriter("logins.csv", true)) {
                                byte[] encodedEmail = Base64.getEncoder().encode(userEmail.getBytes());
                                byte[] encodedPassword = Base64.getEncoder().encode(userPass.getBytes());
                                long start = System.nanoTime();
                                loginDetails.put(new String(encodedEmail), new String(encodedPassword));
                                long end = System.nanoTime();
                                System.out.println("Time to add login details to logins array: " + (end - start) + " ns");
                                fw.append(highestCustomerID + "," + userFirstName + "," + userLastName + "," + new String(encodedEmail) + "," + new String(encodedPassword)  + "," + "0" + "\n");
                                fw.flush();
                                fw.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            frame.dispose();
                            new LoginPage(logDetails.getLoginDetails());
                            
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
        return highestCustomerID + 2;
    }
}