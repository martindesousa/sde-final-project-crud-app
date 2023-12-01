package edu.virginia.sde.reviews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void handleLogin(){
        LoginValidator validator = new LoginValidator();

    }

    private void successfulLogIn(){
        try {
            //This was based on the JavaFXML HotDog votes "handleGraph" method
            var fxmlLoader = new FXMLLoader(LoginController.class.getResource("course-search.fxml"));
            var newScene = new Scene(fxmlLoader.load());
            var controller = (CourseSearchController) fxmlLoader.getController();
            controller.setStage(stage);
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
