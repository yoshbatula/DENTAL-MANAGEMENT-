package org.example.dentalmanagement.ADMINCONTROLLER;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import org.example.dentalmanagement.DATABASE.DATABASECONNECTIVITY;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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


    public void setData(int appointmentID, String patientName, String appointmentDate, String appointmentTime, String service, int patientID, Double servicecost) {
        this.appoinmentID = appointmentID;
        this.appoinmentName = patientName;
        this.service = service;
        this.PatientID = patientID;
        this.ServiceCost = servicecost;

        AppointmentIDLabel.setText(String.valueOf(appointmentID));
        PatientNameLabel.setText(patientName);
        ServiceLabel.setText(service);
        ServiceCostLabel.setText(servicecost.toString());
        TotalLabel.setText(servicecost.toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialization if needed
    }

    public void PaymentMethod() {
        Singleton instance = Singleton.getInstance();
        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();


    }

mm
}
