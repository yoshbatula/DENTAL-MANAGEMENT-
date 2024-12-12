package org.example.dentalmanagement.ADMINCONTROLLER;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.dentalmanagement.DATABASE.DATABASECONNECTIVITY;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddDoctorController {

    @FXML
    private Button AddBTN;

    @FXML
    private TextField ContactInfoTF;

    @FXML
    private TextField FullNameTF;

    @FXML
    private TextField SpecTF;

    private DoctorsController doctorsController;

    public void setDoctorsController(DoctorsController doctorsController) {
        this.doctorsController = doctorsController;
    }


    public void AddDoctors() {
        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();
        String fullname = FullNameTF.getText().trim();
        String spec = SpecTF.getText().trim();
        String contact = ContactInfoTF.getText().trim();

        if (fullname.isEmpty()) {
            FullNameTF.setStyle("-fx-border-color: red");
            return;
        }
        if (spec.isEmpty()) {
            SpecTF.setStyle("-fx-border-color: red");
            return;
        }
        if (contact.isEmpty()) {
            ContactInfoTF.setStyle("-fx-border-color: red");
            return;
        } else {
            try {
                PreparedStatement pmt = db.getConnection().prepareStatement("INSERT INTO doctor (FullName, Specialization, ContactInfo) VALUES (?, ?, ?)");
                pmt.setString(1, fullname);
                pmt.setString(2, spec);
                pmt.setString(3, contact);

                if (pmt.executeUpdate() > 0) {
                    System.out.println("Doctor added successfully.");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("DOCTOR");
                    alert.setHeaderText("Doctor added successfully.");
                    alert.setContentText("Doctor added successfully.");
                    alert.showAndWait();

                    Stage stage = (Stage) AddBTN.getScene().getWindow();
                    stage.close();
                    if (doctorsController != null) {
                        doctorsController.loadDoctorsFromDatabase();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
