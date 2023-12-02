package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.io.IOException;

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
        messageLabel.setTextFill(Color.RED);

        if(usernameString.equals("")){
            messageLabel.setText("Please Enter a Username");
            return;
        }

        if(passwordString.equals("")){
            messageLabel.setText("Please Enter a Password");
            return;
        }

        if(!LoginValidator.validateUsername(usernameString, service)){
            messageLabel.setText("Username Not Found");
            return;
        }

        if(!LoginValidator.validatePassword(usernameString,passwordString, service)){
            messageLabel.setText("Incorrect Password");
            return;
        }

        messageLabel.setText("");
        successfulLogIn();

    }

    public void handleCreateUser(){

        String usernameString = usernameField.getText();
        String passwordString = passwordField.getText();
        messageLabel.setTextFill(Color.RED);

        if(usernameString.equals("")){
            messageLabel.setText("Please Enter a Username");
            return;
        }

        if(passwordString.equals("")){
            messageLabel.setText("Please Enter a Password");
            return;
        }

        if(!NewUserValidator.validateUsername(usernameString, service)){
            messageLabel.setText("This Username is Already Taken");
            return;
        }

        if(!NewUserValidator.validatePassword(passwordString)){
            messageLabel.setText("Password Must Be at Least 8 Characters");
            return;
        }
        User user = new User();
        user.setUsername(usernameString);
        user.setPassword(passwordString);
        service.addUser(user);
        messageLabel.setTextFill(Color.color(0, 0.7, 0.2));
        messageLabel.setText("User Created Successfully! You can now log in.");
        usernameField.clear();
        passwordField.clear();

        //add to database

    }

    private void successfulLogIn(){
        try {
            //This was based on the JavaFXML HotDog votes "handleGraph" method
            var fxmlLoader = new FXMLLoader(LoginController.class.getResource("course-search.fxml"));
            var newScene = new Scene(fxmlLoader.load());
            var controller = (CourseSearchController) fxmlLoader.getController();
            controller.setStage(stage);
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
