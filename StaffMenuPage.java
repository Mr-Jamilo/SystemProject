//wanted to add buttons to table
//wanted to dynamically name the buttons https://www.tutorialspoint.com/how-can-we-change-the-jbutton-text-dynamically-in-java

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
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StaffMenuPage implements MouseListener, ActionListener{
    String foodItem;
    String foodCost;
    int removeFoodIndex;
    int amountOrdered;
    BigDecimal totalcost = new BigDecimal(0);
    String orderID = "";
    String line= "";
    String[] templine;
    int highestID = 0;
    
    JFrame frame = new JFrame();
    JLabel title = new JLabel("Menu");
    JButton backBtn = new JButton("<html><center>BACK</center></html>");
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
    JLabel custOrderlbl = new JLabel("CUSTOMER ORDER");
    
    StaffMenuPage(){
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
        
        custOrderlbl.setBounds(550, 40, 200, 60);
        custOrderlbl.setFont(new Font(null,Font.BOLD,15));
        frame.add(custOrderlbl);
        
        backBtn.setBounds(700,20,70,30);
        backBtn.setFocusable(false);
        backBtn.addActionListener(this);
        frame.add(backBtn);

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
        if (e.getSource()==backBtn){
            frame.dispose();
            new StaffPage();
        }
        
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
            // try {
            //     FileWriter fr = new FileWriter("custorder.csv");
            //     for(int i = 0;i < amountOrdered;i++){
            //         String tempfoodItem = (String) oTable.getModel().getValueAt(i,0);
            //         String tempfoodCost = (String) oTable.getModel().getValueAt(i,1);
            //         fr.write(tempfoodItem+","+tempfoodCost);
            //         fr.write("\r\n");
            //     }
            //     fr.close();
            // } 
            // catch (Exception e1) {
            //     System.out.println("error e1");
            // }
            
            
            for(int i = 0; i < amountOrdered;i++){
                String tempfoodCost = (String) oTable.getModel().getValueAt(i,1);
                BigDecimal cost = new BigDecimal(tempfoodCost);
                totalcost = totalcost.add(cost);
            }
            
            totalLbl.setText("Total: £" + totalcost);
            getOrderID();
            writeToCurrentOrdersFile();
            clearTable();
        }
    }
    
    public int getOrderID(){
        try (BufferedReader bR = new BufferedReader(new FileReader("tempcurrentorders.csv"))) {
            while((line = bR.readLine()) != null){
                templine = line.split(",");
                    
                try{
                    String strtemphighestID = templine[0];
                    if (highestID < (Integer.parseInt(strtemphighestID))){
                        highestID = Integer.parseInt(strtemphighestID);
                    }
                } catch(Exception e){
                    System.out.println("parsing error");
                }
            }
            highestID = highestID + 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return highestID;
    }
    
    public void writeToCurrentOrdersFile(){
        
        String[] data = new String[amountOrdered + 1];
        String strhighestID = highestID + "";
        try{
            FileWriter fW = new FileWriter("tempcurrentorders.csv",true);
            data[0] = (strhighestID);
            fW.append(strhighestID + ",");

            String tempfooditem = (String) oTable.getModel().getValueAt(0,0);
            data[1] = tempfooditem;
            fW.append(data[1] + ",");
            
            for(int i = 1;i<amountOrdered;i++){
                String tempfooditem2 = (String) oTable.getModel().getValueAt(i,0);
                //System.out.println("loop: " + i);
                //System.out.println(tempfooditem2);
                data[i+1] = tempfooditem2;
                fW.append(data[i+1] + ",");
            }
            String strtotalcost = totalcost.toString();
            fW.append(strtotalcost);
            fW.append("\r\n");
            fW.close();

        } catch(Exception exception){
            System.out.println("error writing to tempcurrentorders.csv");
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
