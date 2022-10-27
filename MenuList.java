import java.io.FileWriter;
import javax.swing.JOptionPane;

public class MenuList {
    static FoodItem[] wholeMenu = new FoodItem[100000];
    static int position=0;
    
    public void readFoodMenutoArray(FoodItem tempFood){
        wholeMenu[position] = tempFood;
        position++;
    }
    
    public void writearraytofile(){
        try{
            FileWriter fw = new FileWriter("AllOrders.txt");

            for(int count = 0; count < position; count++){
                FoodItem CurrentFoodItem = wholeMenu[count];
                fw.write(CurrentFoodItem.foodID+",");
                fw.write(CurrentFoodItem.foodName+",");
                fw.write(CurrentFoodItem.foodCost+",");
                fw.write("\r\n");
            }
            fw.close();
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "errrrooorrrr");
        }
    }

    // public void readarraytofile(){
    //     wholeMenu = new FoodItem[10000];
    //     position = 0;
        
    //     try{
    //         BufferedReader reader = new BufferedReader(new FileReader("AllOrders.txt"));
    //         String tempLine = reader.readLine();
    //         while(tempLine != null){
    //             String[] splitData = tempLine.split(",");
    //             FoodItem tempFood = new FoodItem();

    //         }
    //     } catch(Exception e){
    //         e.printStackTrace();  
    //     }
    // }
}
