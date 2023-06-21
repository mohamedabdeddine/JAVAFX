module com.example.demo2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires poi.ooxml;
    requires poi;
    requires com.fasterxml.jackson.databind;
    opens com.example.demo2.entities to javafx.base;


    opens com.example.demo2 to javafx.fxml;
    exports com.example.demo2;
}