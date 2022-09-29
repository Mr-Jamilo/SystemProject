//TODO add buttons to table
//TODO dynamically name the buttons https://www.tutorialspoint.com/how-can-we-change-the-jbutton-text-dynamically-in-java

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.*;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class MenuPage implements MouseListener{
    JFrame frame = new JFrame();
    JLabel title = new JLabel("Menu");
    JTextField userSearch = new JTextField();
    TextPrompt searchPrompt = new TextPrompt("Search Name / ID", userSearch);
    JButton addFoodBtn = new JButton("<html><center>ADD TO ORDER</center></html>");
    
    String[] headings= {"Food ID","Food Name","Cost"};      
    DefaultTableModel model = new DefaultTableModel(headings,0);
    JTable demoTable = new JTable(model);
    JScrollPane demoTableScroll = new JScrollPane(demoTable);
    
    JPanel box = new JPanel();
    JButton confirmOrder = new JButton("<html><center>CONFIRM ORDER</center></html>");
    JLabel custOrderlbl = new JLabel("YOUR ORDER");
    //////////////////////
    String file = "menu.txt";
    String line = "";
    MenuList menuList = new MenuList();
    FoodItem food = new FoodItem();
    
    MenuPage(){
        
        
        box.setBounds(500,80,250,300);
        box.setBorder(BorderFactory.createLineBorder(Color.black));
        frame.add(box);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setLayout(null);
        frame.setVisible(true);

        initComponentsintoFrame();
        initComponentsintoPanel();
        readMenu();
    }
    
    public void initComponentsintoFrame(){
        title.setBounds(380, 10, 70, 30);
        title.setFont(new Font(null,Font.BOLD,20));
        frame.add(title);    
        
        addFoodBtn.setBounds(140,480,100,30);
        addFoodBtn.setFocusable(false);
        frame.add(addFoodBtn);
        
        userSearch.setBounds(30,50,200,20);
        searchPrompt.setForeground(Color.gray); 
        frame.add(userSearch);
        
        demoTable.addMouseListener(this);
        
        demoTableScroll.setBounds(30,80,400,350);
        
        frame.add(demoTableScroll);

        confirmOrder.setBounds(500,390,250,40);
        confirmOrder.setFont(new Font(null,Font.BOLD,19));
        confirmOrder.setFocusable(false);
        frame.add(confirmOrder);
    }
    
    public void initComponentsintoPanel(){
        custOrderlbl.setBounds(550, 90, 100, 60);
        custOrderlbl.setFont(new Font(null,Font.BOLD,15));
        box.add(custOrderlbl);
    }

    public void addFoodtoOrder(){
        
    }
    
    public void readMenu(){
        //reference: https://youtu.be/L2xczUN9aI0
        String file = "menu.csv";
        try {
            try (BufferedReader bR = new BufferedReader(new FileReader(file))) {
                Object[] tableLines = bR.lines().toArray();
                
                for (int i = 0; i < tableLines.length;i++){
                    String line = tableLines[i].toString().trim();
                    String[] dataRow = line.split(",");
                    model.addRow(dataRow);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void mouseClicked(MouseEvent mevt){  
        int theRow = demoTable.rowAtPoint(mevt.getPoint());
        System.out.println(theRow);
    } 
    
    public void mousePressed(MouseEvent mevt){
    
    }
    
    public void mouseEntered(MouseEvent mevt){
    }
    
    public void mouseExited(MouseEvent mevt){
    
    }
    
    public void mouseReleased(MouseEvent mevt){
    
    }
}
