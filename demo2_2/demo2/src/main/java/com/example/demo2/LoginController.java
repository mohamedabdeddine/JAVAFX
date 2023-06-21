package com.example.demo2;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private Runnable onLoginSuccess;

    public void setOnLoginSuccess(Runnable onLoginSuccess) {
        this.onLoginSuccess = onLoginSuccess;
    }

    @FXML
    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Perform database verification here
        if (verifyLogin(username, password)) {
            // Login successful
            if (onLoginSuccess != null) {
                onLoginSuccess.run();
            }
        } else {
            // Login failed
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password.");
            alert.showAndWait();
        }
    }

    private boolean verifyLogin(String username, String password) {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/player";
        String dbUsername = "root";
        String dbPassword = "";

        // SQL query to check if the user exists and the password matches
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next(); // If a row is returned, login is successful

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
