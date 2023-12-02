package edu.virginia.sde.reviews;

import org.hibernate.Session;

public class NewUserValidator {

    public static boolean validateUsername(String username, DatabaseService service){
        User user = new User();
        user.setUsername(username);
        boolean usernameExists = service.usernameExists(user);
        return !usernameExists;
        //return !LoginValidator.validateUsername(username);

    }

    public static boolean validatePassword(String password){
        return password.length() >= 8;

    }

}
