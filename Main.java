public class Main {
    //allows the program to run
    public static void main(String[] args){
        LoginDetails loginDetails = new LoginDetails();
        LoginPage loginpage = new LoginPage(loginDetails.getLoginDetail());
        
    }
}
