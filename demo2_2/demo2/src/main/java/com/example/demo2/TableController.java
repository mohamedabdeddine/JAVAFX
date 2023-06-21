package com.example.demo2;

import com.example.demo2.dao.Impl.EquipImpl;
import com.example.demo2.dao.daoEquipe;
import com.example.demo2.entities.Equipe;
import com.example.demo2.service.EquipeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class TableController {
    @FXML
    private TextField nomField;

    @FXML
    private DatePicker dateCreationField;

    @FXML
    private TextField entraineurField;

    @FXML
    private GridPane newEquipeDataPane;

    @FXML
    private TableView<Equipe> table;

    private ObservableList<Equipe> equipeList;
    private daoEquipe daoequipe = new EquipImpl();
    private EquipeService equipeService;

    public TableController() {
        // Initialize the EquipeService
        equipeService = new EquipeService();
    }

    public void initialize() {
        // Add columns to the TableView
        table.getColumns().addAll(
                EquipeTableUtil.getIdColumn(),
                EquipeTableUtil.getNomColumn(),
                EquipeTableUtil.getDateCreationColumn(),
                EquipeTableUtil.getEntraineurColumn());
        table.getColumns().add(EquipeTableUtil.getActionsColumn(this));

        // Initialize the ObservableList
        equipeList = FXCollections.observableArrayList();

        // Load equipe data into the table
        equipeList.addAll(equipeService.findAll());
        table.setItems(equipeList);

        // Start monitoring for database changes
        startDatabaseMonitoring();
    }

    private void startDatabaseMonitoring() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000); // Adjust the time interval as needed
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Retrieve updated data from the database
                List<Equipe> updatedEquipeList = equipeService.findAll();

                // Update the ObservableList on the JavaFX Application Thread
                table.getItems().clear();
                table.getItems().addAll(updatedEquipeList);
            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    public void Importer() {
        try {
            equipeService.importerDepuisExcel("C:/Users/ULTRAPC/Desktop/demo2_2 (1)/demo2/src/main/resources/dataimporte.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void Exporter() {
        equipeService.exporterVersExcel("C:/Users/ULTRAP/Desktop/demo2_2 (1)/demo2_2/demo2/src/main/resources/dataexporte.xls");
    }

    @FXML
    public void addEquipe() {
        String nom = nomField.getText();
        LocalDate dateCreation = dateCreationField.getValue();
        String entraineur = entraineurField.getText();

        // Convert LocalDate to java.util.Date
        java.util.Date dateCreationFormatted = java.sql.Date.valueOf(dateCreation);

        // Create a new Equipe object
        Equipe equipe = new Equipe(1, nom, dateCreationFormatted, entraineur);

        // Save the Equipe to the database
        equipeService.save(equipe);

        // Add the Equipe to the ObservableList
        equipeList.add(equipe);

        // Clear the fields
        clearFields();
    }

    private void clearFields() {
        nomField.clear();
        dateCreationField.setValue(null);
        entraineurField.clear();
    }

    public void deleteEquipe(Equipe equipe) {
        // Remove the equipe from the ObservableList
        equipeList.remove(equipe);

        // Delete the corresponding record from the database
        equipeService.remove(equipe);
    }

    public void updateEquipe(Equipe equipe) {
        // Handle update logic here
        // ...
    }

    public void showUpdateForm(Equipe equipe) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("edit_equipe_form.fxml"));
        Parent root;
        try {
            root = loader.load();
            EditEquipeFormController editController = loader.getController();
            editController.setEquipe(equipe);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
