import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.swing.JTable;
import javax.swing.table.*;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class CurrentOrders implements MouseListener, ActionListener{
    int amountOrdered;
    JFrame frame = new JFrame();
    JLabel title = new JLabel("CURRENT ORDERS");
    RoundedButton orderBtn = new RoundedButton("READ ORDER");
    CustomBackButton backBtn = new CustomBackButton("BACK", Color.decode("#920000"));
    RoundedButton paidBtn = new RoundedButton("ORDER PAID");
    RoundedButton notpaidBtn = new RoundedButton("ORDER NOT PAID");
    
    String[] headings = {"Order ID"};
    DefaultTableModel model = new DefaultTableModel(headings,0);
    JTable table = new JTable(model){
        public boolean isCellEditable(int row, int column){
            return false;
        }
    };
    JScrollPane ScrollTable = new JScrollPane(table);

    String[] oHeadings = {"Order"};
    DefaultTableModel oModel = new DefaultTableModel(oHeadings,0);
    JTable oTable = new JTable(oModel){
        public boolean isCellEditable(int row, int column){
            return false;
        }
    };
    JScrollPane oTableScroll = new JScrollPane(oTable);




    CurrentOrders(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(800,650);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.decode("#a4cbfe"));
        frame.setLayout(null);
        frame.setVisible(true);
        
        initComponentsintoFrame();
    }
    
    public void initComponentsintoFrame(){
        title.setBounds(260,20,250,30);
        title.setFont(new Font(null,Font.BOLD,20));
        frame.add(title);
        
        backBtn.setBounds(680,15,80,30);
        backBtn.setFocusable(false);
        backBtn.addActionListener(this);
        frame.add(backBtn);
        
        orderBtn.setBounds(190,450,90,50);
        orderBtn.setFocusable(false);
        orderBtn.addActionListener(this);
        frame.add(orderBtn);
        
        paidBtn.setBounds(290,450,90,50);
        paidBtn.setFocusable(false);
        paidBtn.addActionListener(this);
        frame.add(paidBtn);
        
        notpaidBtn.setBounds(390,450,90,50);
        notpaidBtn.setFocusable(false);
        notpaidBtn.addActionListener(this);
        frame.add(notpaidBtn);

        TableCellRenderer rendererFromHeader = table.getTableHeader().getDefaultRenderer();
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) rendererFromHeader;
        headerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        table.getTableHeader().setDefaultRenderer(new TableHeaderColour());
        table.addMouseListener(this);
        ScrollTable.setBounds(30,80,100,200);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        frame.add(ScrollTable);
        
        TableCellRenderer rendererFromoHeader = oTable.getTableHeader().getDefaultRenderer();
        DefaultTableCellRenderer oheaderRenderer = (DefaultTableCellRenderer) rendererFromoHeader;
        oheaderRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        oTable.getTableHeader().setDefaultRenderer(new TableHeaderColour());
        oTableScroll.setBounds(200,80,400,280);
        oTable.getTableHeader().setReorderingAllowed(false);
        oTable.getTableHeader().setResizingAllowed(false);
        frame.add(oTableScroll);

        readOrderID();
    }
    
    public void readOrderID(){
        String file = "tempcurrentorders.csv";
        try (BufferedReader bR = new BufferedReader(new FileReader(file))) {
            Object[] tableLines =  bR.lines().toArray();
            for (int i = 1; i < tableLines.length;i++){
                String line = tableLines[i].toString().trim();
                String[] dataRow = line.split(",");
                model.addRow(dataRow);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void actionPerformed(ActionEvent e){
        if (e.getSource()==backBtn){
            frame.dispose();
            new StaffPage();
        }
        
        if (e.getSource()==orderBtn){
            clearTable();
            readOrder();
        }
        
        else if (e.getSource()==paidBtn){
            clearTable();
            readOrder();
            
            System.out.println("Order Paid - order written to allorders.csv");
            try (FileWriter fW = new FileWriter("allorders.csv",true)){
                for(int i = 0; i < oModel.getRowCount();i++){
                    fW.append(oModel.getValueAt(i,0).toString());
                    fW.append(",");
                }
                fW.write("\r\n");
            } catch (Exception ex) {
                System.out.println("Error writing to file");
            }
            
            removeOrderfromFile();
            long start = System.nanoTime();
            removeOrderfromCustomerFile();
            System.out.println("Time taken to remove order from customer file: " + (System.nanoTime() - start) + "ns");
            model.removeRow(table.getSelectedRow());
            clearTable();
        }

        else if (e.getSource()==notpaidBtn){
            System.out.println("Order not paid - removed from tempcurrentorders.csv");
            removeOrderfromFile();
            model.removeRow(table.getSelectedRow());
            clearTable();
        }
    }

    public void readOrder(){
        int selectedRowIndex = table.getSelectedRow();
        String orderID = model.getValueAt(selectedRowIndex,0).toString();
        String file = "tempcurrentorders.csv";
        try (BufferedReader bR = new BufferedReader(new FileReader(file))) {
            Object[] tableLines =  bR.lines().toArray();
            for (int i = 0; i < tableLines.length;i++){
                String line = tableLines[i].toString();
                String[] dataRow = line.split(",");
                if (dataRow[0].equals(orderID)){
                    for (int j = 1; j < dataRow.length - 2;j++){
                        oModel.addRow(new Object[]{dataRow[j]});
                        amountOrdered = amountOrdered + 1;
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Error reading file");
        }
    }
    
    public void clearTable(){
        int lastRow = amountOrdered - 1;
        for(int i = lastRow;i>=0;i--){
            oModel.removeRow(i);
        }
        amountOrdered = 0;
    }
    
    public void removeOrderfromFile(){
        int selectedRowIndex = table.getSelectedRow();
        String orderID = model.getValueAt(selectedRowIndex,0).toString();
        String file = "tempcurrentorders.csv";
        try (BufferedReader bR = new BufferedReader(new FileReader(file))) {
            Object[] tableLines =  bR.lines().toArray();
            for (int i = 0; i < tableLines.length;i++){
                String line = tableLines[i].toString();
                String[] dataRow = line.split(",");
                if (dataRow[0].equals(orderID)){
                    try (FileWriter fW = new FileWriter(file)){
                        for (int j = 0; j < tableLines.length;j++){
                            if (j != i){
                                fW.append(tableLines[j].toString());
                                fW.write("\r\n");
                            }
                        }
                    } catch (Exception ex) {
                        System.out.println("Error writing to file");                            
                    }
                }   
            }
        } catch (Exception ex) {
            System.out.println("Error reading file");
        }
    }

    public void removeOrderfromCustomerFile(){
        int selectedRowIndex = table.getSelectedRow();
        String orderID = model.getValueAt(selectedRowIndex,0).toString();
        String file = "custorders.csv";
        try (BufferedReader bR = new BufferedReader(new FileReader(file))) {
            Object[] tableLines =  bR.lines().toArray();
            for (int i = 0; i < tableLines.length;i++){
                String line = tableLines[i].toString();
                String[] dataRow = line.split(",");
                if (dataRow[0].equals(orderID)){
                    try (FileWriter fW = new FileWriter(file)){
                        for (int j = 0; j < tableLines.length;j++){
                            if (j != i){
                                fW.append(tableLines[j].toString());
                                fW.write("\r\n");
                            }
                        }
                    } catch (Exception ex) {
                        System.out.println("Error writing to file");                            
                    }
                }   
            }
        } catch (Exception ex) {
            System.out.println("Error reading file");
        }
    }

    public void mouseClicked(MouseEvent mevt){
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
