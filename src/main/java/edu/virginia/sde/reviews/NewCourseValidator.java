package edu.virginia.sde.reviews;

import javafx.scene.control.Label;

public class NewCourseValidator {

    public static boolean ValidateTitle(String text){
        if(text == null){return false;}
        if(text.length() < 1){return false;}
        if(text.length() >50){return false;}
        return true;
    }

    public static boolean ValidateNumber(String number){
        if(number == null){return false;}
        if(number.length()!=4){return false;}
        char[] numberArray = number.toCharArray();
        for(int i=0;i<4;i++){
            switch (numberArray[i]){
                case '0','1','2','3','4','5','6','7','8','9':
                    break;

                default:
                    return false;
            }
        }
        return true;

    }

    public static boolean ValidateSubject(String subject){

        if(subject == null){return false;}
        if(subject.length()<2){return false;}
        if(subject.length()>4){return false;}
        subject = subject.toUpperCase();
        char[] numberArray = subject.toCharArray();
        for(int i=0;i<subject.length();i++){
            switch (numberArray[i]){
                case 'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z':
                    break;

                default:
                    return false;
            }
        }
        return true;

    }

}
