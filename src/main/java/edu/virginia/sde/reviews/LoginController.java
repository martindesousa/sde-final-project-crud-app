package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private Label messageLabel;
    @FXML
    private Button exitButton;

    public void handleExit() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

    }
    public void handleUserCreated() {
        messageLabel.setText("User created successfully.");
    }

}
