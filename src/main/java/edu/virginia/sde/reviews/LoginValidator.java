package edu.virginia.sde.reviews;

public class LoginValidator {

    public boolean validateUsername(String username){
        return !(username == null);
    }

    public boolean validatePassword(String password){
        return !(password == null);

    }

    public boolean validatePassword(String username, String password){
        if(username == null){return false;}
        if(password == null){return false;}
        if(username.equals("")||password.equals("")){return false;}
        return true;
    }


}
