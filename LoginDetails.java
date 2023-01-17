//TODO read logins.csv
//TODO use the logins to add to hashmap
import java.io.BufferedReader;
import java.io.FileReader;  //login details
import java.util.HashMap;  //login details 
// public class LoginDetails {
//     HashMap<String,String> loginDetails = new HashMap<String,String>(); //hashmap used so emails and passwords can refer to each other

//     LoginDetails(){ //details are hardcoded but this will change in the final product
//         loginDetails.put("Staff@gmail.com","abc123");
//         loginDetails.put("Customer1@gmail.com","4321");
//     }

//   //private
//     public HashMap<String,String> getLoginDetail() { //don't want anywhere else having access to the details - protected
//         return loginDetails;
//     }
// }

public class LoginDetails {
    private HashMap<String, String> loginDetails;

    public LoginDetails() {
        loginDetails = new HashMap<>();
        readLoginDetailsFromFile();
    }

    private void readLoginDetailsFromFile() {
        String file = "logins.csv";
        try (BufferedReader bR = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bR.readLine()) != null) {
                String[] dataRow = line.split(",");
                String email = dataRow[3];
                String password = dataRow[4];
                loginDetails.put(email, password);
            }
            bR.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, String> getLoginDetails() {
        return loginDetails;
    }
}
