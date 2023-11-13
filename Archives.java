import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.io.File;
import java.util.Scanner;

public class Archives implements ActionListener, MouseListener, KeyListener{
    JFrame frame = new JFrame();
    JPanel secondaryPanel = new JPanel();
    JLabel title = new JLabel("Archives");
    JLabel testlbl = new JLabel("testing second panel");
    CustomBackButton backBtn = new CustomBackButton("BACK",Color.decode("#920000"));
    CustomBackButton closeBtn = new CustomBackButton("CLOSE",Color.decode("#920000"));
    RoundedTextField searchPrompt= new RoundedTextField(Color.BLACK);
    TextPrompt searchPromptText = new TextPrompt("Search Week",searchPrompt);
    int selectedRow = 0;
    
    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
    Font font = new Font("Arial",Font.BOLD,15);
    
    //declaring table for file names
    String[] fileHeadings = {"Week","File Name"}; 
    DefaultTableModel fileModel = new DefaultTableModel(fileHeadings,0);
    JTable fileTable = new JTable(fileModel)
    {   
        public boolean isCellEditable(int row, int column) {                
            return false;               
        }
    };
    JScrollPane fileTableScroll = new JScrollPane(fileTable);

    //declaring table for file contents
    
    //earnings table
    String[] earningsHeadings= {"Week Day","Earnings"}; //declaring tables for menu panel   
    DefaultTableModel earningModel = new DefaultTableModel(earningsHeadings,0);
    JTable earningTable = new JTable(earningModel)
    {   
        public boolean isCellEditable(int row, int column) {                
            return false;               
        }
    };
    JScrollPane earningTableScroll = new JScrollPane(earningTable);

    //expenses table
    String[] expensesHeadings= {"Name","Cost"}; //declaring tables for menu panel   
    DefaultTableModel expensesModel = new DefaultTableModel(expensesHeadings,0);
    JTable expensesTable = new JTable(expensesModel)
    {   
        public boolean isCellEditable(int row, int column) {                
            return false;               
        }
    };
    JScrollPane expensesTableScroll = new JScrollPane(expensesTable);

    Archives(){
        initFrame();
        readFolder();
    }

