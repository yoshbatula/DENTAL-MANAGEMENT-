module org.example.dentalmanagement {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens org.example.dentalmanagement to javafx.fxml;
    exports org.example.dentalmanagement;
    exports org.example.dentalmanagement.LAUNCHER;
    exports org.example.dentalmanagement.CONTROLLER;
    opens org.example.dentalmanagement.CONTROLLER to javafx.fxml;
    opens org.example.dentalmanagement.LAUNCHER to javafx.fxml;
}