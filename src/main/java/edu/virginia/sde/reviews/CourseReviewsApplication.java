package edu.virginia.sde.reviews;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.util.logging.Level;

public class CourseReviewsApplication extends Application{

    @Override
    public void start(Stage stage) throws Exception {

        //From ClientDemo
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        Session session = HibernateUtil.getSessionFactory().openSession();

        // this was based on the start class from JavaFXMLHotDogVotesApplication and the HelloWorldApplication

        var fxmlLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        var scene = new Scene(fxmlLoader.load());
        var controller = (LoginController) fxmlLoader.getController();
        DatabaseService service = new DatabaseService(session);
        controller.setService(service);
        controller.setStage(stage);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();

    }

}
