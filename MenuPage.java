//wanted to add buttons to table
//wanted to dynamically name the buttons https://www.tutorialspoint.com/how-can-we-change-the-jbutton-text-dynamically-in-java

//DONE todo ADD TABBEDPANE
//DONE todo CHANGE FRAME TO PANEL

import javax.swing.*;
import java.awt.*;
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

import javax.swing.JTabbedPane;
import java.math.BigDecimal;

public class MenuPage extends JFrame implements MouseListener, ActionListener, KeyListener{
    LoginDetails loginDetails = new LoginDetails(); //declaring gui
    JPanel menupanel = new JPanel(null);
    JPanel currentOrderpanel = new JPanel(null);
    JFrame menuFrame = new JFrame();
    JTabbedPane tabsPane = new JTabbedPane();
    JPanel box = new JPanel();
    
    String foodItem; //declaring variables for menu panel
    String foodCost;
    int removeFoodIndex;
    int amountOrdered;
    BigDecimal totalcost = new BigDecimal(0);
    String orderID = "";
    String line= "";
    String[] templine;
    int highestID = 0;
    JLabel title = new JLabel("Menu");
    JLabel totalLbl = new JLabel();
    JTextField userSearch = new JTextField();
    TextPrompt searchPrompt = new TextPrompt("Search Name / ID", userSearch);
    JButton addFoodBtn = new JButton("<html><center>ADD TO ORDER</center></html>");
    JButton removeFoodBtn = new JButton("<html><center>REMOVE FROM ORDER</center></html>");
    JButton logoutBtn = new JButton("<html><center>LOG OUT</center></html>");
    JButton confirmOrder = new JButton("<html><center>CONFIRM ORDER</center></html>");
    JLabel custOrderlbl = new JLabel("YOUR ORDER");

    int amountOrdered2; //declaring variables for current orders panel
    JLabel title2 = new JLabel("Current Orders");
    JButton orderBtn = new JButton("<html><center>READ ORDER</center></html>");
    JButton backBtn2 = new JButton("<html><center>BACK</center></html>");
    JButton paidBtn = new JButton("<html><center>ORDER PAID</center></html>");
    JButton notpaidBtn = new JButton("<html><center>ORDER NOT PAID</center></html>");
    
    String[] headings= {"Food ID","Food Name","Cost"}; //declaring tables for menu panel   
    DefaultTableModel model = new DefaultTableModel(headings,0);
    JTable demoTable = new JTable(model)
    {   
        public boolean isCellEditable(int row, int column) {                
            return false;               
        }
    };
    JScrollPane demoTableScroll = new JScrollPane(demoTable);
    
    String[] oHeadings = {"Food Name","Cost"};
    DefaultTableModel oModel = new DefaultTableModel(oHeadings,0);
    JTable oTable = new JTable(oModel){
        public boolean isCellEditable(int row, int column) {                
            return false;               
        }
    };
    JScrollPane oTableScroll = new JScrollPane(oTable);
    
    String[] currentHeadings = {"Order ID"};
    DefaultTableModel currentModel = new DefaultTableModel(currentHeadings,0);
    JTable currentTable = new JTable(currentModel){
        public boolean isCellEditable(int row, int column){
            return false;
        }
    };
    JScrollPane ScrollTable = new JScrollPane(currentTable);

    String[] ordersHeadings = {"Order"};
    DefaultTableModel ordersModel = new DefaultTableModel(ordersHeadings,0);
    JTable ordersTable = new JTable(ordersModel){
        public boolean isCellEditable(int row, int column){
            return false;
        }
    };
    JScrollPane ordersTableScroll = new JScrollPane(ordersTable);
    
    MenuPage(){
        initFrame();
        readMenu();
    }
    
    public void initFrame(){
        this.add(tabsPane);
        this.setSize(800,650);
        this.setLayout(new GridLayout(1,1));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        initTabs();
    }
    
    public void initTabs(){
        initComponentsintoMenuPanel();
        initComponentsintoCurrentOrdersPanel();
        tabsPane.addTab("Menu", menupanel);
        tabsPane.addTab("Current Orders", currentOrderpanel);
    }
    
