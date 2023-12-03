package edu.virginia.sde.reviews;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CourseReviewsController {

    public Stage stage;

    protected DatabaseService service;

    protected Scene previousScene;

    protected CourseSearchController previousController;

    public void setStage(Stage stage){
        this.stage = stage;
    }

    protected void setService(DatabaseService service){
        this.service = service;
    }

    public void setPreviousScene(Scene previousScene) {
        this.previousScene = previousScene;
    }

    public void setPreviousController(CourseSearchController previousController) {
        this.previousController = previousController;
    }

    public void handleBack(){
        previousController.setStage(stage);
        previousController.setService(service);
        stage.setTitle("Course Search");
        stage.setScene(previousScene);
        stage.show();
    }




}
