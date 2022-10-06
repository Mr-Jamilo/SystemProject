//wanted to add buttons to table
//wanted to dynamically name the buttons https://www.tutorialspoint.com/how-can-we-change-the-jbutton-text-dynamically-in-java

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.*;
import javax.swing.text.JTextComponent;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MenuPage implements MouseListener, ActionListener{
    String foodItem;
    String foodCost;
    int removeFoodIndex;
    int amountOrdered;
    Double totalcost = 0.0;
    
    JFrame frame = new JFrame();
    JLabel title = new JLabel("Menu");
    JTextField userSearch = new JTextField();
    TextPrompt searchPrompt = new TextPrompt("Search Name / ID", userSearch);
    JButton addFoodBtn = new JButton("<html><center>ADD TO ORDER</center></html>");
    JButton removeFoodBtn = new JButton("<html><center>REMOVE FROM ORDER</center></html>");
    
    String[] headings= {"Food ID","Food Name","Cost"};      
    DefaultTableModel model = new DefaultTableModel(headings,0);
    JTable demoTable = new JTable(model);
    JScrollPane demoTableScroll = new JScrollPane(demoTable);
    
    JLabel totalLbl = new JLabel();
    
    String[] oHeadings = {"Food Name","Cost"};
    DefaultTableModel oModel = new DefaultTableModel(oHeadings,0);
    JTable oTable = new JTable(oModel);
    JScrollPane oTableScroll = new JScrollPane(oTable);
    
    JPanel box = new JPanel();
    JButton confirmOrder = new JButton("<html><center>CONFIRM ORDER</center></html>");
    JLabel custOrderlbl = new JLabel("YOUR ORDER");
    
    MenuPage(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setLayout(null);
        frame.setVisible(true);

        initComponentsintoFrame();
        readMenu();
    }
    
    public void initComponentsintoFrame(){
        title.setBounds(380, 10, 70, 30);
        title.setFont(new Font(null,Font.BOLD,20));
        frame.add(title);    
        
        custOrderlbl.setBounds(570, 40, 200, 60);
        custOrderlbl.setFont(new Font(null,Font.BOLD,15));
        frame.add(custOrderlbl);
        
        totalLbl.setBounds(500,490,250,40);
        totalLbl.setFont(new Font(null,Font.BOLD,15));
        frame.add(totalLbl);
        
        addFoodBtn.setBounds(140,480,100,70);
        addFoodBtn.setFocusable(false);
        addFoodBtn.addActionListener(this);
        frame.add(addFoodBtn);
        
        removeFoodBtn.setBounds(250,480,100,70);
        removeFoodBtn.setFocusable(false);
        removeFoodBtn.addActionListener(this);
        frame.add(removeFoodBtn);
        
        userSearch.setBounds(30,50,200,20);
        searchPrompt.setForeground(Color.gray); 
        frame.add(userSearch);
        
        demoTable.addMouseListener(this);
        demoTableScroll.setBounds(30,80,400,350);
        frame.add(demoTableScroll);

        oTable.addMouseListener(this);
        oTableScroll.setBounds(500,90,250,280);
        frame.add(oTableScroll);

        confirmOrder.setBounds(500,390,250,40);
        confirmOrder.setFont(new Font(null,Font.BOLD,19));
        confirmOrder.setFocusable(false);
        confirmOrder.addActionListener(this);
        frame.add(confirmOrder);
    }
    
    public void readMenu(){
        //reference: https://youtu.be/L2xczUN9aI0
        String file = "menu.csv";
        try {
            try (BufferedReader bR = new BufferedReader(new FileReader(file))) {
                Object[] tableLines =  bR.lines().toArray();
                
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
        int selectedRowIndex = demoTable.getSelectedRow();
        foodItem = (String) demoTable.getModel().getValueAt(selectedRowIndex, 1);
        foodCost = (String) demoTable.getModel().getValueAt(selectedRowIndex, 2);

        removeFoodIndex = oTable.getSelectedRow();
    } 
    
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==addFoodBtn){
            String[] data = {foodItem,foodCost};
            oModel.addRow(data);
            amountOrdered = amountOrdered + 1;
        }
        else if(e.getSource()==removeFoodBtn){
            try {
                oModel.removeRow(removeFoodIndex);
                amountOrdered = amountOrdered - 1;
            } catch (Exception a) {
                System.out.println("error");
            }
        }
        else if(e.getSource()==confirmOrder){
            try {
                FileWriter fr = new FileWriter("custorder.csv");
                for(int i = 0;i < amountOrdered;i++){
                    String tempfoodItem = (String) oTable.getModel().getValueAt(i,0);
                    String tempfoodCost = (String) oTable.getModel().getValueAt(i,1);
                    fr.write(tempfoodItem+","+tempfoodCost);
                    fr.write("\r\n");
                }
                fr.close();
            } 
            catch (Exception e1) {
                System.out.println("error");
            }
            
            for(int i = 0; i < amountOrdered;i++){
                String tempfoodCost = (String) oTable.getModel().getValueAt(i,1);
                Double cost = Double.parseDouble(tempfoodCost);
                totalcost = totalcost + cost;
            }
            //System.out.println(totalcost);
            
            totalLbl.setText("Total: " + totalcost);
            clearTable();
        }
    }
    
    public void clearTable(){
        int lastRow = amountOrdered - 1;
        for(int i = lastRow;i>=0;i--){
            oModel.removeRow(i);
        }
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
