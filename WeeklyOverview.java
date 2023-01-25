import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.RowSorter;
import java.util.List;

public class WeeklyOverview implements ActionListener{
    JFrame frame = new JFrame();
    JLabel title = new JLabel("Weekly Overview");
    JButton backBtn = new JButton("<html><center>BACK</center></html>");

    String[] earningsHeadings= {"Week Day","Earnings"}; //declaring tables for menu panel   
    DefaultTableModel earningModel = new DefaultTableModel(earningsHeadings,0);
    JTable earningTable = new JTable(earningModel)
    {   
        public boolean isCellEditable(int row, int column) {                
            return false;               
        }
    };
    JScrollPane earningTableScroll = new JScrollPane(earningTable);

    WeeklyOverview(){
        initFrame();
        initComponents();
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

    public void initComponents(){
        earningTableScroll.setBounds(30,100,700,400);
        earningTableScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        earningTable.getTableHeader().setReorderingAllowed(false);
        earningTable.getTableHeader().setResizingAllowed(false);
        frame.add(earningTableScroll);
    }

    
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==backBtn){
            frame.dispose();
            System.out.println("Back to Staff Page");
            new StaffPage();
        }
    }
}
