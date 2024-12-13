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
                Statement st = db.getConnection().createStatement();
                String sql = "INSERT INTO services (ServiceName, ServiceCost) VALUES (?,?,?)";
                PreparedStatement pst = db.getConnection().prepareStatement(sql);
                pst.setString(1, serviceName);
                pst.setDouble(2, serviceCost);
                pst.setString(3, serviceCost.toString());

                if (pst.executeUpdate() > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("INFORMATION");
                    alert.setHeaderText(null);
                    alert.setContentText("Service Added Successfully");
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
}
