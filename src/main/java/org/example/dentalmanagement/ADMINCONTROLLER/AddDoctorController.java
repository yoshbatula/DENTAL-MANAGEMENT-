package org.example.dentalmanagement.ADMINCONTROLLER;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.dentalmanagement.DATABASE.DATABASECONNECTIVITY;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddDoctorController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private Button AddBTN;

    @FXML
    private TextField ContactInfoTF;

    @FXML
    private TextField FullNameTF;

    @FXML
    private TextField SpecTF;

    private DoctorsController doctorsController;

    private boolean isUpdateMode = false;

    private int doctorID;

    public void setDoctorsController(DoctorsController doctorsController) {
        this.doctorsController = doctorsController;
    }


    public void AddDoctors() {
        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();
        String fullname = FullNameTF.getText().trim();
        String spec = SpecTF.getText().trim();
        String contact = ContactInfoTF.getText().trim();

        if (fullname.isEmpty() || spec.isEmpty() || contact.isEmpty()) {
            if (fullname.isEmpty()) FullNameTF.setStyle("-fx-border-color: red");
            if (spec.isEmpty()) SpecTF.setStyle("-fx-border-color: red");
            if (contact.isEmpty()) ContactInfoTF.setStyle("-fx-border-color: red");
            return;
        }

        try {
            PreparedStatement pmt;
            if (isUpdateMode) {
                pmt = db.getConnection().prepareStatement(
                        "UPDATE doctor SET FullName = ?, Specialization = ?, ContactInfo = ? WHERE DoctorID = ?"
                );
                pmt.setString(1, fullname);
                pmt.setString(2, spec);
                pmt.setString(3, contact);
                pmt.setInt(4, doctorID);
            } else {
                pmt = db.getConnection().prepareStatement(
                        "INSERT INTO doctor (FullName, Specialization, ContactInfo) VALUES (?, ?, ?)"
                );
                pmt.setString(1, fullname);
                pmt.setString(2, spec);
                pmt.setString(3, contact);
            }

            if (pmt.executeUpdate() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Doctor");
                alert.setHeaderText(null);
                alert.setContentText(isUpdateMode ? "Doctor updated successfully." : "Doctor added successfully.");
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


    public void setDoctorData(int doctorID, String fullName, String specialization, String contactInfo) {
        this.isUpdateMode = true;
        this.doctorID = doctorID;

        FullNameTF.setText(fullName);
        SpecTF.setText(specialization);
        ContactInfoTF.setText(contactInfo);
    }
}
