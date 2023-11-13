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
public class StaffMenuPage extends JFrame implements MouseListener, ActionListener, KeyListener{
    
    //declaring variables
    JFrame frame = new JFrame();
    int highestID = 0;
    String foodItem; 
    String foodCost;
    int removeFoodIndex;
    int amountOrdered;
    BigDecimal totalcost = new BigDecimal(0);
    String orderID = "";
    String line= "";
    String[] templine;
    JLabel title = new JLabel("Menu");
    JLabel totalLbl = new JLabel();
    RoundedTextField userSearch = new RoundedTextField(Color.BLACK);
    TextPrompt searchPrompt = new TextPrompt("Search Name / ID", userSearch);
    CustomBackButton backBtn = new CustomBackButton("BACK", Color.decode("#920000"));
    CustomLoginButton confirmOrder = new CustomLoginButton("CONFIRM ORDER", Color.decode("#1464f6"));
    JLabel custOrderlbl = new JLabel("CUSTOMER ORDER");
    String[] filterHeadings = {"FILTER","Starters", "Soups", "Chef Special Dishes", "King Prawn Dishes", "Roast Duck Dishes", 
                                "Chicken, Beef or Char Sui Dishes", "Egg Foo Young and Omelette Dishes", "Curry Dishes", "Chow Mein Dishes", 
                                "Fried Rice Dishes", "Burgers and Sandwiches", "Side Orders", "Desserts", "Kids Meals", "Extra Deals", "Drinks"};
    RoundedComboBox filterBox = new RoundedComboBox(filterHeadings);
    Map<String, int[]> idFilters = new HashMap<>();
    
    
    //declaring tables  
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
    
    StaffMenuPage(){
        initFrame();
        initComponentsintoFrame();
        readMenu();
    }
    
    public void initFrame(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,650);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.decode("#a4cbfe"));
        frame.setVisible(true);
    }
    
    public void initComponentsintoFrame(){
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
        frame.add(title);    
        
        backBtn.setBounds(680,15,80,30);
        backBtn.setFocusable(false);
        backBtn.addActionListener(this);
        frame.add(backBtn);
        
        filterBox.setSelectedIndex(0);
        filterBox.setBounds(280,50,250,20);
        filterBox.addActionListener(this);
        frame.add(filterBox);
        
        custOrderlbl.setBounds(570, 55, 200, 60);
        custOrderlbl.setFont(new Font(null,Font.BOLD,15));
        frame.add(custOrderlbl);
        
        totalLbl.setBounds(500,490,250,40);
        totalLbl.setFont(new Font(null,Font.BOLD,15));
        frame.add(totalLbl);
        
        userSearch.setBounds(30,50,200,20);
        userSearch.addKeyListener(this);
        userSearch.setFont(new Font(null,Font.BOLD,13));
        searchPrompt.setForeground(Color.gray); 
        frame.add(userSearch);
        
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
        frame.add(demoTableScroll);

        
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
        frame.add(oTableScroll);
        confirmOrder.setBounds(500,410,250,40);
        confirmOrder.setFont(new Font(null,Font.BOLD,19));
        confirmOrder.setFocusable(false);
        confirmOrder.addActionListener(this);
        frame.add(confirmOrder);
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
        
        
        String file = "menu.csv";
        boolean searchTermisInt = false;
        String searchTerm = userSearch.getText();
        System.out.println("Searching for " + searchTerm); 
        
        try {
            int intSearchTerm = Integer.parseInt(searchTerm);
            searchTermisInt = true;
            System.out.println("Search term is an integer");

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
    
    public void actionPerformed(ActionEvent e){ //button actions
        if (e.getSource()==backBtn){
            frame.dispose();
            System.out.println("Back to Staff Menu Page");
            new StaffPage();
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
        
        else if(e.getSource()==confirmOrder){
            getOrderID();
            totalcost = new BigDecimal("0.00");
            
            for(int i = 0; i < amountOrdered;i++){
                String tempfoodCost = (String) oTable.getModel().getValueAt(i,1);
                BigDecimal cost = new BigDecimal(tempfoodCost);
                totalcost = totalcost.add(cost);
            }
            System.out.println(totalcost);
            totalLbl.setText("Total: £" + totalcost);
            
            writeToCurrentOrdersFile();
            clearOrderTable();
            amountOrdered = 0;
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
            fW.append(strtotalcost+ ", " + ", " + "\r\n");
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

    public void setLineWrap(boolean wrap) {
        
    }
}