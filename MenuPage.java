//wanted to add buttons to table
//wanted to dynamically name the buttons https://www.tutorialspoint.com/how-can-we-change-the-jbutton-text-dynamically-in-java

//DONE todo ADD TABBEDPANE
//DONE todo CHANGE FRAME TO PANEL

import javax.swing.*;
import java.awt.*;

import javax.swing.table.*;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
    String[] filterHeadings = {"FILTER","Starters", "Soups", "Chef Special Dishes", "King Prawn Dishes", "Roast Duck Dishes", 
                                "Chicken, Beef or Char Sui Dishes", "Egg Foo Young and Omelette Dishes", "Curry Dishes", "Chow Mein Dishes", 
                                "Fried Rice Dishes", "Burgers and Sandwiches", "Side Orders", "Desserts", "Kids Meals", "Extra Deals", "Drinks"};
    JComboBox<String> filterBox = new JComboBox<String>(filterHeadings);
    
    Map<String, int[]> idFilters = new HashMap<>();

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
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(1,1));
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
        idFilters.put("FILTER", new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,
            37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,
            80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,
            117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,139,140});
        idFilters.put("Starters", new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14});
        idFilters.put("Soups", new int[]{15,16,17,18,19});
        idFilters.put("Chef Special Dishes", new int[]{20,21,22,23,24,25,26,27,28,29,30,31,32});
        idFilters.put("King Prawn Dishes", new int[]{33,34,35,36,37,38,39,40,41,42,43});
        idFilters.put("Roast Duck Dishes", new int[]{44,45,46,47,48,49,50,51,52});
        idFilters.put("Chicken, Beef or Char Sui Dishes", new int[]{53,54,55,56,57,58,59,60,61});
        idFilters.put("Egg Foo Young and Omelette Dishes", new int[]{62,63,64,65,66,67,68,69,70,71});
        idFilters.put("Curry Dishes", new int[]{72,73,74,75,76});
        idFilters.put("Chow Mein Dishes", new int[]{77,78,79,80,81});
        idFilters.put("Fried Rice Dishes", new int[]{82,83,84,85,86,87,88});
        idFilters.put("Burgers and Sandwiches", new int[]{89,90,91,92,93,94,95,96,97});
        idFilters.put("Side Orders", new int[]{98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,
            117,118,119,120,121,122});
        idFilters.put("Desserts", new int[]{123,124});
        idFilters.put("Kids Meals", new int[]{125,126});
        idFilters.put("Extra Deals", new int[]{127,128,129,130,131,132,133,134});
        idFilters.put("Drinks", new int[]{135,136,137,138,139,140});

        
        title.setBounds(380, 10, 70, 30);
        title.setFont(new Font(null,Font.BOLD,20));
        menupanel.add(title);    
        
        filterBox.setSelectedIndex(0);
        filterBox.setBounds(280,50,250,20);
        filterBox.addActionListener(this);
        menupanel.add(filterBox);
        
        custOrderlbl.setBounds(570, 55, 200, 60);
        custOrderlbl.setFont(new Font(null,Font.BOLD,15));
        menupanel.add(custOrderlbl);
        
        logoutBtn.setBounds(700,10,70,30);
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
        oTableScroll.setBounds(500,110,250,280);
        oTable.getTableHeader().setReorderingAllowed(false);
        oTable.getTableHeader().setResizingAllowed(false);
        oTable.getColumnModel().getColumn(0).setPreferredWidth(216);
        oTable.getColumnModel().getColumn(1).setPreferredWidth(34);
        menupanel.add(oTableScroll);

        confirmOrder.setBounds(500,410,250,40);
        confirmOrder.setFont(new Font(null,Font.BOLD,19));
        confirmOrder.setFocusable(false);
        confirmOrder.addActionListener(this);
        menupanel.add(confirmOrder);
    }

    public void initComponentsintoCurrentOrdersPanel(){
        title2.setBounds(330,10,250,30); 
        title2.setFont(new Font(null,Font.BOLD,20));
        currentOrderpanel.add(title2);
        
        orderBtn.setBounds(190,450,90,50);
        orderBtn.setFocusable(false);
        orderBtn.addActionListener(this);
        currentOrderpanel.add(orderBtn);
        
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
        
        
        String file = "menu.csv";
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
        String file = "menu.csv";
        
        try {
            try (BufferedReader bR = new BufferedReader(new FileReader(file))) {
                Object[] tableLines = bR.lines().toArray();
                
                for (int i = 0; i < tableLines.length;i++){
                    String line = tableLines[i].toString().trim();
                    String[] dataRow = line.split(",");
                       model.addRow(dataRow);
                }
                bR.close();
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
            this.dispose();
            System.out.println("Logging out of customer account");
            new LoginPage(loginDetails.getLoginDetail());
        }
        
        if (e.getSource()==filterBox){
            
            String selectedHeading = (String)filterBox.getSelectedItem();
            int[] idsToDisplay = idFilters.get(selectedHeading);
            
            clearMenuTable();
            
            try (BufferedReader bR = new BufferedReader(new FileReader("menu.csv"))) {
                Object[] tableLines = bR.lines().toArray();
                
                // Iterate through each line of the menu
                for (int i = 0; i < tableLines.length;i++){
                    String line = tableLines[i].toString().trim();
                    String[] dataRow = line.split(",");
                    int id = Integer.parseInt(dataRow[0]);
                    // Check if the menu item's ID is in the list of IDs to display
                    if (Arrays.stream(idsToDisplay).anyMatch(x -> x == id)){
                        model.addRow(dataRow);
                    }
                }
                bR.close();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
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
                    fr.append(tempfoodItem+","+tempfoodCost);
                    fr.append("\r\n");
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
