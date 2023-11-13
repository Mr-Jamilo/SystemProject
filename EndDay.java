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
    JLabel sundayErrorlbl = new JLabel("Today is Sunday, please wait till Monday to reset the system");
    CustomBackButton backBtn = new CustomBackButton("BACK",Color.decode("#920000"));
    RoundedButton resetBtn = new RoundedButton("RESET FOR NEXT DAY");
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
        frame.getContentPane().setBackground(Color.decode("#a4cbfe"));
        frame.setLayout(null);
        frame.setVisible(true);

        initComponentsintoFrame();
        readStats();
        checkWeeklyOverviewFile();
    }

    public void initComponentsintoFrame(){
        title.setBounds(260,20,250,30);
        title.setFont(new Font(null,Font.BOLD,20));
        frame.add(title);

        sundayErrorlbl.setBounds(200,100,400,30);
        sundayErrorlbl.setFont(new Font(null,Font.BOLD,15));
        sundayErrorlbl.setForeground(Color.RED);
        sundayErrorlbl.setVisible(false);
        frame.add(sundayErrorlbl);

        backBtn.setBounds(680,15,80,30);
        backBtn.setFocusable(false);
        backBtn.addActionListener(this);
        frame.add(backBtn);

        TableCellRenderer rendererFromHeader = table.getTableHeader().getDefaultRenderer();
        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) rendererFromHeader;
        headerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        table.getTableHeader().setDefaultRenderer(new TableHeaderColour());
        ScrollTable.setBounds(200,150,300,100);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setFont(new Font(null, Font.BOLD, 15));
        //table.setRowHeight(table.getRowHeight()+gap); 
        frame.add(ScrollTable);

        resetBtn.setBounds(290,450,130,50);
        resetBtn.setFocusable(false);
        resetBtn.addActionListener(this);
        frame.add(resetBtn);
    }

    public void readStats(){
        int totalOrders = 0;
        try (BufferedReader bR = new BufferedReader(new FileReader("allorders.csv"))){
            long start = System.nanoTime();
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
            System.out.println("Time taken to add total cost of orders to cost array: " + (System.nanoTime() - start) + "ns");
            //System.out.println(totalCost);
            String data[] = {Integer.toString(totalOrders),"£"+totalRevenue.toString()};
            model.addRow(data);
        } catch (Exception e) {
            System.out.println("Error reading the last value of each row");
        }
    }

    public void checkWeeklyOverviewFile(){
        try {
            FileReader fR = new FileReader("weeklyoverview.csv");
            System.out.println("File exists");
            fR.close();
        } catch(Exception e){
            System.out.println("File does not exist");
            try {
                File myObj = new File("weeklyoverview.csv");
                if (myObj.createNewFile()){
                    System.out.println("File created: " + myObj.getName());
                } else{
                    System.out.println("File already exists.");
                }
            } catch (IOException ex){
                System.out.println("An error occurred.");
                ex.printStackTrace();
            }
        }
    }

    public void actionPerformed(ActionEvent e){
        String strTotalRevenue = totalRevenue.toString();
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (e.getSource() == backBtn){
            frame.dispose();
            new StaffPage();
        }
        else{
            if (dayOfWeek == Calendar.SUNDAY) {
                sundayErrorlbl.setVisible(true);
            } else {
                try (BufferedWriter bW = new BufferedWriter(new FileWriter("allorders.csv"))) {
                    File file = new File("allorders.csv");
                    if(file.length() == 0){
                        bW.write("");
                    }
                    bW.close();
                } catch (Exception e2){
                    System.out.println("Error writing to file 2");
                }

                try{
                    long start = System.nanoTime();
                    List<String> lines = Files.readAllLines(Paths.get("weeklyoverview.csv"));
                    int numRows = lines.size();
                    for (int i = numRows; i < 9; i++){
                        lines.add("");
                    }
                    lines.set(0, "Weekday,Revenue");
                    lines.set(8, "Name,Expense");

                    if (dayOfWeek == Calendar.MONDAY){
                        // Do something for Monday
                        lines.set(1, "Monday," + strTotalRevenue);
                    } else if (dayOfWeek == Calendar.TUESDAY){
                        // Do something for Tuesday
                        lines.set(2, "Tuesday," + strTotalRevenue);
                    } else if (dayOfWeek == Calendar.WEDNESDAY){
                        // Do something for Wednesday
                        lines.set(3, "Wednesday," + strTotalRevenue);
                    } else if (dayOfWeek == Calendar.THURSDAY){
                        // Do something for Thursday
                        lines.set(4, "Thursday," + strTotalRevenue);
                    } else if (dayOfWeek == Calendar.FRIDAY){
                        // Do something for Friday
                        lines.set(5, "Friday," + strTotalRevenue);
                    } else if (dayOfWeek == Calendar.SATURDAY){
                        // Do something for Saturday
                        lines.set(6, "Saturday," + strTotalRevenue);
                    }
                    
                    try{
                        Files.write(Paths.get("weeklyoverview.csv"), lines);
                    } catch (IOException e1) {
                        System.out.println("File does not exist");
                    }
                    System.out.println("Time taken to write total revenue to weeklyoverview.csv: " + (System.nanoTime() - start) + "ns");
                } catch (Exception e1){
                    System.out.println("File does not exist");
                }

                frame.dispose();
                new StaffPage();
            }
        } 
    }
}
