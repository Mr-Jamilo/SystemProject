//TODO read logins.csv
//TODO use the logins to add to hashmap

import java.util.HashMap;  //login details 
public class LoginDetails {
    HashMap<String,String> loginDetails = new HashMap<String,String>(); //hashmap used so emails and passwords can refer to each other

    LoginDetails(){ //details are hardcoded but this will change in the final product
        loginDetails.put("Staff@gmail.com","abc123");
        loginDetails.put("Customer1@gmail.com","4321");
    }

  //private
    public HashMap<String,String> getLoginDetail() { //don't want anywhere else having access to the details - protected
        return loginDetails;
    }
}
