module com.example.ourdictionary {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.web;
    requires javafx.media;
    requires org.controlsfx.controls;
    requires java.sql;
    requires mysql.connector.j;
    requires com.fasterxml.jackson.databind;
    requires de.jensd.fx.glyphs.fontawesome;

    opens com.example.controller to javafx.fxml;
    opens com.example.ourdictionary to javafx.fxml;
    exports com.example.ourdictionary;
    exports com.example.controller;
}