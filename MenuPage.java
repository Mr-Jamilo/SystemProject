//wanted to add buttons to table
//wanted to dynamically name the buttons https://www.tutorialspoint.com/how-can-we-change-the-jbutton-text-dynamically-in-java

//DONE todo ADD TABBEDPANE
//DONE todo CHANGE FRAME TO PANEL

import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class MenuPage extends JFrame implements MouseListener, ActionListener, KeyListener{
    LoginDetails loginDetails = new LoginDetails(); //declaring gui
    JPanel menupanel = new JPanel(null);
    JPanel currentOrderpanel = new JPanel(null);
    JPanel rewardspanel = new JPanel(null);
    JFrame menuFrame = new JFrame();
    JTabbedPane tabsPane = new JTabbedPane();
    JPanel box = new JPanel();
    
    //declaring variables for menu panel
    String foodItem; 
    String foodCost;
    String strCustomerID = "";
    String strCustomerName = "";
    int currentCustPoints = 0;
    int removeFoodIndex;
    int amountOrdered;
    BigDecimal totalcost = new BigDecimal(0);
    String orderID = "";
    String line= "";
    String[] templine;
    int highestID = 0;
    JLabel title = new JLabel("Menu");
    JLabel totalLbl = new JLabel();
    JLabel earnedPointsLbl = new JLabel();
    RoundedTextField userSearch = new RoundedTextField(Color.BLACK);
    TextPrompt searchPrompt = new TextPrompt("Search Name / ID", userSearch);
    CustomBackButton menuPanelLogoutBtn = new CustomBackButton("LOG OUT", Color.decode("#920000"));
    CustomLoginButton confirmOrder = new CustomLoginButton("CONFIRM ORDER", Color.decode("#1464f6"));
    JLabel custOrderlbl = new JLabel("YOUR ORDER");
    String[] filterHeadings = {"FILTER","Starters", "Soups", "Chef Special Dishes", "King Prawn Dishes", "Roast Duck Dishes", 
                                "Chicken, Beef or Char Sui Dishes", "Egg Foo Young and Omelette Dishes", "Curry Dishes", "Chow Mein Dishes", 
                                "Fried Rice Dishes", "Burgers and Sandwiches", "Side Orders", "Desserts", "Kids Meals", "Extra Deals", "Drinks"};
    RoundedComboBox filterBox = new RoundedComboBox(filterHeadings);
    Map<String, int[]> idFilters = new HashMap<>();
    
    //declaring variables for rewards panel
    JLabel title3 = new JLabel("Rewards");
    int totalPointsClaimed = 0;
    int pointsCost = 0;
    JLabel custPointsLbl = new JLabel();
    JLabel pointstoNextLbl = new JLabel();
    CustomBackButton rewardsPanelLogoutBtn = new CustomBackButton("LOG OUT", Color.decode("#920000"));
    CustomLoginButton claimRewardbtn = new CustomLoginButton("CLAIM REWARD", Color.decode("#51c151"));
    
    //declaring variables for current orders panel
    int amountOrdered2;
    JLabel title2 = new JLabel("Current Orders");
    CustomBackButton currentOrdersPanelLogoutBtn = new CustomBackButton("LOG OUT", Color.decode("#920000"));
    
    
    //declaring tables for menu panel   
    String[] headings= {"Food ID","Food Name","Cost"}; 
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
    
    //declare tables for current orders panel
    String[] currentIDHeadings = {"Order ID"};
    DefaultTableModel currentIDModel = new DefaultTableModel(currentIDHeadings,0);
    JTable currentIDTable = new JTable(currentIDModel){
        public boolean isCellEditable(int row, int column){
            return false;
        }
    };
    JScrollPane currentIDScrollTable = new JScrollPane(currentIDTable);

    String[] currentOrdersHeadings = {"Order"};
    DefaultTableModel currentOrdersModel = new DefaultTableModel(currentOrdersHeadings,0);
    JTable currentOrdersTable = new JTable(currentOrdersModel){
        public boolean isCellEditable(int row, int column){
            return false;
        }
    };
    JScrollPane currentOrdersTableScroll = new JScrollPane(currentOrdersTable);

    //declare table for rewards panel
    String rewardsHeadings[] = {"Food","Points"};
    DefaultTableModel rewardsModel = new DefaultTableModel(rewardsHeadings,0);
    JTable rewardsTable = new JTable(rewardsModel){
        public boolean isCellEditable(int row, int column){
            return false;
        }
    };
    JScrollPane rewardsTableScroll = new JScrollPane(rewardsTable);
    
    MenuPage(String userEmail, String userPassword){
        initFrame();
        readMenu();
        readCurrentCustomerOrdersID();
        getCustomerIdandName(userEmail, userPassword);
        readRewards();
        
    }
    
    public void initFrame(){
        this.add(tabsPane);
        this.setSize(800,650);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(1,1));
        this.getContentPane().setBackground(Color.decode("#a4cbfe"));
        this.setVisible(true);

        initTabs();
    }
    
    public void initTabs(){
        initComponentsintoMenuPanel();
        initComponentsintoRewardsPanel();
        initComponentsintoCurrentOrdersPanel();
        tabsPane.addTab("Menu", menupanel);
        tabsPane.addTab("Rewards", rewardspanel);
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
        
        menuPanelLogoutBtn.setBounds(680,15,80,30);
        menuPanelLogoutBtn.setFocusable(false);
        menuPanelLogoutBtn.addActionListener(this);
        menupanel.add(menuPanelLogoutBtn);
        
        totalLbl.setBounds(500,490,250,40);
        totalLbl.setFont(new Font(null,Font.BOLD,15));
        menupanel.add(totalLbl);
        
        userSearch.setBounds(30,50,200,20);
        userSearch.addKeyListener(this);
        userSearch.setFont(new Font(null,Font.BOLD,13));
        searchPrompt.setForeground(Color.gray); 
        menupanel.add(userSearch);
        
        TableCellRenderer rendererFromHeader = demoTable.getTableHeader().getDefaultRenderer();
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) rendererFromHeader;
        headerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        demoTable.getTableHeader().setDefaultRenderer(new TableHeaderColour());
        demoTable.setGridColor(Color.BLACK);
        demoTable.addMouseListener(this);
        demoTableScroll.setBounds(30,80,400,480);
        demoTableScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        demoTable.getTableHeader().setReorderingAllowed(false);
        demoTable.getTableHeader().setResizingAllowed(false);
        demoTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        demoTable.getColumnModel().getColumn(1).setPreferredWidth(316);
        demoTable.getColumnModel().getColumn(2).setPreferredWidth(34);
        menupanel.add(demoTableScroll);

        
        TableCellRenderer rendererFromoHeader = oTable.getTableHeader().getDefaultRenderer();
        DefaultTableCellRenderer oheaderRenderer = (DefaultTableCellRenderer) rendererFromoHeader;
        oheaderRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        oTable.getTableHeader().setDefaultRenderer(new TableHeaderColour());
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

        menupanel.setBackground(Color.decode("#a4cbfe"));
    }
    
    public void initComponentsintoCurrentOrdersPanel(){
        title2.setBounds(330,10,250,30); 
        title2.setFont(new Font(null,Font.BOLD,20));
        currentOrderpanel.add(title2);
        
        TableCellRenderer rendererFromHeader = currentIDTable.getTableHeader().getDefaultRenderer();
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) rendererFromHeader;
        headerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        currentIDTable.getTableHeader().setDefaultRenderer(new TableHeaderColour());
        currentIDTable.addMouseListener(this);
        currentIDScrollTable.setBounds(30,120,100,200);
        currentIDTable.getTableHeader().setReorderingAllowed(false);
        currentIDTable.getTableHeader().setResizingAllowed(false);
        currentOrderpanel.add(currentIDScrollTable);
        
        TableCellRenderer rendererFromCurrentOrdersHeader = currentOrdersTable.getTableHeader().getDefaultRenderer();
        DefaultTableCellRenderer currentOrdersheaderRenderer = (DefaultTableCellRenderer) rendererFromCurrentOrdersHeader;
        currentOrdersheaderRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        currentOrdersTable.getTableHeader().setDefaultRenderer(new TableHeaderColour());
        currentOrdersTableScroll.setBounds(200,80,400,280);
        currentOrdersTable.getTableHeader().setReorderingAllowed(false);
        currentOrdersTable.getTableHeader().setResizingAllowed(false);
        currentOrderpanel.add(currentOrdersTableScroll);

        currentOrdersPanelLogoutBtn.setBounds(680,15,80,30);
        currentOrdersPanelLogoutBtn.setFocusable(false);
        currentOrdersPanelLogoutBtn.addActionListener(this);
        currentOrderpanel.add(currentOrdersPanelLogoutBtn);

        currentOrderpanel.setBackground(Color.decode("#a4cbfe"));
    }
    
    public void initComponentsintoRewardsPanel(){
        title3.setBounds(340, 10, 100, 30);
        title3.setFont(new Font(null,Font.BOLD,20));
        rewardspanel.add(title3);
        
        custPointsLbl.setBounds(535,260,250,30);
        custPointsLbl.setFont(new Font(null,Font.BOLD,17));
        rewardspanel.add(custPointsLbl);

        pointstoNextLbl.setBounds(480,300,250,30);
        pointstoNextLbl.setFont(new Font(null,Font.BOLD,17));
        rewardspanel.add(pointstoNextLbl);

        TableCellRenderer rendererFromHeader = rewardsTable.getTableHeader().getDefaultRenderer();
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) rendererFromHeader;
        headerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        rewardsTable.getTableHeader().setDefaultRenderer(new TableHeaderColour());
        rewardsTable.addMouseListener(this);
        rewardsTableScroll.setBounds(30,80,400,373);
        rewardsTable.setRowHeight(70);
        rewardsTable.getColumnModel().getColumn(0).setCellRenderer(new WrappingTableCellRenderer());
        rewardsTableScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        rewardsTable.getTableHeader().setReorderingAllowed(false);
        rewardsTable.getTableHeader().setResizingAllowed(false);
        rewardsTable.getColumnModel().getColumn(0).setPreferredWidth(316);
        rewardsTable.getColumnModel().getColumn(1).setPreferredWidth(20);
        rewardspanel.add(rewardsTableScroll);

        claimRewardbtn.setBounds(500,400,250,40);
        claimRewardbtn.setFont(new Font(null,Font.BOLD,19));
        claimRewardbtn.setFocusable(false);
        claimRewardbtn.addActionListener(this);
        rewardspanel.add(claimRewardbtn);

        rewardsPanelLogoutBtn.setBounds(680,15,80,30);
        rewardsPanelLogoutBtn.setFocusable(false);
        rewardsPanelLogoutBtn.addActionListener(this);
        rewardspanel.add(rewardsPanelLogoutBtn);

        rewardspanel.setBackground(Color.decode("#a4cbfe"));
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
    }
    
    public void searchFoodItem(){
        //search by reading the file
        long start = System.nanoTime();
        
        String file = "menu.csv";
        boolean searchTermisInt = false;
        String searchTerm = userSearch.getText();
        if (searchTerm.equals("")){
            System.out.println("Search term is empty, refreshing the menu");
        }
        else{
            System.out.println("Searching for " + searchTerm); 
        }

        try {
            int intSearchTerm = Integer.parseInt(searchTerm);
            searchTermisInt = true;
            //System.out.println("Search term is an integer");

        } catch (Exception e) {
            System.out.println("Search term is not an integer");
            //searchTermisInt = false;
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
        long finish = System.nanoTime();
        System.out.println("Time taken to search: " + (finish - start) + " nanoseconds");
    }
    
    public void readRewards(){
        System.out.println("Displaying rewards");
        String file = "rewards.txt";
        
        try {
            try (BufferedReader bR = new BufferedReader(new FileReader(file))) {
                Object[] tableLines = bR.lines().toArray();
                
                for (int i = 0; i < tableLines.length;i++){
                    String line = tableLines[i].toString().trim();
                    String[] dataRow = line.split("/");
                    rewardsModel.addRow(dataRow);
                }
                bR.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void mouseClicked(MouseEvent mevt){ //table click event  
        removeFoodIndex = oTable.getSelectedRow();
        
        if (mevt.getClickCount() == 2 && mevt.getButton() == MouseEvent.BUTTON1 && mevt.getSource() == demoTable){
            int selectedRowIndex = demoTable.getSelectedRow();
            foodItem = (String) demoTable.getModel().getValueAt(selectedRowIndex, 1);
            foodCost = (String) demoTable.getModel().getValueAt(selectedRowIndex, 2);
            if (foodItem != null && foodCost != null) {
                System.out.println("Added " + foodItem + " to order");
                String[] data = {foodItem,foodCost};
                oModel.addRow(data);
                amountOrdered = amountOrdered + 1;
            }
        }
        if (mevt.getClickCount() == 2 && mevt.getButton() == MouseEvent.BUTTON1 && mevt.getSource() == oTable){
            int tempAmountOrdered = amountOrdered;
            if (removeFoodIndex != -1) {
                String DelFoodCost = (String) oTable.getModel().getValueAt(removeFoodIndex, 1);
                if (DelFoodCost.equals("0.0")){
                    int result = JOptionPane.showOptionDialog(null, "Are you sure you want to unclaim your reward?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (result == JOptionPane.YES_OPTION){
                        // iterate over rows in reverse order to avoid index shifting
                        for (int i = tempAmountOrdered - 1; i >= 0; i--) {
                            if (DelFoodCost.equals(oTable.getModel().getValueAt(i, 1))) {
                                oModel.removeRow(i);
                                amountOrdered = amountOrdered - 1;
                            }
                        }
                        currentCustPoints = currentCustPoints + totalPointsClaimed;
                        custPointsLbl.setText("Points: " + currentCustPoints);
                        getPointsToNextReward();
                        updateCustomerPoints();
                        totalPointsClaimed = 0;    
                    }
                    else{
                        // do nothing
                    }
                } else {
                    try {
                        System.out.println("Removed " + foodItem + " from order");
                        oModel.removeRow(removeFoodIndex);
                        amountOrdered = amountOrdered - 1;
                    } catch (Exception a) {
                        System.out.println("error");
                    }
                }
            }
        }

        if (mevt.getClickCount() == 2 && mevt.getButton() == MouseEvent.BUTTON1 && mevt.getSource() == currentIDTable){
            System.out.println("Reading Order");
            clearCurrentOrdersTable();
            //readCurrentCustomerOrders();
            int selectedRowIndex = currentIDTable.getSelectedRow();
            String orderID = currentIDModel.getValueAt(selectedRowIndex,0).toString();
            String file = "custorders.csv";
            try (BufferedReader bR = new BufferedReader(new FileReader(file))) {
                Object[] tableLines =  bR.lines().toArray();
                for (int i = 0; i < tableLines.length;i++){
                    String line = tableLines[i].toString();
                    String[] dataRow = line.split(",");
                    if (dataRow[0].equals(orderID)){
                        for (int j = 1; j < dataRow.length - 1;j++){
                            currentOrdersModel.addRow(new Object[]{dataRow[j]});
                            amountOrdered = amountOrdered + 1;
                        }
                    }
                }
            } catch (Exception ex) {
                System.out.println("Error reading file");
            }
        }
        if (mevt.getButton() == MouseEvent.BUTTON1 && mevt.getSource() == rewardsTable){
            int selectedRowIndex = rewardsTable.getSelectedRow();
            String strPointsCost = rewardsModel.getValueAt(selectedRowIndex, 1).toString();
            pointsCost = Integer.parseInt(strPointsCost);
        }
    } 
    
    public void actionPerformed(ActionEvent e){ //button actions
        if (e.getSource()==menuPanelLogoutBtn || e.getSource()==rewardsPanelLogoutBtn || e.getSource()==currentOrdersPanelLogoutBtn){
            this.dispose();
            System.out.println("Logging out of customer account");
            new LoginPage(loginDetails.getLoginDetails());
        }
        
        if (e.getSource()==filterBox){
            long start = System.nanoTime();
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
            long end = System.nanoTime();
            System.out.println("Filtering took " + (end - start) + " nanoseconds");
        }
        
        else if(e.getSource()==confirmOrder){
            
            getOrderID();
            BigDecimal totalCost = new BigDecimal("0.00");
            totalcost = new BigDecimal("0.00");
            try {
                FileWriter fr = new FileWriter("custorders.csv", true);
                String strhighestID = Integer.toString(highestID);
                fr.append(strhighestID + ",");
                if(amountOrdered > oTable.getRowCount()) {
                    amountOrdered = oTable.getRowCount();
                }
                
                long startAddtoArray = System.nanoTime();
                
                for(int i = 0;i < amountOrdered;i++){
                    String tempfoodItem = (String) oTable.getModel().getValueAt(i,0);
                    String tempfoodCost = (String) oTable.getModel().getValueAt(i,1);
                    BigDecimal decimalFoodcost = new BigDecimal(tempfoodCost);
                    totalCost = totalCost.add(decimalFoodcost);
                    fr.append(tempfoodItem+",");
                }
                
                long endAddtoArray = System.nanoTime();
                System.out.println("Adding food items to food list array took " + (endAddtoArray - startAddtoArray) + " nanoseconds");
                
                fr.append(totalCost.toString() + "\r\n");
                fr.close();
            } 
            catch (Exception e1) {
                System.out.println("error e1");
            }
            readCurrentCustomerOrdersID();
            
            
            for(int i = 0; i < amountOrdered;i++){
                String tempfoodCost = (String) oTable.getModel().getValueAt(i,1);
                BigDecimal cost = new BigDecimal(tempfoodCost);
                totalcost = totalcost.add(cost);
            }
            System.out.println(totalcost);
            totalLbl.setText("Total: £" + totalcost);
            
            writeToCurrentOrdersFile();
            clearOrderTable();
            double dbletemprewardedpoints = totalcost.doubleValue() * 100;
            int inttemprewardedpoints = (int) dbletemprewardedpoints;
            System.out.println("Points gained: " + inttemprewardedpoints);
            currentCustPoints = currentCustPoints + inttemprewardedpoints;
            System.out.println("Total Points: " + currentCustPoints);

            custPointsLbl.setText("Current Points: " + currentCustPoints);
            getPointsToNextReward();
            updateCustomerPoints();
            amountOrdered = 0;
        }

        if (e.getSource() == claimRewardbtn){
            int selectedRowIndex = rewardsTable.getSelectedRow();
            if (selectedRowIndex == -1){
                JOptionPane.showMessageDialog(null, "Please select a reward");
                return;
            }
            
            
            if (currentCustPoints >= pointsCost){
                addRewardToOrder();
                totalPointsClaimed = totalPointsClaimed + pointsCost;
                currentCustPoints = currentCustPoints - pointsCost;
                custPointsLbl.setText("Current Points: " + currentCustPoints);
                getPointsToNextReward();
                updateCustomerPoints();
                JOptionPane.showMessageDialog(null, "Reward added to order");
            }
            else{
                JOptionPane.showMessageDialog(null, "Not enough points to claim reward");
            }
        }
    }
    
    public void addRewardToOrder(){
        int selectedRowIndex = rewardsTable.getSelectedRow();
        if (selectedRowIndex == 0){
            String[] data1 = {"Sweet and Sour Chicken Balls(6)","0.0"};
            String[] data2 = {"Beef Chop Suey","0.0"};
            String[] data3 = {"Egg Fried Rice(Small)","0.0"};
            oModel.addRow(data1);
            oModel.addRow(data2);
            oModel.addRow(data3);
            amountOrdered = amountOrdered + 3;
        }
        else if (selectedRowIndex == 1){
            String[] data1 = {"Prawn Toast","0.0"};
            String[] data2 = {"Cripsy Won Ton","0.0"};
            String[] data3 = {"Barbecued Spare Ribs","0.0"};
            String[] data4 = {"Vegetarian Mini Spring Rolls","0.0"};
            String[] data5 = {"Cripsy Armoatic Duck(Quarter)","0.0"};
            String[] data6 = {"Chicken in Hong Kong Style","0.0"};
            String[] data7 = {"Beef with Mixed Vegetables","0.0"};
            String[] data8 = {"Young Chow Fried Rice(Large)","0.0"};
            String[] data9 = {"Prawn Crackers","0.0"};
            oModel.addRow(data1);
            oModel.addRow(data2);
            oModel.addRow(data3);
            oModel.addRow(data4);
            oModel.addRow(data5);
            oModel.addRow(data6);
            oModel.addRow(data7);
            oModel.addRow(data8);
            oModel.addRow(data9);
            amountOrdered = amountOrdered + 9;
        }
        else if (selectedRowIndex == 2){
            String[] data1 = {"Chicken and Sweetcorn Soup","0.0"};
            String[] data2 = {"Chicken and Sweetcorn Soup","0.0"};
            String[] data3 = {"Barbecued Spare Ribs(4)","0.0"};
            String[] data4 = {"Chicken and Mushrooms","0.0"};
            String[] data5 = {"Beef and Green Peppers in Blackbean Sauce","0.0"};
            String[] data6 = {"Egg Fried Rice(Large)","0.0"};
            String[] data7 = {"Prawn Crackers","0.0"};
            oModel.addRow(data1);
            oModel.addRow(data2);
            oModel.addRow(data3);
            oModel.addRow(data4);
            oModel.addRow(data5);
            oModel.addRow(data6);
            oModel.addRow(data7);
            amountOrdered = amountOrdered + 7; 
        }
        else if (selectedRowIndex == 3){
            String[] data1 = {"Chicken and Sweetcorn Soup","0.0"};
            String[] data2 = {"Chicken and Sweetcorn Soup","0.0"};
            String[] data3 = {"Chicken and Sweetcorn Soup","0.0"};
            String[] data4 = {"Barbecued Spare Ribs(6)","0.0"};
            String[] data5 = {"Chicken Chop Suey","0.0"};
            String[] data6 = {"Beef in Satay Sauce","0.0"};
            String[] data7 = {"Mixed Meat with Mushrooms","0.0"};
            String[] data8 = {"Young Chow Fried Rice(Small)","0.0"};
            String[] data9 = {"Young Chow Fried Rice(Large)","0.0"};
            String[] data10 = {"Prawns Crackers","0.0"};
            oModel.addRow(data1);
            oModel.addRow(data2);
            oModel.addRow(data3);
            oModel.addRow(data4);
            oModel.addRow(data5);
            oModel.addRow(data6);
            oModel.addRow(data7);
            oModel.addRow(data8);
            oModel.addRow(data9);
            oModel.addRow(data10);
            amountOrdered = amountOrdered + 10;
        }
        else if (selectedRowIndex == 4){
            String[] data1 = {"Sweet and Sour Chicken Balls(8)","0.0"};
            String[] data2 = {"Barbecued Spare Ribs(8)","0.0"};
            String[] data3 = {"Roast Pork Chop Suey","0.0"};
            String[] data4 = {"Beef and Mushrooms","0.0"};
            String[] data5 = {"King Prawns in Hong Kong Style","0.0"};
            String[] data6 = {"Salt and Pepper Chicken","0.0"};
            String[] data7 = {"Young Chow Fried Rice(Large)","0.0"};
            String[] data8 = {"Young Chow Fried Rice(Large)","0.0"};
            String[] data9 = {"Prawn Crackers","0.0"};
            oModel.addRow(data1);
            oModel.addRow(data2);
            oModel.addRow(data3);
            oModel.addRow(data4);
            oModel.addRow(data5);
            oModel.addRow(data6);
            oModel.addRow(data7);
            oModel.addRow(data8);
            oModel.addRow(data9);
            amountOrdered = amountOrdered + 9;
        }
    }
    
    public void updateCustomerPoints(){
        //Read the file and find the desired line to update
        List<String> lines = new ArrayList<>();
        try (BufferedReader bR = new BufferedReader(new FileReader("logins.csv"))) {
            String line;
            while((line = bR.readLine()) != null){
                String[] templine = line.split(",");
                if(templine[0].equals(strCustomerID)){
                // Update the desired value in the line
                templine[5] = Integer.toString(currentCustPoints);
                line = String.join(",", templine);
            }
            lines.add(line);
            }
            bR.close();
        } catch (IOException ex) {
        ex.printStackTrace();
        }

        // Write the entire file again with the updated line
        try (BufferedWriter bW = new BufferedWriter(new FileWriter("logins.csv"))){
            for (String line : lines) {
                bW.write(line);
                bW.newLine();
            }
            bW.close();
        } catch (IOException ex) {
            ex.printStackTrace();
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
                    
                    //System.out.println("file is empty");
                }
            }
            highestID = highestID + 1;
            //System.out.println(highestID);
            bR.close();
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
            fW.append(strtotalcost + "," + strCustomerID + "," + strCustomerName + "\r\n");
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
    
    public void getCustomerIdandName(String userEmail,String userPassword){
        System.out.println(userEmail + "," + userPassword);
        try (BufferedReader bR = new BufferedReader(new FileReader("logins.csv"))) {
            while((line = bR.readLine()) != null){
                templine = line.split(",");
                if(templine[3].equals(userEmail) && templine[4].equals(userPassword)){
                    strCustomerID = templine[0];
                    strCustomerName = templine[1];
                    //System.out.println(strCustomerID);
                }
            }
            bR.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getCustPoints(strCustomerID);
    }

    public String getCustPoints(String strCustomerID2){
        System.out.println("getting customer points");
        System.out.println("customer id: " + strCustomerID);
        try (BufferedReader bR = new BufferedReader(new FileReader("logins.csv"))) {
            while((line = bR.readLine()) != null){
                templine = line.split(",");
                if(templine.length > 0 && templine[0].equals(strCustomerID)){
                    String strCustomerPoints = templine[5];
                    System.out.println(strCustomerPoints);
                    currentCustPoints = Integer.parseInt(strCustomerPoints);
                    System.out.println(currentCustPoints);
                }
            }
            bR.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        custPointsLbl.setText("Current Points: " + currentCustPoints);
        getPointsToNextReward();
        return strCustomerID;
    }
    
    public void getPointsToNextReward(){
        if (currentCustPoints >= 5000){
            pointstoNextLbl.setText("Points to next reward: " + 0);
        }
        else if (currentCustPoints >= 4000){
            int custpointslefttillnextreward = currentCustPoints - 4000;
            pointstoNextLbl.setText("Points to next reward: " + custpointslefttillnextreward);
        }
        else if (currentCustPoints >= 3000){
            int custpointslefttillnextreward = currentCustPoints - 3000;
            pointstoNextLbl.setText("Points to next reward: " + custpointslefttillnextreward);
        }
        else if (currentCustPoints >= 2000){
            int custpointslefttillnextreward = currentCustPoints - 2000;
            pointstoNextLbl.setText("Points to next reward: " + custpointslefttillnextreward);
        }
        else if (currentCustPoints >= 1000){
            int custpointslefttillnextreward = currentCustPoints - 1000;
            pointstoNextLbl.setText("Points to next reward: " + custpointslefttillnextreward);
        }
    }
    
    public void readCurrentCustomerOrdersID(){
        clearCustomerOrdersIDTable();
        
        String file = "custorders.csv";
        try (BufferedReader bR = new BufferedReader(new FileReader(file))) {
            Object[] tableLines =  bR.lines().toArray();
            for (int i = 0; i < tableLines.length;i++){
                String line = tableLines[i].toString().trim();
                String[] dataRow = line.split(",");
                currentIDModel.addRow(dataRow);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void clearCustomerOrdersIDTable(){
        int lastRow = currentIDModel.getRowCount() - 1;
        for(int i = lastRow;i>=0;i--){
            currentIDModel.removeRow(i);
        }
    }
    
    public void clearCurrentOrdersTable(){
        int lastRow = currentOrdersModel.getRowCount() - 1;
        for(int i = lastRow;i>=0;i--){
            currentOrdersModel.removeRow(i);
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

    public void setLineWrap(boolean wrap) {
        
    }
}