package com.example.demo2;

import com.example.demo2.entities.Equipe;
import com.example.demo2.service.EquipeService;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EquipeTableUtil {

    public static TableColumn<Equipe, Integer> getIdColumn() {
        TableColumn<Equipe, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        return idColumn;
    }

    public static TableColumn<Equipe, String> getNomColumn() {
        TableColumn<Equipe, String> nomColumn = new TableColumn<>("Nom");
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        return nomColumn;
    }

    public static TableColumn<Equipe, Date> getDateCreationColumn() {
        TableColumn<Equipe, Date> dateCreationColumn = new TableColumn<>("Date de création");
        dateCreationColumn.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
        dateCreationColumn.setCellFactory(column -> new TableCell<Equipe, Date>() {
            private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            @Override
            protected void updateItem(Date date, boolean empty) {
                super.updateItem(date, empty);
                if (empty || date == null) {
                    setText(null);
                } else {
                    setText(dateFormat.format(date));
                }
            }
        });
        return dateCreationColumn;
    }

    public static TableColumn<Equipe, String> getEntraineurColumn() {
        TableColumn<Equipe, String> entraineurColumn = new TableColumn<>("Entraineur");
        entraineurColumn.setCellValueFactory(new PropertyValueFactory<>("Entreneur"));
        return entraineurColumn;
    }

    public static TableColumn<Equipe, Void> getActionsColumn(TableController controller) {
        TableColumn<Equipe, Void> actionsColumn = new TableColumn<>("Actions");

        actionsColumn.setCellFactory(column -> {
            return new TableCell<>() {
                private final Button deleteButton = new Button("Delete");
                private final Button updateButton = new Button("Update");

                {
                    deleteButton.setOnAction(event -> {
                        Equipe equipe = getTableRow().getItem();
                        if (equipe != null) {
                            controller.deleteEquipe(equipe);
                            getTableView().getItems().remove(equipe);
                        }
                    });


                    updateButton.setOnAction(event -> {
                        Equipe equipe = getTableRow().getItem();
                        if (equipe != null) {
                            controller.showUpdateForm(equipe);
                        }
                    });

                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(new HBox(deleteButton, updateButton));
                    }
                }
            };
        });

        return actionsColumn;
    }

}
