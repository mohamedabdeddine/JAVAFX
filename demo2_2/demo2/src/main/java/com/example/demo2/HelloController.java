package com.example.demo2;

import com.example.demo2.entities.Equipe;
import com.example.demo2.service.EquipeService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalDate;


public class HelloController {
    @FXML
    private TextField nomTextField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField entraineurTextField;

    @FXML
    private Label welcomeText;

    private EquipeService equipeService;

    public HelloController() {
        // Initialize the EquipeService
        equipeService = new EquipeService();
    }
    @FXML
    protected void onHelloButtonClick() {
        //welcomeText.setText("Welcome to JavaFX Application!");
        // Retrieve values from the UI components
        String nom = nomTextField.getText();
        LocalDate dateCreation = datePicker.getValue();
        String entraineur = entraineurTextField.getText();

        // Convert LocalDate to java.util.Date
        java.util.Date dateCreationFormatted = java.sql.Date.valueOf(dateCreation);

        // Create a new Equipe object
        Equipe equipe = new Equipe(1,nom, dateCreationFormatted, entraineur);

        // Save the Equipe to the database
        equipeService.save(equipe);

        // Show a success message
        welcomeText.setText("Equipe added successfully!");
        // Clear the fields
        clearFields();
    }
    @FXML
    private void onAnnulerButtonClick() {
        // Clear the fields
        clearFields();
    }

    private void clearFields() {
        nomTextField.clear();
        datePicker.setValue(null);
        entraineurTextField.clear();
    }
}