    public void initFrame(){
        title.setBounds(350, 10,300,40);
        title.setFont(new Font(null,Font.BOLD,20));
        frame.add(title);

        backBtn.setBounds(680,15,80,30);
        backBtn.setFocusable(false);
        backBtn.addActionListener(this);
        frame.add(backBtn);

        searchPrompt.setBounds(280,70,200,20);
        searchPrompt.addKeyListener(this);
        searchPrompt.setForeground(Color.gray); 
        frame.add(searchPrompt);

        renderer.setFont(font);
        for (int i = 0; i < fileTable.getColumnCount(); i++) {
            fileTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        TableCellRenderer rendererFromHeader = fileTable.getTableHeader().getDefaultRenderer();
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) rendererFromHeader;
        headerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        fileTable.getTableHeader().setDefaultRenderer(new TableHeaderColour());
        fileTableScroll.setBounds(200,100,400,400);
        fileTableScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        fileTable.getTableHeader().setReorderingAllowed(false);
        fileTable.getTableHeader().setResizingAllowed(false);
        fileTable.addMouseListener(this);
        frame.add(fileTableScroll);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(800,650);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.decode("#a4cbfe"));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        initSecondaryPanelComponents();
        initSecondaryPanel();
    }

    public void initSecondaryPanelComponents(){
        testlbl.setBounds(0,0,100,30);
        testlbl.setFont(new Font(null,Font.BOLD,20));
        //secondaryPanel.add(testlbl);

        closeBtn.setBounds(500,10,70,30);
        closeBtn.setFocusable(false);
        closeBtn.addActionListener(this);
        secondaryPanel.add(closeBtn);

        renderer.setFont(font);
        for (int i = 0; i < earningTable.getColumnCount(); i++) {
            earningTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
        
        TableCellRenderer rendererFromEarningHeader = earningTable.getTableHeader().getDefaultRenderer();
        DefaultTableCellRenderer earningHeaderRenderer = (DefaultTableCellRenderer) rendererFromEarningHeader;
        earningHeaderRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        earningTable.getTableHeader().setDefaultRenderer(new TableHeaderColour());
        earningTableScroll.setBounds(30,100,250,250);
        earningTableScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        earningTable.getTableHeader().setReorderingAllowed(false);
        earningTable.getTableHeader().setResizingAllowed(false);
        secondaryPanel.add(earningTableScroll);

        TableCellRenderer rendererFromExpensesHeader = expensesTable.getTableHeader().getDefaultRenderer();
        DefaultTableCellRenderer expensesHeaderRenderer = (DefaultTableCellRenderer) rendererFromExpensesHeader;
        expensesHeaderRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        expensesTable.getTableHeader().setDefaultRenderer(new TableHeaderColour());
        expensesTableScroll.setBounds(300,100,250,250);
        expensesTableScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        expensesTable.getTableHeader().setReorderingAllowed(false);
        expensesTable.getTableHeader().setResizingAllowed(false);
        secondaryPanel.add(expensesTableScroll);
    }
    
    public void initSecondaryPanel(){
        secondaryPanel.setLayout(null);
        secondaryPanel.setBounds(100,50,580,500);
        secondaryPanel.setBackground(Color.WHITE);
        secondaryPanel.setVisible(false);
        secondaryPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        secondaryPanel.setBackground(Color.decode("#0500ce"));
        frame.add(secondaryPanel);
    }

    public void readFolder(){
        long start = System.nanoTime();
        File folder = new File("Archives/");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                fileModel.addRow(new Object[]{i+1, listOfFiles[i].getName()});
            }
        }
        System.out.println("Read folder in " + (System.nanoTime() - start) + "ns");
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==backBtn){
            frame.dispose();
            System.out.println("Back to Staff Page");
            new StaffPage();
        }
        
        if (e.getSource()==closeBtn){
            clearsubTables();
            secondaryPanel.setVisible(false);
            System.out.println("Closed secondary panel");
            fileTable.setVisible(true);
            fileTableScroll.setVisible(true);
            searchPrompt.setVisible(true);
        }
    }

    public void mouseClicked(MouseEvent mevt) {
        if (mevt.getClickCount() == 2 && mevt.getButton() == MouseEvent.BUTTON1 && mevt.getSource() == fileTable){
            selectedRow = fileTable.getSelectedRow();
            readFile();
            System.out.println("Opened secondary panel");
            secondaryPanel.setVisible(true);
            fileTable.setVisible(false);
            fileTableScroll.setVisible(false);
            searchPrompt.setVisible(false);
        }
    }

    public void readFile(){
        
        String selectedFileName = fileTable.getValueAt(selectedRow, 1).toString();

        File folder = new File("Archives/");
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                if (listOfFiles[i].getName().equals(selectedFileName)) {
                    String fileName = listOfFiles[i].getName();
                    try {
                        Scanner myReader = new Scanner(new File("Archives/" + fileName));
                        int rowCount = 0;
                        //reads revenue
                        while (myReader.hasNextLine() && rowCount < 7) {
                            String data = myReader.nextLine();
                            if (rowCount != 0) {
                                String[] dataSplit = data.split(",");
                                earningModel.addRow(new Object[]{dataSplit[0], dataSplit[1]});
                            }
                            rowCount++;
                        }
                        
                        //reads expenses
                        while (myReader.hasNextLine()) {
                            String data = myReader.nextLine();
                            if (rowCount > 8){
                                String[] dataSplit = data.split(",");
                                expensesModel.addRow(new Object[]{dataSplit[0], dataSplit[1]});
                            }
                            rowCount++;
                        }
                        myReader.close();
                    } catch (Exception e) {
                        System.out.println("Error reading file");
                    }
                }
            }
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            searchFileWeek();
        }
    }

    public void searchFileWeek(){
        readFolder();
        String userSearch = searchPrompt.getText();
        for (int i = 0; i < fileTable.getRowCount(); i++) {
            if (fileTable.getValueAt(i, 0).toString().equals(userSearch) == false) {
                clearFileTable();
                readFolder();

            } else {
                String[] row = {fileTable.getValueAt(i, 0).toString(), fileTable.getValueAt(i, 1).toString()};
                clearFileTable();
                fileModel.addRow(row);
                break;
            }
        }
    }

    public void clearFileTable(){
        int lastRow = fileTable.getRowCount() - 1;
        for(int i = lastRow;i>=0;i--){
            fileModel.removeRow(i);
        }
    }

    public void clearsubTables(){
        int lastRow = earningTable.getRowCount() - 1;
        for(int i = lastRow;i>=0;i--){
            earningModel.removeRow(i);
        }
        int lastRow2 = expensesTable.getRowCount() - 1;
        for(int i = lastRow2;i>=0;i--){
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

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }
}
