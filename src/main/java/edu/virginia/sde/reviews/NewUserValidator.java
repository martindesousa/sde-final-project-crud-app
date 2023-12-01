package edu.virginia.sde.reviews;

public class NewUserValidator {

    public boolean validate(String username, String password){
        if(username == null || password == null){return false;}
        if(username.equals("") || password.equals("")){return false;}
        return true;

    }

}
