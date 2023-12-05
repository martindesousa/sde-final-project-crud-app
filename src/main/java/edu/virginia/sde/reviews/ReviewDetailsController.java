package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;

public class ReviewDetailsController {

    protected Scene previousScene;

    public Review review;

    protected CourseReviewsController previousController;

    public Stage stage;

    protected DatabaseService service;

    public Timestamp date;

    public String comment;

    public int rating;

    public String title;

    @FXML
    public TextField dateField;

    @FXML
    public TextArea reviewComment;

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

    public void setPreviousController(CourseReviewsController previousController) {
        this.previousController = previousController;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setService(DatabaseService service){
        this.service = service;
    }

    public void setReview(Review review) { this.review = review; }

    public void setDate(Timestamp timestamp) { this.date = timestamp; }


    public void setComment(String comment) { this.comment = comment; }


    public void setRating(int rating) { this.rating = rating; }


    public void setTitle(String title) { this.title = title; }

    public void setTexts() {
        dateField.setText(date.toString());
        reviewComment.setText(comment);
        reviewRating.setText(""+rating);
        courseTitle.setText(title);
    }

    public void handleBack(){
        previousController.setStage(stage);
        previousController.setService(service);
        //previousController.handleSearch();
        stage.setTitle("Course Search");
        stage.setScene(previousScene);
        stage.show();
    }


}
