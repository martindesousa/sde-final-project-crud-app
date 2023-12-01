package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label errorLabel;

    public void handleLogin(){
        LoginValidator validator = new LoginValidator();
        String usernameString = usernameField.getText();
        String passwordString = passwordField.getText();


        if(!validator.validatePassword(usernameString,passwordString)){
            errorLabel.setText("Please Enter a Valid Login or Create New User");
            return;

        }
        errorLabel.setText("");
        successfulLogIn();

    }

    public void handleCreateUser(){
        NewUserValidator validator = new NewUserValidator();
        String usernameString = usernameField.getText();
        String passwordString = passwordField.getText();
        if(!validator.validate(usernameString,passwordString)){
            errorLabel.setText("Please Enter New User Login Information");
            return;
        }
        errorLabel.setText("");
        successfulLogIn();

    }

    private void successfulLogIn(){
        try {
            //This was based on the JavaFXML HotDog votes "handleGraph" method
            var fxmlLoader = new FXMLLoader(LoginController.class.getResource("course-search.fxml"));
            var newScene = new Scene(fxmlLoader.load());
            var controller = (CourseSearchController) fxmlLoader.getController();
            controller.setStage(stage);
            stage.setTitle("Course Search");
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
