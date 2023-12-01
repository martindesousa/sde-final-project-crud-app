package edu.virginia.sde.reviews;

public class NewUserValidator {

    public static boolean validateUsername(String username){
        return !LoginValidator.validateUsername(username);

    }

    public static boolean validatePassword(String password){
        return password.length() >= 8;

    }

}
