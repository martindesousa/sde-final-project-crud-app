package edu.virginia.sde.reviews;

import org.hibernate.Session;

public class LoginValidator {

    public static boolean validateUsername(String username, DatabaseService service){
        return service.usernameExists(username);
    }

    public static boolean validatePassword(String username, String password, DatabaseService service){
        return service.passwordCorrect(username, password);
    }


}
