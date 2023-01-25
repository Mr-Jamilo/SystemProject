import java.awt.*;
import javax.swing.*;
import java.io.*;
import javax.swing.JTable;
import javax.swing.table.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.List;

public class EndDay implements ActionListener{
    JFrame frame = new JFrame();
    JLabel title = new JLabel("END OF DAY REVIEW");
    JButton backBtn = new JButton("<html><center>BACK</center></html>");
    JButton resetBtn = new JButton("<html><center>RESET FOR NEXT DAY</center></html>");
    BigDecimal totalRevenue = new BigDecimal(0);

    String[] headings = {"Orders Received","Amount Earned"};
    DefaultTableModel model = new DefaultTableModel(headings,0);
    JTable table = new JTable(model){
        public boolean isCellEditable(int row, int column){
            return false;
        }
    };
    JScrollPane ScrollTable = new JScrollPane(table);

    EndDay(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(800,650);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setVisible(true);

        initComponentsintoFrame();
        readStats();
    }

    public void initComponentsintoFrame(){
        title.setBounds(260,20,250,30);
        title.setFont(new Font(null,Font.BOLD,20));
        frame.add(title);

        backBtn.setBounds(700,20,70,30);
        backBtn.setFocusable(false);
        backBtn.addActionListener(this);
        frame.add(backBtn);

        ScrollTable.setBounds(200,150,300,100);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setFont(new Font(null, Font.BOLD, 15));
        //table.setRowHeight(table.getRowHeight()+gap); 
        frame.add(ScrollTable);

        resetBtn.setBounds(290,450,90,50);
        resetBtn.setFocusable(false);
        resetBtn.addActionListener(this);
        frame.add(resetBtn);
    }
    
    public void readStats(){
        int totalOrders = 0;
        try (BufferedReader bR = new BufferedReader(new FileReader("allorders.csv"))){
            Object[] tableLines =  bR.lines().toArray();
            for (int i = 0; i < tableLines.length;i++){
                String line = tableLines[i].toString().trim();
                String[] dataRow = line.split(",");
                for (int j = 0; j < dataRow.length;j++){
                    try {
                        totalRevenue = totalRevenue.add(new BigDecimal(dataRow[j]));
                    } catch (Exception e) {
                        //System.out.println("Not a number");
                    }
                }
                totalOrders++;
            }
            //System.out.println(totalCost);
            String data[] = {Integer.toString(totalOrders),"£"+totalRevenue.toString()};
            model.addRow(data);
        } catch (Exception e) {
            System.out.println("Error reading the last value of each row");
        }
    }
    
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==backBtn){
            frame.dispose();
            new StaffPage();
        }
        if(e.getSource()==resetBtn){
            String strTotalRevenue = totalRevenue.toString();
            
            try (BufferedWriter bW = new BufferedWriter(new FileWriter("allorders.csv"))){
                bW.write("");
                bW.close();
                Calendar calendar = Calendar.getInstance();
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                try (BufferedWriter revenueWriter = new BufferedWriter(new FileWriter("weeklyoverview.csv"))){
                    List<String> lines = Files.readAllLines(Paths.get("weeklyoverview.csv"));
                    
                    if (dayOfWeek == Calendar.MONDAY) {
                        // Do something for Monday
                        lines.set(1, "Monday,"+strTotalRevenue);
                        
                    } else if (dayOfWeek == Calendar.TUESDAY) {
                        // Do something for Tuesday
                        lines.set(2, "Tuesday,"+strTotalRevenue);
                        
                    } else if (dayOfWeek == Calendar.WEDNESDAY) {
                        // Do something for Wednesday
                        lines.set(3, "Wednesday,"+strTotalRevenue);
                    } else if (dayOfWeek == Calendar.THURSDAY) {
                        // Do something for Thursday
                        lines.set(4, "Thursday,"+strTotalRevenue);
                    } else if (dayOfWeek == Calendar.FRIDAY) {
                        // Do something for Friday
                        lines.set(5, "Friday,"+strTotalRevenue);

                    } else if (dayOfWeek == Calendar.SATURDAY) {
                        // Do something for Saturday
                        lines.set(6, "Saturday,"+strTotalRevenue);
                    }
                    for (String line : lines) {
                        revenueWriter.write(line);
                        revenueWriter.newLine();
                    }
                } catch (Exception e1) {
                    System.out.println("Error writing to file");
                }
            } catch (Exception e1) {
                System.out.println("Error writing to file");
            }
            frame.dispose();
            new StaffPage();

        }
    }
}
