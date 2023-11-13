import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.RowSorter;
import java.util.List;

public class Archives implements ActionListener{
    JFrame frame = new JFrame();
    JLabel title = new JLabel("Customer Details");
    JButton backBtn = new JButton("<html><center>BACK</center></html>");

    Archives(){
        initFrame();
    }

    public void initFrame(){
        title.setBounds(300,15,300,40);
        title.setFont(new Font(null,Font.BOLD,20));
        frame.add(title);

        backBtn.setBounds(700,10,70,30);
        backBtn.setFocusable(false);
        backBtn.addActionListener(this);
        frame.add(backBtn);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(800,650);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==backBtn){
            frame.dispose();
            System.out.println("Back to Staff Page");
            new StaffPage();
        }
    }
}
