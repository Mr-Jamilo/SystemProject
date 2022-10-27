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
    JFrame frame = new JFrame();
    JLabel title = new JLabel("CURRENT ORDERS");
    JButton orderBtn = new JButton("<html><center>READ ORDER</center></html>");
    JButton backBtn = new JButton("<html><center>BACK</center></html>");
    
    String[] headings = {"Order ID"};
    DefaultTableModel model = new DefaultTableModel(headings,0);
    JTable table = new JTable(model);
    JScrollPane ScrollTable = new JScrollPane(table);

    String[] oHeadings = {"Order"};
    DefaultTableModel oModel = new DefaultTableModel(oHeadings,0);
    JTable oTable = new JTable(oModel);
    JScrollPane oTableScroll = new JScrollPane(oTable);




    CurrentOrders(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setLayout(null);
        frame.setVisible(true);
        
        initComponentsintoFrame();
    }
    
    public void initComponentsintoFrame(){
        title.setBounds(260,20,250,30);
        title.setFont(new Font(null,Font.BOLD,20));
        frame.add(title);
        
        backBtn.setBounds(700,20,70,30);
        backBtn.setFocusable(false);
        backBtn.addActionListener(this);
        frame.add(backBtn);
        
        orderBtn.setBounds(30,450,100,70);
        orderBtn.setFocusable(false);
        orderBtn.addActionListener(this);
        frame.add(orderBtn);
        
        table.addMouseListener(this);
        ScrollTable.setBounds(30,80,100,200);
        frame.add(ScrollTable);
        
        oTable.addMouseListener(this);
        oTableScroll.setBounds(500,90,250,280);
        frame.add(oTableScroll);

        readOrderID();
    }
    
    public void readOrderID(){
        String file = "tempcurrentorders.csv";
        try (BufferedReader bR = new BufferedReader(new FileReader(file))) {
            Object[] tableLines =  bR.lines().toArray();
            for (int i = 0; i < tableLines.length;i++){
                String line = tableLines[i].toString().trim();
                String[] dataRow = line.split(",");
                model.addRow(dataRow);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void mouseClicked(MouseEvent mevt){
        int selectedRowIndex = table.getSelectedRow();
    }
    
    public void actionPerformed(ActionEvent e){
        if (e.getSource()==backBtn){
            frame.dispose();
            new StaffPage();
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
