package edu.virginia.sde.reviews;

import org.hibernate.Session;

public class NewUserValidator {

    public static boolean validateUsername(String username, DatabaseService service){
        boolean usernameExists = service.usernameExists(username);
        return !usernameExists;
        //return !LoginValidator.validateUsername(username);

    }

    public static boolean validatePassword(String password){
        return password.length() >= 8;

    }

}
