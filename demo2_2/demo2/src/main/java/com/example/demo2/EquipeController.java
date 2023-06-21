package com.example.demo2;
import com.example.demo2.entities.Equipe;
import com.example.demo2.service.EquipeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;


public class EquipeController {
    @FXML
    private TableView<Equipe> equipeTable;

    @FXML
    private TableColumn<Equipe, Integer> idColumn;

    @FXML
    private TableColumn<Equipe, String> nomColumn;

    @FXML
    private TableColumn<Equipe, String> dateCreationColumn;

    @FXML
    private TableColumn<Equipe, String> entraineurColumn;

    private EquipeService equipeService;



    public EquipeController() {
        // Initialize the EquipeService
        equipeService = new EquipeService();
    }

    @FXML
    private void initialize() {
        // Configure table columns to display Equipe properties
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        dateCreationColumn.setCellValueFactory(new PropertyValueFactory<>("formattedDateCreation"));
        entraineurColumn.setCellValueFactory(new PropertyValueFactory<>("entraineur"));

        // Load equipe data into the table
        List<Equipe> equipeList = equipeService.findAll();
        ObservableList<Equipe> equipeObservableList = FXCollections.observableArrayList(equipeList);
        equipeTable.setItems(equipeObservableList);
    }




}
