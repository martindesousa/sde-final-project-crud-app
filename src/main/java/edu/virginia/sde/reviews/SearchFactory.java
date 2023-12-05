package edu.virginia.sde.reviews;

import java.util.ArrayList;



public class SearchFactory {

    public static ArrayList<Course> getMatchingCourses(DatabaseService service, String mnemonic, String num, String title){

        if(!num.equals("")) {
            try {
                Integer.parseInt(num);
            } catch (NumberFormatException e) {
                return new ArrayList<>();
            }
        }

        mnemonic = mnemonic.toUpperCase();

        boolean mBool = !(mnemonic.equals(""));
        boolean nBool = !(num.equals(""));
        boolean tBool = !(title.equals(""));

        if(mBool){
            if(nBool){
                if(tBool){
                    //all three inputs
                    return service.getSimilarTitleCourses(title,
                            service.getSameNumberCourses(Integer.parseInt(num),
                                    service.getSameMnemonicCourses(mnemonic)));

                }
                return service.getSameNumberCourses(Integer.parseInt(num),
                        service.getSameMnemonicCourses(mnemonic));
            }
            if(tBool){
                return service.getSimilarTitleCourses(title,
                        service.getSameMnemonicCourses(mnemonic));

            }
            return service.getSameMnemonicCourses(mnemonic);
        }if(nBool){
            if(tBool){
                return service.getSimilarTitleCourses(title,
                        service.getSameNumberCourses(Integer.parseInt(num)));

            }
            return service.getSameNumberCourses(Integer.parseInt(num));

        }if(tBool){
            return service.getSimilarTitleCourses(title);
        }

        return service.getAllCourses();
    }
}
