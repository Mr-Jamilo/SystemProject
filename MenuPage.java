import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;

public class MenuPage {
    JFrame frame = new JFrame();
    String[] headings= {"Food ID","Food Name","Cost"};      
    DefaultTableModel model = new DefaultTableModel(headings,0);
    JTable demoTable = new JTable(model);
    JScrollPane demoTableScroll = new JScrollPane(demoTable);
    JLabel title = new JLabel("Menu");
    String file = "menu.txt";
    String line = "";
    MenuList menuList = new MenuList();
    FoodItem food = new FoodItem();
    
    MenuPage(){
        title.setBounds(380, 10, 70, 30);
        title.setFont(new Font(null,Font.BOLD,20));
        frame.add(title);    
        
        demoTableScroll.setBounds(30,80,400,350);
        frame.add(demoTableScroll);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setLayout(null);
        frame.setVisible(true);

        readMenu();
    }
    
    public void readMenu(){ 
        String[] row1 = {"1","Salt and Pepper Spare Ribs","4.8"};
        String[] row2 = {"2","Salt and Pepper Chicken Wings","4.8"};
        String[] row3 = {"3","Sesame prawns on Toast","2.2"};
        String[] row4 = {"4","Chicken Wings in Fruity O.K Sauce","3.5"};
        String[] row5 = {"5","Prawn Cocktail","2.2"};
        
        model.addRow(row1);
        model.addRow(row2);
        model.addRow(row3);
        model.addRow(row4);
        model.addRow(row5);



        // String file = "menu.csv";
        // BufferedReader reader = null;
        // String line = "";
        // String tempValue = "";
        // int count = 0;
        
        // try {
        //     reader = new BufferedReader(new FileReader(file));
        //     while((line = reader.readLine()) != null){
        //         int charCount = line.length();
        //         for(int tempcharCount = 0;tempcharCount<charCount;tempcharCount++){
        //             String[] data = new String[3];
        //             char currentChar = line.charAt(tempcharCount);
        //             if (currentChar == ','){
        //                 tempcharCount++;
        //                 currentChar = line.charAt(tempcharCount);
        //                 tempValue = tempValue + currentChar;
        //                 data[count] = tempValue;
        //                 System.out.println(data[count]);
        //                 count++;
        //             }
        //             else{    
        //                 tempValue = tempValue + currentChar;
        //             }
        //         }
        //     }
        // }
        // catch (Exception e) {
        //     e.printStackTrace();
        // }
    }
}
