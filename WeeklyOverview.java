import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeeklyOverview implements ActionListener, MouseListener{
    JFrame frame = new JFrame();
    JLabel title = new JLabel("Weekly Overview");
    CustomBackButton backBtn = new CustomBackButton("BACK", Color.decode("#920000"));
    RoundedButton addExpenseBtn = new RoundedButton("ADD EXPENSE");
    RoundedButton resetBtn = new RoundedButton("RESET FOR NEXT WEEK");
    JLabel totalEarningsLbl = new JLabel();
    JLabel totalExpensesLbl = new JLabel("Total Expenses: £0.00");
    JLabel totalRevenueLbl = new JLabel("Total Profit: £0.00");
    BigDecimal totalEarnings = new BigDecimal(0);
    BigDecimal totalExpenses = new BigDecimal(0);

    //declaring revenue table
    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
    Font font = new Font("Arial",Font.BOLD,15);
    
    String[] earningsHeadings= {"Week Day","Earnings"}; //declaring tables for menu panel   
    DefaultTableModel earningModel = new DefaultTableModel(earningsHeadings,0);
    JTable earningTable = new JTable(earningModel)
    {   
        public boolean isCellEditable(int row, int column) {                
            return false;               
        }
    };
    JScrollPane earningTableScroll = new JScrollPane(earningTable);

    //declaring expenses table

    String[] expensesHeadings= {"Name","Cost"}; //declaring tables for menu panel   
    DefaultTableModel expensesModel = new DefaultTableModel(expensesHeadings,0);
    JTable expensesTable = new JTable(expensesModel)
    {   
        public boolean isCellEditable(int row, int column) {                
            return false;               
        }
    };
    JScrollPane expensesTableScroll = new JScrollPane(expensesTable);

    WeeklyOverview(){
        initFrame();
        initComponents();
        readRevenuefromFile();
        calculateTotalEarnings();
    }

    public void initFrame(){
        title.setBounds(300,15,300,40);
        title.setFont(new Font(null,Font.BOLD,20));
        frame.add(title);

        totalEarningsLbl.setBounds(100,400,250,40);
        totalEarningsLbl.setFont(new Font(null,Font.BOLD,17));
        frame.add(totalEarningsLbl);
        
        totalExpensesLbl.setBounds(470,400,250,40);
        totalExpensesLbl.setFont(new Font(null,Font.BOLD,17));
        frame.add(totalExpensesLbl);

        totalRevenueLbl.setBounds(300,450,250,40);
        totalRevenueLbl.setFont(new Font(null,Font.BOLD,17));
        frame.add(totalRevenueLbl);

        addExpenseBtn.setBounds(270,500,100,50);
        addExpenseBtn.setFocusable(false);
        addExpenseBtn.addActionListener(this);
        frame.add(addExpenseBtn);
        
        resetBtn.setBounds(400,500,100,50);
        resetBtn.setFocusable(false);
        resetBtn.addActionListener(this);
        frame.add(resetBtn);

        backBtn.setBounds(680,15,80,30);
        backBtn.setFocusable(false);
        backBtn.addActionListener(this);
        frame.add(backBtn);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(800,650);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.decode("#a4cbfe"));
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public void initComponents(){
        renderer.setFont(font);
        for (int i = 0; i < earningTable.getColumnCount(); i++) {
            earningTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
        
        TableCellRenderer rendererFromHeader = earningTable.getTableHeader().getDefaultRenderer();
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) rendererFromHeader;
        headerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        earningTable.getTableHeader().setDefaultRenderer(new TableHeaderColour());
        earningTableScroll.setBounds(30,100,360,300);
        earningTableScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        earningTable.getTableHeader().setReorderingAllowed(false);
        earningTable.getTableHeader().setResizingAllowed(false);
        frame.add(earningTableScroll);

        TableCellRenderer rendererFromExpenseHeader = expensesTable.getTableHeader().getDefaultRenderer();
        DefaultTableCellRenderer expenseHeaderRenderer = (DefaultTableCellRenderer) rendererFromExpenseHeader;
        expenseHeaderRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        expensesTable.getTableHeader().setDefaultRenderer(new TableHeaderColour());
        expensesTableScroll.setBounds(400,100,370,300);
        expensesTableScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        expensesTable.getTableHeader().setReorderingAllowed(false);
        expensesTable.getTableHeader().setResizingAllowed(false);
        expensesTable.addMouseListener(this);
        frame.add(expensesTableScroll);

        //test data
        // earningModel.addRow(new Object[]{"Monday","£10.00"});
        // earningModel.addRow(new Object[]{"Tuesday","£20.00"});
        // earningModel.addRow(new Object[]{"Wednesday","£30.00"});
        // earningModel.addRow(new Object[]{"Thursday","£40.00"});
        // earningModel.addRow(new Object[]{"Friday","£50.00"});
        // earningModel.addRow(new Object[]{"Saturday","£60.00"});
    }

    public void readRevenuefromFile(){
        String file = "weeklyoverview.csv";
        
        try {
            try (BufferedReader bR = new BufferedReader(new FileReader(file))) {
                Object[] tableLines = bR.lines().toArray();
                
                for (int i = 1; i < 7;i++){
                    String line = tableLines[i].toString().trim();
                    String[] dataRow = line.split(",");
                    earningModel.addRow(dataRow);
                }
                bR.close();
            }
        }
        catch (Exception e) {
            System.out.println("Weekly overview file does not exist");
        }
    }
    
    public void calculateTotalEarnings(){
        for (int i = 0; i < earningTable.getRowCount(); i++) {
            totalEarnings = totalEarnings.add(new BigDecimal(earningTable.getValueAt(i, 1).toString()));
        }
        totalEarningsLbl.setText("Total Revenue: £" + totalEarnings);
    }
    
    
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==backBtn){
            frame.dispose();
            System.out.println("Back to Staff Page");
            new StaffPage();
        }

        if (e.getSource()==addExpenseBtn){
            String expenseName = "";

            try {
                expenseName = JOptionPane.showInputDialog("Enter Name of Expense");
                if (expenseName == null) {
                    System.out.println("JOptionPane closed");
                    return;
                }

                while (!(expenseName.matches("^[a-zA-Z\']+$"))) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a string for the name of the expense.", "Error", JOptionPane.ERROR_MESSAGE);
                    expenseName = JOptionPane.showInputDialog("Enter Name of Expense");
                }
            } catch (Exception ex1) {
                System.out.println("JOptionPane closed");
                return;
            }
            
            String expenseCost = "";
            try {
                expenseCost = JOptionPane.showInputDialog("Enter Cost of Expense");
                
                if (expenseCost == null) {
                    System.out.println("JOptionPane closed");
                    return;
                }
            
                while (!(expenseCost.matches("^(([0-9]){0,})((\\.)?[0-9]){1,2}$"))) {
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number for the cost of the expense.", "Error", JOptionPane.ERROR_MESSAGE);
                    expenseCost = JOptionPane.showInputDialog("Enter Cost of Expense");
                }
                
            } catch (Exception ex2) {
                System.out.println("JOptionPane closed");
                return;
            }

            
            String[] expense = {expenseName,expenseCost};
            expensesModel.addRow(expense);

            updateExpenses();
        }

        if (e.getSource()==resetBtn){

            try {
                writeExpensestoFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            
            // long start = System.nanoTime();
            // archiveandReplaceFile();
            // System.out.println("Time taken to archive: " + (System.nanoTime() - start) + "ns");

            clearTables();
            // frame.dispose();
            // System.out.println("Back to Staff Page");
            // new StaffPage();

        }
    }

    public void mouseClicked(MouseEvent mevt) {
        Object[] editOptions = {"Edit Name","Edit Cost", "Delete"};
        //Object[] deleteOptions = {"Yes","No"};
        
        if (mevt.getClickCount() == 2 && mevt.getButton() == MouseEvent.BUTTON1 && mevt.getSource() == expensesTable){
            int selectedRowIndex = expensesTable.getSelectedRow();
            int editOption = JOptionPane.showOptionDialog(null, "Choose what you would like to do", "Edit/Delete", 
            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, editOptions, editOptions[0]);

            if (editOption == 0) {
                String newName = JOptionPane.showInputDialog("Enter new name: ");
                expensesModel.setValueAt(newName, selectedRowIndex, 0);
            } else if (editOption == 1) {
                String newCost = JOptionPane.showInputDialog("Enter new cost: ");
                expensesModel.setValueAt(newCost, selectedRowIndex, 1);
            } else if (editOption == 2) {
                expensesModel.removeRow(selectedRowIndex);
            }

            updateExpenses();
        }
        // if (mevt.getClickCount() == 2 && mevt.getButton() == MouseEvent.BUTTON2 && mevt.getSource() == expensesTable){
        //     int selectedRowIndex = expensesTable.getSelectedRow();
        //     int editOption = JOptionPane.showOptionDialog(null, "Choose are you sure you want to delete this row?", "Warning", 
        //     JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,null, deleteOptions, editOptions[0]);

        //     if (editOption == 0) {
        //         expensesModel.removeRow(selectedRowIndex);
        //     }
        // }
    }

    public void updateExpenses(){
        double totalExpenses = 0;
        for (int i = 0; i < expensesTable.getRowCount(); i++) {
            totalExpenses += Double.parseDouble(expensesTable.getValueAt(i, 1).toString());
        }
        totalExpensesLbl.setText("Total Expenses: £" + totalExpenses);

        double profit = totalEarnings.doubleValue() - totalExpenses;
        totalRevenueLbl.setText("Total Revenue: £" + profit);
    }
    
    public void writeExpensestoFile() throws IOException{
        
        try (BufferedWriter expensesWriter = new BufferedWriter(new FileWriter("weeklyoverview.csv",true))) {
            List<String> lines = Files.readAllLines(Paths.get("weeklyoverview.csv"));
            try (FileWriter clearWriter = new FileWriter("weeklyoverview.csv")){
                for (int i = 0;i < 9;i++){
                    clearWriter.write("");
                }
                
            } catch (Exception e) {
                System.out.println("Error clearing file");
            }
            
            
            long start = System.nanoTime();
            for (int i = 0; i < expensesTable.getRowCount(); i++) {
                lines.set(i+9, expensesTable.getValueAt(i, 0) + "," + expensesTable.getValueAt(i, 1));
            }
            System.out.println("Time taken to write spendings to spendings array: " + (System.nanoTime() - start) + "ns");
            
            long start2 = System.nanoTime();
            for (String line : lines) {
                
                expensesWriter.write(line);
                expensesWriter.newLine();
            }
            System.out.println("Time taken to write spendings array to file: " + (System.nanoTime() - start2) + "ns");
            
            expensesWriter.close();
            
            
            // int numRows = lines.size();
            // for(int i = numRows ; i < 20; i++) {
            //     lines.add("");
            // }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error writing expenses to file 1");
        }
    }
    
    public void archiveandReplaceFile(){
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM");
        LocalDate monday = today.with(DayOfWeek.MONDAY);
        String formattedDate = monday.format(formatter);
        System.out.println(formattedDate);

        File oldFile = new File("weeklyoverview.csv");
        File newFile = new File("Archives/" + formattedDate + ".csv");
        boolean success = oldFile.renameTo(newFile);

        try {
            File myObj = new File("weeklyoverview.csv");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException ex) {
            System.out.println("An error occurred.");
            ex.printStackTrace();
        }
    }

    public void clearTables(){
        //clears earnings table
        int earningsLastRow = earningModel.getRowCount() - 1;
        for(int i = earningsLastRow;i>=0;i--){
            earningModel.removeRow(i);
        }
        
        //clears expenses table
        int expensesLastRow = expensesModel.getRowCount() - 1;
        for(int i = expensesLastRow;i>=0;i--){
            expensesModel.removeRow(i);
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
