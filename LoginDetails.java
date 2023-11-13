import java.io.BufferedReader;
import java.io.FileReader;  //login details
import java.util.HashMap;  //login details 

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
