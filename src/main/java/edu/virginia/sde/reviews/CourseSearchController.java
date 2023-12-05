package edu.virginia.sde.reviews;

import jakarta.persistence.criteria.CriteriaBuilder;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CourseSearchController {

    @FXML
    public TableView<Course> courseTable;

    @FXML
    public TableColumn<Course, String> subjectColumn;

    @FXML
    public TableColumn<Course, Integer> numberColumn;

    @FXML
    public TableColumn<Course, String> titleColumn;

    @FXML
    public TableColumn<Course, String> ratingColumn;

    private User user;


    private DatabaseService service;

    @FXML
    private Label addCourseErrorLabel;

    @FXML
    private TextField newCourseSubject;

    @FXML
    private TextField newCourseNumber;

    @FXML
    private TextField newCourseTitle;

    @FXML
    public TextField searchSubject;

    @FXML
    public TextField searchNumber;

    @FXML
    public TextField searchTitle;

    public String lastSearchSubject;
    public String lastSearchNumber;
    public String lastSearchTitle;

    public String getLastSearchSubject() {
        return lastSearchSubject;
    }

    public void setLastSearchSubject(String lastSearchSubject) {
        this.lastSearchSubject = lastSearchSubject;
    }

    public String getLastSearchNumber() {
        return lastSearchNumber;
    }

    public void setLastSearchNumber(String lastSearchNumber) {
        this.lastSearchNumber = lastSearchNumber;
    }

    public String getLastSearchTitle() {
        return lastSearchTitle;
    }

    public void setLastSearchTitle(String lastSearchTitle) {
        this.lastSearchTitle = lastSearchTitle;
    }

    private Stage stage;

    public void setService(DatabaseService service) {
        this.service = service;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setTable(){

        //This code was based on the NYT best sellers tableView example
        // and documentation of tableViews given while hovering over tableView Class

        courseTable.getItems().clear();

        List<Course> courseList = service.getAllCourses();

        /*

        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("abcd", 1234, "it sucked"));

         */

        ObservableList<Course> obvCourseList = FXCollections.observableList(courseList);
        subjectColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("subjectMnemonic"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<Course, Integer>("courseNumber"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("courseTitle"));
        // learned how to do simpleStringProperties from chatGPT example given in MyReviewsController.setTable()
        ratingColumn.setCellValueFactory((review) -> {
            double averageScore = review.getValue().getAvgRating();
            if (averageScore == 0) { return new SimpleStringProperty("");}
            else {return new SimpleStringProperty(String.format("%.2f",averageScore));}
        });
        //ratingColumn.setCellValueFactory();
        courseTable.getColumns().setAll(subjectColumn, numberColumn, titleColumn,ratingColumn);
        courseTable.getItems().addAll(obvCourseList);

    }

    public void handleShowAll(){
        handleSearch("","","");
    }

    public void setTable(ArrayList<Course> courseList){

        //This code was based on the NYT best sellers tableView example
        // and documentation of tableViews given while hovering over tableView Class

        courseTable.getItems().clear();

        /*

        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("abcd", 1234, "it sucked"));

         */

        ObservableList<Course> obvCourseList = FXCollections.observableList(courseList);
        subjectColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("subjectMnemonic"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<Course, Integer>("courseNumber"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Course, String>("courseTitle"));

        // learned how to do simpleStringProperties from chatGPT example given in MyReviewsController.setTable()

        ratingColumn.setCellValueFactory((review) -> {
            double averageScore = review.getValue().getAvgRating();
            if (averageScore == 0) { return new SimpleStringProperty("");}
            else {return new SimpleStringProperty(String.format("%.2f",averageScore));}
        });
        //ratingColumn.setCellValueFactory();
        courseTable.getColumns().setAll(subjectColumn, numberColumn, titleColumn,ratingColumn);
        //ratingColumn.setCellValueFactory();
        courseTable.getColumns().setAll(subjectColumn, numberColumn, titleColumn,ratingColumn);
        courseTable.getItems().addAll(obvCourseList);

    }

    protected void setUser(User user){this.user = user;}

    public void setTable(String nameSearch){
        /*ist<Course> courseList = service.getCourseList();
        ObservableList<Course> obvCourseList = FXCollections.observableList(courseList);
        courseTable.getItems().clear();
        courseTable.getItems().addAll(obvCourseList);*/
    }

    public void handleAddCourse(){

        // got info on getting text from textfields from this link
        // https://docs.oracle.com/javafx/2/ui_controls/text-field.htm

        String subject = newCourseSubject.getText();
        String number = newCourseNumber.getText();
        String title = newCourseTitle.getText();

        subject = subject.toUpperCase();

        if(subject.equals("")){
            addCourseErrorLabel.setTextFill(Color.color(1, 0, 0));
            addCourseErrorLabel.setText("Please Enter Course Subject");
            return;
        }
        if(number.equals("")){
            addCourseErrorLabel.setTextFill(Color.color(1, 0, 0));
            addCourseErrorLabel.setText("Please Enter Course Number");
            return;
        }
        if(title.equals("")){
            addCourseErrorLabel.setTextFill(Color.color(1, 0, 0));
            addCourseErrorLabel.setText("Please Enter Course Title");
            return;
        }

        if(!NewCourseValidator.ValidateSubject(subject)) {
            addCourseErrorLabel.setTextFill(Color.color(1, 0, 0));
            addCourseErrorLabel.setText("Course Subject Must Be 2-4 Letters");
            return;
        }
        if(!NewCourseValidator.ValidateNumber(number)) {
            addCourseErrorLabel.setTextFill(Color.color(1, 0, 0));
            addCourseErrorLabel.setText("Course Number Must Be 4 Numbers");
            return;
        }
        if(!NewCourseValidator.ValidateTitle(title)) {
            addCourseErrorLabel.setTextFill(Color.color(1, 0, 0));
            addCourseErrorLabel.setText("Course Title Must Be Between 1 and 50 Characters Long");
            return;
        }

        int intNumber = Integer.parseInt(number);

        if(service.existingCourse(subject, intNumber, title)){
            addCourseErrorLabel.setTextFill(Color.color(1, 0, 0));
            addCourseErrorLabel.setText("This Course Already Exists");
            return;
        }
        addCourseErrorLabel.setTextFill(Color.color(0, 0.7, 0.2));
        addCourseErrorLabel.setText("Course Successfully Added");
        newCourseSubject.clear();
        newCourseTitle.clear();
        newCourseNumber.clear();

        service.addCourse(new Course(subject.toUpperCase(), intNumber, title));
        handleSearch();
        //add to database
        //

    }

    public void handleSearch(){

        lastSearchSubject = searchSubject.getText();
        lastSearchNumber = searchNumber.getText();
        lastSearchTitle = searchTitle.getText();

        setTable(SearchFactory.getMatchingCourses(service, lastSearchSubject, lastSearchNumber, lastSearchTitle));

    }

    public void handleSearch(String subject, String number, String title){

        setTable(SearchFactory.getMatchingCourses(service, subject, number, title));

    }


    public void handleCourseSelection(){
        Course newCourse = courseTable.getSelectionModel().getSelectedItem();
        if(newCourse == null){return;}

        try {
            var fxmlLoader = new FXMLLoader(CourseReviewsController.class.getResource("course-reviews.fxml"));
            var newScene = new Scene(fxmlLoader.load());
            var controller = (CourseReviewsController) fxmlLoader.getController();
            controller.setStage(stage);
            controller.setService(service);
            controller.setPreviousController(this);
            controller.setPreviousScene(stage.getScene());
            controller.setCourse(newCourse);
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

    public void handleLogOut(){
        //This was based on the JavaFXML HotDog votes "handleGraph" method
        try {
            var fxmlLoader = new FXMLLoader(LoginController.class.getResource("login.fxml"));
            var newScene = new Scene(fxmlLoader.load());
            var controller = (LoginController) fxmlLoader.getController();
            controller.setStage(stage);
            controller.setService(service);
            stage.setTitle("Login");
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void handleMyReviews(){
        try {
            var fxmlLoader = new FXMLLoader(MyReviewsController.class.getResource("my-reviews.fxml"));
            var newScene = new Scene(fxmlLoader.load());
            var controller = (MyReviewsController) fxmlLoader.getController();
            controller.setStage(stage);
            controller.setService(service);
            controller.setPreviousController(this);
            controller.setUser(user);
            controller.setTable();
            controller.setPreviousScene(stage.getScene());
            stage.setTitle("My Reviews");
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
