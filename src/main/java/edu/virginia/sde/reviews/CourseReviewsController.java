package edu.virginia.sde.reviews;

import jakarta.persistence.criteria.CriteriaBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

public class CourseReviewsController {

    public Stage stage;

    public Course course;

    protected DatabaseService service;

    protected Scene previousScene;

    protected User user;

    protected CourseSearchController previousController;

    @FXML
    public TableColumn<Review, Integer> ratingColumn;

    @FXML
    public TableColumn<Review, String> commentColumn;

    @FXML
    public TableColumn<Review, Timestamp> timeColumn;

    @FXML
    public TableView<Review> reviewTable;

    // used documentation from following website for RadioButtons and ToggleGroups
    //https://docs.oracle.com/javafx/2/ui_controls/radio-button.htm

    @FXML
    public RadioButton rating5;

    @FXML
    public RadioButton rating4;

    @FXML
    public RadioButton rating3;

    @FXML
    public RadioButton rating2;

    @FXML
    public RadioButton rating1;

    @FXML
    public Label errorLabel;

    @FXML
    public Label courseNameLabel;

    public ToggleGroup ratingGroup;

    @FXML
    public TextArea commentArea;


    public void setStage(Stage stage){
        this.stage = stage;
    }

    protected void setService(DatabaseService service){
        this.service = service;
    }

    public void setCourseNameLabel(String text){
        courseNameLabel.setText(text);
    }

    public void initializeReviews(){
        ratingGroup = new ToggleGroup();
        rating5.setToggleGroup(ratingGroup);
        rating4.setToggleGroup(ratingGroup);
        rating3.setToggleGroup(ratingGroup);
        rating2.setToggleGroup(ratingGroup);
        rating1.setToggleGroup(ratingGroup);

        if(service.containsReview(user,course)) {
            Review review = service.getReview(user,course);
            commentArea.setText(review.getComment());
            int rating = review.getRating();
            if(rating == 5){rating5.setSelected(true);}
            if(rating == 4){rating4.setSelected(true);}
            if(rating == 3){rating3.setSelected(true);}
            if(rating == 2){rating2.setSelected(true);}
            if(rating == 1){rating1.setSelected(true);}
        }
    }

    public void setPreviousScene(Scene previousScene) {
        this.previousScene = previousScene;
    }

    protected void setUser(User user){
        this.user = user;
    }

    public void setCourse(Course course){
        this.course = course;
    }

    public void setTable(){

        //This code was based on the NYT best sellers tableView example
        // and documentation of tableViews given while hovering over tableView Class

        reviewTable.getItems().clear();

        List<Review> reviewList = service.getCourseReviews(course);

        /*

        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("abcd", 1234, "it sucked"));

         */

        ObservableList<Review> obvReviewList = FXCollections.observableList(reviewList);
        ratingColumn.setCellValueFactory(new PropertyValueFactory<Review, Integer>("rating"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<Review, String>("comment"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Review, Timestamp>("time"));
        //ratingColumn.setCellValueFactory();
        reviewTable.getColumns().setAll(ratingColumn, commentColumn, timeColumn);
        reviewTable.getItems().addAll(obvReviewList);

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
        //need to update previous thing with new review stats
    }

    private int getRating(){
        if(ratingGroup.getSelectedToggle()==null){return 0;}
        if(ratingGroup.getSelectedToggle() == rating5){return 5;}
        if(ratingGroup.getSelectedToggle() == rating4){return 4;}
        if(ratingGroup.getSelectedToggle() == rating3){return 3;}
        if(ratingGroup.getSelectedToggle() == rating2){return 2;}
        return 1;
    }

    public void handleSubmitReview(){
        if(getRating() == 0){
            errorLabel.setTextFill(Color.color(1, 0, 0));
            errorLabel.setText("Please Select a Rating");
            return;
        }
        if(service.containsReview(user, course)){
            service.updateReview(service.getReview(user,course), commentArea.getText(), getRating());
            service.updateRatings(course);
            //update course rating
            errorLabel.setTextFill(Color.color(0, 0.7, 0.2));
            errorLabel.setText("Review Updated");
            setTable();
            return;
        }
        errorLabel.setTextFill(Color.color(0, 0.7, 0.2));
        errorLabel.setText("Review Added");
        service.addReview(new Review(user, getRating(), course, commentArea.getText(), new Timestamp(System.currentTimeMillis())));
        setTable();

    }

    public void handleDelete(){
        if(!service.containsReview(user, course)){
            errorLabel.setTextFill(Color.color(1, 0, 0));
            errorLabel.setText("Cannot Delete Review: You Have Not Submitted A Review for this Class");
            return;
        }
        service.removeReview(service.getReview(user,course));
        errorLabel.setTextFill(Color.color(0, 0.7, 0.2));
        errorLabel.setText("Successfully Removed");
        setTable();
    }




}
