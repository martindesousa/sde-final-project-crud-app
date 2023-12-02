package edu.virginia.sde.reviews;

import org.hibernate.Session;

public class LoginValidator {

    public static boolean validateUsername(String username, DatabaseService service){
        User user = new User();
        user.setUsername(username);
        boolean usernameExists = service.usernameExists(user);
        return usernameExists;
    }

    public static boolean validatePassword(String username, String password, DatabaseService service){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        boolean passwordCorrect = service.passwordCorrect(user);
        return passwordCorrect;
    }


}
