import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MenuPage {
    JFrame frame = new JFrame();
    String[] headings= {"Food ID","Food Name","Cost"};      
    DefaultTableModel model = new DefaultTableModel(headings,0);
    JTable demoTable = new JTable(model);
    JScrollPane demoTableScroll = new JScrollPane(demoTable);
    JLabel title = new JLabel("Menu");
    BufferedReader reader = null;
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
        System.out.println("reading menu...");
        String file = "menu.csv";
        BufferedReader reader = null;
        String line = "";
        String tempValue = "";
        int count = 0;
        
        try {
            System.out.println("reading...");
            reader = new BufferedReader(new FileReader(file));
            while((line = reader.readLine()) != null){
                int charCount = line.length();
                for(int tempcharCount = 0;tempcharCount<charCount;tempcharCount++){
                    String[] data = new String[3];
                    char currentChar = line.charAt(tempcharCount);
                    if (currentChar == ','){
                        tempcharCount++;
                        currentChar = line.charAt(tempcharCount);
                        tempValue = tempValue + currentChar;  
                    }
                    else{    
                        tempValue = tempValue + currentChar;
                    }

                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        } 
        
        
        
        
        
        
        
        // try {
        //     reader = new BufferedReader(new FileReader(file));
        //     while((line = reader.readLine()) != null){
        //         String[] row = line.split(",");
        //         //System.out.println(String index);
                
        //          for(String index : row){                ///(prints file)
        //             System.out.printf("%-10s", index);
        //         }
        //         System.out.println();
        //     }
        // }
        // catch(Exception e) {
        //     System.out.println("error");
        // }
        // finally {
        //     try {
        //         reader.close();
        //     } catch (IOException e) {
        //         // TODO Auto-generated catch block
        //         e.printStackTrace();
        //     }
        // }
    }
    
    
    // public void readMenu(){
    //     int FoodNum = MenuList.position;
    //     for(int i=0; i<FoodNum;i++){
    //         FoodItem tempfood = MenuList.wholeMenu[i];
    //         String[] fooddata = new String[3];
            
    //         String tempID = (tempfood.foodID) + "";
    //         fooddata[0] = tempID;
    //         fooddata[1] = tempfood.foodName;
    //         String tempCost = (tempfood.foodCost) + "";
    //         fooddata[2] = tempCost;
    //         model.addRow(fooddata);
    //     }
    // }
}
