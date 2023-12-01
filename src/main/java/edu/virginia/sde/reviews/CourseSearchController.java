package edu.virginia.sde.reviews;

import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CourseSearchController {


    @FXML
    private Label addCourseErrorLabel;

    @FXML
    private TextField newCourseSubject;

    @FXML
    private TextField newCourseNumber;

    @FXML
    private TextField newCourseTitle;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void handleAddCourse(){

        // got info on getting text from textfields from this link
        // https://docs.oracle.com/javafx/2/ui_controls/text-field.htm

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
        //This was based on the JavaFXML HotDog votes "handleGraph" method
        try {
            var fxmlLoader = new FXMLLoader(LoginController.class.getResource("login.fxml"));
            var newScene = new Scene(fxmlLoader.load());
            var controller = (LoginController) fxmlLoader.getController();
            controller.setStage(stage);
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void handleMyReviews(){

    }
}
