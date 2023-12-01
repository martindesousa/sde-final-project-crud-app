package edu.virginia.sde.reviews;

public class LoginValidator {

    public boolean validateLogin(String username, String password){
        if(username.equals("")||password.equals("")){return false;}
        return true;
    }
}
