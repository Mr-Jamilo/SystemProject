import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Base64;
import javax.swing.RowSorter;
import java.util.List;

public class CustomerLoginsPage implements ActionListener, KeyListener{
    JFrame frame = new JFrame();
    JLabel title = new JLabel("Customer Details");
    CustomBackButton backBtn = new CustomBackButton("BACK", Color.decode("#920000"));
    RoundedTextField userSearch = new RoundedTextField(Color.BLACK);
    TextPrompt searchPrompt = new TextPrompt("Search Forename / ID", userSearch);
    String[] sortHeadings = {"SORT","ID","Forename","Surname"};
    RoundedComboBox sortBox = new RoundedComboBox(sortHeadings);

    String[] headings= {"ID","Forename","Surname","Email","Password","Points"}; //declaring table 
    DefaultTableModel model = new DefaultTableModel(headings,0);
    JTable customerTable = new JTable(model)
    {   
        public boolean isCellEditable(int row, int column) {                
            return false;               
        }
    };
    JScrollPane customerTableScroll = new JScrollPane(customerTable);

    CustomerLoginsPage(){
        initFrame();
        readCustomerDetails();
    }

    public void initFrame(){
        title.setBounds(300,15,300,40);
        title.setFont(new Font(null,Font.BOLD,20));
        frame.add(title);

        backBtn.setBounds(680,15,80,30);
        backBtn.setFocusable(false);
        backBtn.addActionListener(this);
        frame.add(backBtn);

        userSearch.setBounds(30,70,200,20);
        userSearch.addKeyListener(this);
        searchPrompt.setForeground(Color.gray); 
        frame.add(userSearch);
        
        sortBox.setSelectedIndex(0);
        sortBox.setBounds(280,70,200,20);
        sortBox.addActionListener(this);
        frame.add(sortBox);
        
        TableCellRenderer rendererFromHeader = customerTable.getTableHeader().getDefaultRenderer();
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) rendererFromHeader;
        headerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        customerTable.getTableHeader().setDefaultRenderer(new TableHeaderColour());
        customerTableScroll.setBounds(30,100,700,400);
        customerTableScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        customerTable.getTableHeader().setReorderingAllowed(false);
        customerTable.getTableHeader().setResizingAllowed(false);
        customerTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        customerTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        frame.add(customerTableScroll);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(800,650);
        frame.getContentPane().setBackground(Color.decode("#a4cbfe"));
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public void readCustomerDetails(){
        //reference: https://youtu.be/L2xczUN9aI0
        
        System.out.println("Displaying details");
        String file = "logins.csv";
        try {
            try (BufferedReader bR = new BufferedReader(new FileReader(file))) {
                Object[] tableLines = bR.lines().toArray();
                long start = System.nanoTime();
                for (int i = 2; i < tableLines.length;i++){
                    String line = tableLines[i].toString().trim();
                    String[] dataRow = line.split(",");
                    for (int j = 0; j < dataRow.length; j++){
                        if (j == 3){
                            String encryptedEmail = dataRow[j];
                            byte[] decryptedEmail = Base64.getDecoder().decode(new String(encryptedEmail));
                            dataRow[j] = new String(decryptedEmail);
                        }
                    }
                    model.addRow(dataRow);
                }
                long end = System.nanoTime();
                bR.close();
                System.out.println("Time taken to decode customer details: " + (end - start) + " nanoseconds");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void searchCustomer(){
        String file = "logins.csv";
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

        
        clearLoginTable();
        try (BufferedReader bR = new BufferedReader(new FileReader(file))) {
            Object[] tableLines = bR.lines().toArray();
            
            for (int i = 2; i < tableLines.length;i++){
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
    
    public void clearLoginTable(){
        int rowCount = model.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==backBtn){
            frame.dispose();
            System.out.println("Back to Staff Page");
            new StaffPage();
        }
        if (e.getSource()==sortBox){
            String sortChoice = sortBox.getSelectedItem().toString();
            System.out.println("Sorting by " + sortChoice);
            
            if (sortChoice.equals("ID")){
                TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
                customerTable.setRowSorter(sorter);
                //sorter.setSortable(0, false);
                sorter.setSortable(1, false);
                sorter.setSortable(2, false);
                sorter.setSortable(3, false);
                sorter.setSortable(4, false);
                sorter.setSortable(5, false);
                List<RowSorter.SortKey> sortKeys = new ArrayList<>();
                sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
                sorter.setSortKeys(sortKeys);
                sorter.sort();
            }
            if (sortChoice.equals("Forename")){
                TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
                customerTable.setRowSorter(sorter);
                sorter.setSortable(0, false);
                //sorter.setSortable(1, false);
                sorter.setSortable(2, false);
                sorter.setSortable(3, false);
                sorter.setSortable(4, false);
                sorter.setSortable(5, false);

                List<RowSorter.SortKey> sortKeys = new ArrayList<>();
                sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));
                sorter.setSortKeys(sortKeys);
                sorter.sort();
            }
            if (sortChoice.equals("Surname")){
                TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
                customerTable.setRowSorter(sorter);
                sorter.setSortable(0, false);
                sorter.setSortable(1, false);
                //sorter.setSortable(2, false);
                sorter.setSortable(3, false);
                sorter.setSortable(4, false);
                sorter.setSortable(5, false);
                List<RowSorter.SortKey> sortKeys = new ArrayList<>();
                sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING));
                sorter.setSortKeys(sortKeys);
                sorter.sort();
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            searchCustomer();
        }
    }

    public void keyTyped(KeyEvent e) { 
    }
    
    public void keyReleased(KeyEvent e) {
    }
}
