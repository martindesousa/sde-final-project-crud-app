package edu.virginia.sde.reviews;

import jakarta.persistence.criteria.CriteriaBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

public class MyReviewsController {

    protected Scene previousScene;

    protected User user;

    protected CourseSearchController previousController;

    public Stage stage;

    protected DatabaseService service;

    @FXML
    public TableColumn<Review, String> subjectColumn;

    @FXML
    public TableColumn<Review, Integer> numberColumn;

    @FXML
    public TableColumn<Review, Integer> ratingColumn;

    @FXML
    public TableView<Review> reviewTable;

    public void setPreviousScene(Scene previousScene) {
        this.previousScene = previousScene;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPreviousController(CourseSearchController previousController) {
        this.previousController = previousController;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setService(DatabaseService service){
        this.service = service;
    }

    public void handleBack(){
        previousController.setStage(stage);
        previousController.setService(service);
        stage.setTitle("Course Search");
        stage.setScene(previousScene);
        stage.show();
    }

    public void setTable(){


        reviewTable.getItems().clear();

        List<Review> reviewList = service.getUserReviews(user);

        /*

        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("abcd", 1234, "it sucked"));

         */

        ObservableList<Review> obvReviewList = FXCollections.observableList(reviewList);
        ratingColumn.setCellValueFactory(new PropertyValueFactory<Review, Integer>("rating"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<Review, String>("subjectMnemonic"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<Review, Integer>("courseNumber"));
        //ratingColumn.setCellValueFactory();
        reviewTable.getColumns().setAll(subjectColumn, numberColumn, ratingColumn);
        reviewTable.getItems().addAll(obvReviewList);
    }

    public void handleReviewSelection(){
        Review newReview = reviewTable.getSelectionModel().getSelectedItem();
        Course newCourse = newReview.getCourse();

        try {
            var fxmlLoader = new FXMLLoader(CourseReviewsController.class.getResource("course-reviews.fxml"));
            var newScene = new Scene(fxmlLoader.load());
            var controller = (CourseReviewsController) fxmlLoader.getController();
            controller.setStage(stage);
            controller.setService(service);
            controller.setPreviousController(previousController);
            controller.setPreviousScene(previousScene);
            controller.setCourse(newReview.getCourse());
            controller.setUser(user);
            controller.setTable();
            controller.initializeReviews();
            controller.setCourseNameLabel(newCourse.getSubjectMnemonic() +" "+ newCourse.getCourseNumber() +" "+ newCourse.getCourseTitle());
            stage.setTitle(newCourse.getCourseTitle());
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }




}
