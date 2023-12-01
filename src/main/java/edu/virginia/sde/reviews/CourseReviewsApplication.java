package edu.virginia.sde.reviews;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CourseReviewsApplication extends Application{

    @Override
    public void start(Stage stage) throws Exception {

        // this was based on the start class from JavaFXMLHotDogVotesApplication and the HelloWorldApplication

        var fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        var scene = new Scene(fxmlLoader.load());
        var controller = (LoginController) fxmlLoader.getController();
        controller.setStage(stage);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

}
