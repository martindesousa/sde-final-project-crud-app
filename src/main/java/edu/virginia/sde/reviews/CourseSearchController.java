package edu.virginia.sde.reviews;

import jakarta.persistence.criteria.CriteriaBuilder;
import javafx.beans.Observable;
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

    /*
    @FXML
    public TableColumn<Course, Double> ratingColumn;

     */


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
    private TextField searchSubject;

    @FXML
    private TextField searchNumber;

    @FXML
    private TextField searchTitle;

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
        //ratingColumn.setCellValueFactory();
        courseTable.getColumns().setAll(subjectColumn, numberColumn, titleColumn);
        courseTable.getItems().addAll(obvCourseList);

    }

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

        if(subject.equals("")){
            addCourseErrorLabel.setText("Please Enter Course Subject");
            return;
        }
        if(number.equals("")){
            addCourseErrorLabel.setText("Please Enter Course Number");
            return;
        }
        if(title.equals("")){
            addCourseErrorLabel.setText("Please Enter Course Title");
            return;
        }



        if(!NewCourseValidator.ValidateSubject(subject)) {
            addCourseErrorLabel.setText("Course Subject Must Be 2-4 Letters");
            return;
        }
        if(!NewCourseValidator.ValidateNumber(number)) {
            addCourseErrorLabel.setText("Course Number Must Be 4 Numbers");
            return;
        }
        if(!NewCourseValidator.ValidateTitle(title)) {
            addCourseErrorLabel.setText("Course Title Must Be Between 1 and 50 Characters Long");
            return;
        }
        addCourseErrorLabel.setText("");

        service.addCourse(new Course(subject, Integer.parseInt(number), title));
        setTable();
        //add to database
        //

    }

    public void handleSearch(){



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

    }
}
