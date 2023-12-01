package edu.virginia.sde.reviews;

import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CourseSearchController {


    @FXML
    private Label addCourseErrorLabel;

    @FXML
    private TextField newCourseSubject;

    @FXML
    private TextField newCourseNumber;

    @FXML
    private TextField newCourseTitle;

    public void handleAddCourse(){

        NewCourseValidator validator = new NewCourseValidator();

        if(!validator.ValidateSubject(newCourseSubject.getText())) {
            addCourseErrorLabel.setText("Course Subject Must Be 2-4 Letters");
            return;
        }else if(!validator.ValidateNumber(newCourseNumber.getText())) {
            addCourseErrorLabel.setText("Course Number Must Be 4 Numbers");
            return;
        }else if(!validator.ValidateTitle(newCourseTitle.getText())) {
            addCourseErrorLabel.setText("Course Title Must Be Between 1 and 50 Characters Long");
            return;
        }
        addCourseErrorLabel.setText("");
        //add to database
        //

    }

    public void handleSearch(){

    }

    public void handleLogOut(){


    }

    public void handleMyReviews(){

    }
}
