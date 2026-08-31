package com.reservation.online_reservation_system;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label statusLabel;

    @FXML
    protected void onLoginButtonClick() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = UserAuthenticationService.authenticate(username, password);

        if (user != null) {
            statusLabel.setText("Access granted. Welcome, " + user.getUsername() + "!");
            statusLabel.setStyle("-fx-text-fill: #2e7d32;");
        } else {
            statusLabel.setText("Access denied. Invalid credentials.");
            statusLabel.setStyle("-fx-text-fill: #c62828;");
            passwordField.clear();
            passwordField.requestFocus();
        }
    }
}
