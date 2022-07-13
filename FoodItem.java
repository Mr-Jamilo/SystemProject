public class FoodItem {
    int foodID;
    String foodName;
    double foodCost;

    public String toString(){
        String foodData = foodID+","+foodName+","+foodCost;
        return foodData;
    }
}
