package edu.virginia.sde.reviews;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class CourseReviewsController {

    public Stage stage;

    protected DatabaseService service;



    public void setStage(Stage stage){
        this.stage = stage;
    }

    protected void setService(DatabaseService service){
        this.service = service;
    }




}
