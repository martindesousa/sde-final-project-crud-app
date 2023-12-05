package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class ReviewDetailsController {

    protected Scene previousScene;

    public Review review;

    protected CourseSearchController previousController;

    public Stage stage;

    protected DatabaseService service;

    @FXML
    public TextArea date;

    @FXML
    public TextField reviewComment;

    @FXML
    public Text reviewRating;

    @FXML
    public Text courseTitle;

    public void setPreviousScene(Scene previousScene) {
        this.previousScene = previousScene;
    }

//    public void setUser(User user) {
//        this.user = user;
//    }

    public void setPreviousController(CourseSearchController previousController) {
        this.previousController = previousController;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setService(DatabaseService service){
        this.service = service;
    }

    public void setTexts() {
        date.setText(review.getTime().toString());
        reviewComment.setText(review.getComment());
        reviewRating.setText(""+review.getRating());
        courseTitle.setText(review.getCourse().getCourseTitle());
    }

    public void handleBack(){
        previousController.setStage(stage);
        previousController.setService(service);
        previousController.handleSearch();
        stage.setTitle("Course Search");
        stage.setScene(previousScene);
        stage.show();
    }


}