    public void initComponentsintoMenuPanel(){
        title.setBounds(380, 10, 70, 30);
        title.setFont(new Font(null,Font.BOLD,20));
        menupanel.add(title);    
        
        custOrderlbl.setBounds(570, 40, 200, 60);
        custOrderlbl.setFont(new Font(null,Font.BOLD,15));
        menupanel.add(custOrderlbl);
        
        logoutBtn.setBounds(700,20,70,30);
        logoutBtn.setFocusable(false);
        logoutBtn.addActionListener(this);
        menupanel.add(logoutBtn);
        
        totalLbl.setBounds(500,490,250,40);
        totalLbl.setFont(new Font(null,Font.BOLD,15));
        menupanel.add(totalLbl);
        
        addFoodBtn.setBounds(140,480,100,70);
        addFoodBtn.setFocusable(false);
        addFoodBtn.addActionListener(this);
        menupanel.add(addFoodBtn);
        
        removeFoodBtn.setBounds(250,480,100,70);
        removeFoodBtn.setFocusable(false);
        removeFoodBtn.addActionListener(this);
        menupanel.add(removeFoodBtn);
        
        userSearch.setBounds(30,50,200,20);
        userSearch.addKeyListener(this);
        searchPrompt.setForeground(Color.gray); 
        menupanel.add(userSearch);
        
        demoTable.addMouseListener(this);
        demoTableScroll.setBounds(30,80,400,350);
        demoTableScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        demoTable.getTableHeader().setReorderingAllowed(false);
        demoTable.getTableHeader().setResizingAllowed(false);
        demoTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        demoTable.getColumnModel().getColumn(1).setPreferredWidth(316);
        demoTable.getColumnModel().getColumn(2).setPreferredWidth(34);
        menupanel.add(demoTableScroll);

        oTable.addMouseListener(this);
        oTableScroll.setBounds(500,90,250,280);
        oTable.getTableHeader().setReorderingAllowed(false);
        oTable.getTableHeader().setResizingAllowed(false);
        oTable.getColumnModel().getColumn(0).setPreferredWidth(216);
        oTable.getColumnModel().getColumn(1).setPreferredWidth(34);
        menupanel.add(oTableScroll);

        confirmOrder.setBounds(500,390,250,40);
        confirmOrder.setFont(new Font(null,Font.BOLD,19));
        confirmOrder.setFocusable(false);
        confirmOrder.addActionListener(this);
        menupanel.add(confirmOrder);
    }

    public void initComponentsintoCurrentOrdersPanel(){
        title2.setBounds(330,10,250,30); 
        title2.setFont(new Font(null,Font.BOLD,20));
        currentOrderpanel.add(title2);
        
        backBtn2.setBounds(700,20,70,30);
        backBtn2.setFocusable(false);
        backBtn2.addActionListener(this);
        currentOrderpanel.add(backBtn2);
        
        orderBtn.setBounds(190,450,90,50);
        orderBtn.setFocusable(false);
        orderBtn.addActionListener(this);
        currentOrderpanel.add(orderBtn);
        
        paidBtn.setBounds(290,450,90,50);
        paidBtn.setFocusable(false);
        paidBtn.addActionListener(this);
        currentOrderpanel.add(paidBtn);
        
        notpaidBtn.setBounds(390,450,90,50);
        notpaidBtn.setFocusable(false);
        notpaidBtn.addActionListener(this);
        currentOrderpanel.add(notpaidBtn);

        currentTable.addMouseListener(this);
        ScrollTable.setBounds(30,80,100,200);
        currentTable.getTableHeader().setReorderingAllowed(false);
        currentTable.getTableHeader().setResizingAllowed(false);
        currentOrderpanel.add(ScrollTable);
        
        ordersTableScroll.setBounds(200,80,400,280);
        ordersTable.getTableHeader().setReorderingAllowed(false);
        ordersTable.getTableHeader().setResizingAllowed(false);
        currentOrderpanel.add(ordersTableScroll);

        //readOrderID();
    }
    
