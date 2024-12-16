package org.example.dentalmanagement.ADMINCONTROLLER;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.dentalmanagement.DATABASE.DATABASECONNECTIVITY;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class AddServices {


    @FXML
    private Button ProceedBTN;

    @FXML
    private TextField ServiceCostTF;

    @FXML
    private TextField ServiceNameTF;

    private ServicesController servicesController;

    public void setServicesController(ServicesController servicesController) {
        this.servicesController = servicesController;
    }

    public void AddServices() {
        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();
        String serviceName = ServiceNameTF.getText();
        Double serviceCost = Double.parseDouble(ServiceCostTF.getText());

        if (ServiceNameTF.getText().isBlank()) {
            ServiceNameTF.setStyle("-fx-border-color: red");
        } else if (ServiceCostTF.getText().isBlank()) {
            ServiceCostTF.setStyle("-fx-border-color: red");
        } else {
            try {
                PreparedStatement pmt;

                if (UpdateServices) {
                    pmt = db.getConnection().prepareStatement("UPDATE services SET ServiceName = ?, ServiceCost = ? WHERE ServiceID = ?");
                    pmt.setString(1, serviceName);
                    pmt.setDouble(2, serviceCost);
                    pmt.setInt(3, ServiceID);
                } else {
                    pmt = db.getConnection().prepareStatement("INSERT INTO services(ServiceName, ServiceCost) VALUES (?, ?)");
                    pmt.setString(1, serviceName);
                    pmt.setDouble(2, serviceCost);
                } if (pmt.executeUpdate() > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("INFORMATION");
                    alert.setHeaderText(null);
                    alert.setContentText(UpdateServices ? "Services update successfully." : "Services add successfully.");
                    alert.showAndWait();

                    Stage stage = (Stage) ProceedBTN.getScene().getWindow();
                    stage.close();

                    if (servicesController != null) {
                        servicesController.LoadServices();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Integer ServiceID;
    private Boolean UpdateServices = false;

    public void setServicesData(Integer servicesID, String servicesName, Double serviceCost) {
        this.ServiceID = servicesID;
        this.UpdateServices = true;

        this.ServiceNameTF.setText(servicesName);
        this.ServiceCostTF.setText(Double.toString(serviceCost));

    }
}
