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
import java.sql.Statement;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    private int appoinmentID;
    private String appoinmentName;
    private String appointmentDate;
    private String appointmentTime;
    private String service;
    private Double ServiceCost;

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


    public void setData(int appointmentID, String patientName, String appointmentDate, String appointmentTime, String service) {
        this.appoinmentID = appointmentID;
        this.appoinmentName = patientName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.service = service;

        AppointmentIDLabel.setText(String.valueOf(appointmentID));
        PatientNameLabel.setText(patientName);
        AppointmentDateLabel.setText(appointmentDate);
        AppointmentTimeLabel.setText(appointmentTime);
        ServiceLabel.setText(service);
        ServiceCostLabel.setText(String.valueOf(ServiceCost));
        TotalLabel.setText(String.valueOf(ServiceCost));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void PaymentMethod() {
        Singleton instance = Singleton.getInstance();
        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();
        try {
            String sql = "SELECT ServiceCost FROM services WHERE ServiceName = ?";
            PreparedStatement pmt = db.getConnection().prepareStatement(sql);
            pmt.setString(1, service);

            ResultSet rs = pmt.executeQuery();

            if (rs.next()) {
                ServiceCost = rs.getDouble("ServiceCost");
                service = rs.getString("ServiceName");
                instance.setServiceCosts(ServiceCost);
                System.out.println("SERVICE COST " + ServiceCost);
            } else {
                System.out.println("No service cost found for: " + service);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("DONE PAYING");
            alert.setHeaderText(null);
            alert.setContentText("Payment Method");
            alert.showAndWait();

            // Clear the labels
            AppointmentIDLabel.setText("");
            PatientNameLabel.setText("");
            AppointmentDateLabel.setText("");
            AppointmentTimeLabel.setText("");
            ServiceCostLabel.setText("");
            ServiceLabel.setText("");
            TotalLabel.setText("");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
