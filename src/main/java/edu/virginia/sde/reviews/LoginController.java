package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

    private Stage stage;

    private DatabaseService service;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label messageLabel;

    public void setService(DatabaseService service) {
        this.service = service;
    }

    public void handleExit() {
        Stage stage = (Stage) this.stage.getScene().getWindow();
        service.closeSession();
        stage.close();
    }

    public void handleLogin(){

        String usernameString = usernameField.getText();
        String passwordString = passwordField.getText();

        if(usernameString.equals("")){
            messageLabel.setText("Please Enter a Username");
            return;
        }

        if(passwordString.equals("")){
            messageLabel.setText("Please Enter a Password");
            return;
        }

        if(!service.usernameExists(usernameString)){
            messageLabel.setText("Username Not Found");
            return;
        }

        if(!service.passwordCorrect(usernameString,passwordString)){
            messageLabel.setText("Incorrect Password");
            return;
        }

        messageLabel.setText("");

        successfulLogIn(service.getUser(usernameString));

    }

    public void handleCreateUser(){

        String usernameString = usernameField.getText();
        String passwordString = passwordField.getText();

        if(usernameString.equals("")){
            messageLabel.setText("Please Enter a Username");
            return;
        }

        if(passwordString.equals("")){
            messageLabel.setText("Please Enter a Password");
            return;
        }

        if(service.usernameExists(usernameString)){
            messageLabel.setText("This Username is Already Taken");
            return;
        }

        if(!NewUserValidator.validatePassword(passwordString)){
            messageLabel.setText("Password Must Be at Least 8 Characters");
            return;
        }

        messageLabel.setText("User Created Successfully");
        service.addUser(new User(usernameString, passwordString));
        // Perform database operation here (e.g., saving, updating, querying)


        //add to database

    }

    private void successfulLogIn(User user){
        try {
            //This was based on the JavaFXML HotDog votes "handleGraph" method
            var fxmlLoader = new FXMLLoader(CourseSearchController.class.getResource("course-search.fxml"));
            var newScene = new Scene(fxmlLoader.load());
            var controller = (CourseSearchController) fxmlLoader.getController();
            controller.setStage(stage);
            controller.setUser(user);
            controller.setService(service);
            controller.setTable();
            stage.setTitle("Course Search");
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