    public void searchFoodItem(){
        //search by reading the file
        
        
        String file = "tempmenu.csv";
        boolean searchTermisInt = false;
        String searchTerm = userSearch.getText();
        System.out.println("Searching for " + searchTerm); 
        
        try {
            int intSearchTerm = Integer.parseInt(searchTerm);
            searchTermisInt = true;

        } catch (Exception e) {
            System.out.println("Search term is not an integer");
            searchTermisInt = false;
        }

        
        clearMenuTable();
        try (BufferedReader bR = new BufferedReader(new FileReader(file))) {
            Object[] tableLines = bR.lines().toArray();
            
            for (int i = 0; i < tableLines.length;i++){
                    String line = tableLines[i].toString().trim();
                    String[] dataRow = line.split(",");
                    if ((searchTermisInt == true) && (dataRow[0].equals(searchTerm))){
                            model.addRow(dataRow);
                    } 
                    if ((searchTermisInt == false) && (dataRow[1].toLowerCase().contains(searchTerm.toLowerCase()))){
                            model.addRow(dataRow);
                    }    
            }
            bR.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        // boolean searchTermisInt = false; 
        // int foodNum = model.getRowCount();
        // int intSearchTerm = 0;

        
        // String searchTerm = userSearch.getText();
        // System.out.println("Searching for " + searchTerm);
        
        // try {
            // intSearchTerm = Integer.parseInt(searchTerm);
            // searchTermisInt = true;

        // } catch (Exception e) {
            // System.out.println("Search term is not an integer");
            // searchTermisInt = false;
        // }
        
        // if (searchTermisInt == false){
            // System.out.println("searching for food name");
            // for (int i = 0; i < foodNum; i++){
                // String foodName = model.getValueAt(i, 1).toString();
                // if (foodName.contains(searchTerm)){
                    // //model.addRow(new Object[]{model.getValueAt(i, 0), model.getValueAt(i, 1), model.getValueAt(i, 2)});
                    // String tempFoodId = model.getValueAt(i, 0).toString();
                    // String tempFoodName = model.getValueAt(i, 1).toString();
                    // String tempFoodPrice = model.getValueAt(i, 2).toString();
                    // clearMenuTable();
                    // String[] foodarraysearch = new String[]{tempFoodId, tempFoodName, tempFoodPrice}; 
                    // for (int j = 0; j < foodarraysearch.length; j++){
                        // System.out.println(foodarraysearch[j]);
                    // }
                // }
            // }
        // }
        // else if (searchTermisInt == true){
            // for (int i = 0; i < foodNum; i++){
                // int foodID = Integer.parseInt(model.getValueAt(i, 0).toString());
                // if (foodID == intSearchTerm){
                    // String tempFoodId = model.getValueAt(i, 0).toString();
                    // String tempFoodName = model.getValueAt(i, 1).toString();
                    // String tempFoodPrice = model.getValueAt(i, 2).toString();
                    // clearMenuTable();
                    // model.addRow(new String[]{tempFoodId, tempFoodName, tempFoodPrice}); 
                // }
            // }
        // }
    }
    
    public void readMenu(){
        //reference: https://youtu.be/L2xczUN9aI0
        
        System.out.println("Displaying menu");
        String file = "tempmenu.csv";

        
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

        // try {
        //     try (BufferedReader bR = new BufferedReader(new FileReader(file))) {
        //         while(line != null) {
        //             line = file.trim();
        //             if(line != null) {
        //                 String[] dataRow = line.split(",");
        //                 model.addRow(dataRow);
        //             }
        //         }
        //     }
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
    }
    
    public void mouseClicked(MouseEvent mevt){  
        int selectedRowIndex = demoTable.getSelectedRow();
        foodItem = (String) demoTable.getModel().getValueAt(selectedRowIndex, 1);
        foodCost = (String) demoTable.getModel().getValueAt(selectedRowIndex, 2);

        removeFoodIndex = oTable.getSelectedRow();
    } 
    
    public void actionPerformed(ActionEvent e){
        if (e.getSource()==logoutBtn){
            menuFrame.setVisible(false);
            System.out.println("Logging out of customer account");
            new LoginPage(loginDetails.getLoginDetail());
        }
        
        if(e.getSource()==addFoodBtn){
            System.out.println("Added " + foodItem + " to order");
            String[] data = {foodItem,foodCost};
            oModel.addRow(data);
            amountOrdered = amountOrdered + 1;
        }
        else if(e.getSource()==removeFoodBtn){
            try {
                String DelFoodItem = (String) oTable.getModel().getValueAt(removeFoodIndex, 1);
                System.out.println("Removed " + foodItem + " from order");
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
                System.out.println("error e1");
            }
            
            // try {
            //     FileWriter fr = new FileWriter("currentorders.csv");
            //     for(int i = 0;i < amountOrdered;i++){
            //         String tempfoodCost = (String) oTable.getModel().getValueAt(i,1);
            //         Double cost = Double.parseDouble(tempfoodCost);   
            //     }
            //     fr.close();
            // } catch (Exception e2) {
            //     System.out.println("error e2");
            // }
            
            
            for(int i = 0; i < amountOrdered;i++){
                String tempfoodCost = (String) oTable.getModel().getValueAt(i,1);
                BigDecimal cost = new BigDecimal(tempfoodCost);
                totalcost = totalcost.add(cost);
            }
            System.out.println(totalcost);
            totalLbl.setText("Total: £" + totalcost);
            getOrderID();
            writeToCurrentOrdersFile();
            clearOrderTable();
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
                    
                    System.out.println("file is empty");
                }
            }
            highestID = highestID + 1;
            //System.out.println(highestID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(highestID + " number 1");
        return highestID;
    }
    
    public void writeToCurrentOrdersFile(){
        System.out.println("Writing order to current orders file");
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
    
    
    public void clearMenuTable(){
        int lastRow = demoTable.getRowCount() - 1;
        for(int i = lastRow;i>=0;i--){
            model.removeRow(i);
        }
    }
    
    public void clearOrderTable(){
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
    
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            searchFoodItem();
        }
    }

    public void keyTyped(KeyEvent e) { 
    }
    
    public void keyReleased(KeyEvent e) {
    }
}
