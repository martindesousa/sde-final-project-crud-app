package edu.virginia.sde.reviews;

import javafx.fxml.FXML;

import javafx.scene.control.Label;

public class CourseSearchController {


    @FXML
    private Label searchErrorLabel;

    public void handleAddCourse(){

    }

    public void handleSearch(){
        searchErrorLabel.setText("Course Does Not Exist");
    }

    public void handleLogOut(){

    }

    public void handleMyReviews(){

    }
}
