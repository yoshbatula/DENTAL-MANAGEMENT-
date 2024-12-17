package org.example.dentalmanagement.ADMINCONTROLLER;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.dentalmanagement.DATABASE.DATABASECONNECTIVITY;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    private int appoinmentID;
    private String appoinmentName;
    private String appointmentDate;
    private String appointmentTime;
    private String service;
    private Double ServiceCost;
    private int PatientID;

    @FXML
    private Label AppointmentDateLabel;

    @FXML
    private Label AppointmentIDLabel;

    @FXML
    private Label AppointmentTimeLabel;

    @FXML
    private Label PatientNameLabel;

    @FXML
    private Label ServiceCostLabel;

    @FXML
    private Label ServiceLabel;

    @FXML
    private Label TotalLabel;

    @FXML
    private Button PaymentBTN;

    private String paymentStatus = "PAID";

    public void setData(int appointmentID, String patientName, String service, Double serviceCost) {
        this.appoinmentID = appointmentID;
        this.appoinmentName = patientName;
        this.service = service;
        this.ServiceCost = serviceCost;

        AppointmentIDLabel.setText(String.valueOf(appointmentID));
        PatientNameLabel.setText(patientName);
        ServiceLabel.setText(service);
        ServiceCostLabel.setText(serviceCost.toString());
        TotalLabel.setText(serviceCost.toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void PaymentMethod() {
        Singleton instance = Singleton.getInstance();
        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();

        try {

            String fetchPatientID = "SELECT PatientID FROM patient WHERE FullName = ?";
            PreparedStatement fetchPstmt = db.getConnection().prepareStatement(fetchPatientID);
            fetchPstmt.setString(1, appoinmentName);
            ResultSet rs = fetchPstmt.executeQuery();

            if (rs.next()) {
                this.PatientID = rs.getInt("PatientID");
            } else {
                showErrorAlert("Error", "Patient not found. Please check the patient's name.");
                return;
            }

            String sql = "INSERT INTO payment (AppointmentID, PatientID, PaymentStatus, Amount) VALUES (?, ?, ?, ?)";
            PreparedStatement psmt = db.getConnection().prepareStatement(sql);
            psmt.setInt(1, appoinmentID);
            psmt.setInt(2, PatientID);
            psmt.setString(3, paymentStatus);
            psmt.setDouble(4, ServiceCost);

            int rows = psmt.executeUpdate();

            if (rows > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Payment Method");
                alert.setHeaderText(null);
                alert.setContentText("Payment successfully processed.");
                alert.showAndWait();

                Stage stage = (Stage) PaymentBTN.getScene().getWindow();
                stage.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Database Error", "An error occurred while processing payment.");
        }
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
