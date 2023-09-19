module com.example.ourdictionary {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens com.example.ourdictionary to javafx.fxml;
    exports com.example.ourdictionary;
}