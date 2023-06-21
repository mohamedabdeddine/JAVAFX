package com.example.demo2;

import com.example.demo2.entities.Equipe;
import com.example.demo2.service.EquipeService;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class EditEquipeFormController {

    @FXML
    private TextField idField;

    @FXML
    private TextField nomField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField entraineurField;

    private Equipe equipe;

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
        // Set the values in the form
        idField.setText(String.valueOf(equipe.getId()));
        nomField.setText(equipe.getNom());
        datePicker.setValue(equipe.getDateCreation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        entraineurField.setText(equipe.getEntreneur());
    }

    @FXML
    public void updateEquipe() {
        // Get the updated values from the form
        int id= Integer.parseInt(idField.getText());
        String nom = nomField.getText();
        LocalDate dateCreation = datePicker.getValue();
        String entraineur = entraineurField.getText();
        // Convert LocalDate to java.util.Date
        java.util.Date dateCreationFormatted = java.sql.Date.valueOf(dateCreation);
        // Create a new Equipe object
        Equipe equipee = new Equipe(id,nom, dateCreationFormatted, entraineur);
        System.out.println(id+nom+dateCreationFormatted+entraineur);
        // Create an instance of EquipeService
        EquipeService equipeService = new EquipeService();
        // Call the update method to update the equipe in the database
        equipeService.update(equipee);
        // Close the update form
        Stage stage = (Stage) idField.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void close() {
        // Get the reference to the main application window and close it
        Stage stage = (Stage) idField.getScene().getWindow();
        stage.close();
    }

}
