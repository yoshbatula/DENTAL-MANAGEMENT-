module org.example.dentalmanagement {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires mysql.connector.j;

    opens org.example.dentalmanagement to javafx.fxml;
    exports org.example.dentalmanagement;
    exports org.example.dentalmanagement.LAUNCHER;
    exports org.example.dentalmanagement.RECEPTIONISTCONTROLLER;
    opens org.example.dentalmanagement.RECEPTIONISTCONTROLLER to javafx.fxml;
    opens org.example.dentalmanagement.LAUNCHER to javafx.fxml;
    exports org.example.dentalmanagement.ADMINCONTROLLER;
    opens org.example.dentalmanagement.ADMINCONTROLLER to javafx.fxml;
    opens org.example.dentalmanagement.LOGINCONTROLLER to javafx.fxml;
    exports org.example.dentalmanagement.LOGINCONTROLLER;
    opens org.example.dentalmanagement.REGISTRATIONCONTROLLER to javafx.fxml;
    exports org.example.dentalmanagement.REGISTRATIONCONTROLLER;
}