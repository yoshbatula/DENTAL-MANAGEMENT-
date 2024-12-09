module org.example.dentalmanagement {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens org.example.dentalmanagement to javafx.fxml;
    exports org.example.dentalmanagement;
    exports org.example.dentalmanagement.LAUNCHER;
    exports org.example.dentalmanagement.RECEPTIONISTCONTROLLER;
    opens org.example.dentalmanagement.RECEPTIONISTCONTROLLER to javafx.fxml;
    opens org.example.dentalmanagement.LAUNCHER to javafx.fxml;
    exports org.example.dentalmanagement.ADMINCONTROLLER;
    opens org.example.dentalmanagement.ADMINCONTROLLER to javafx.fxml;
}