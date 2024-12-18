package org.example.dentalmanagement.ADMINCONTROLLER;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.dentalmanagement.DATABASE.DATABASECONNECTIVITY;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RevenuesController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();

    }
    private ObservableList<RevenueInfo> RevenueList = FXCollections.observableArrayList();

    private double totalRevenue = 0.0;

    @FXML
    private TableColumn<RevenueInfo, Integer> AppointmenstIDColumn;

    @FXML
    private TableView<RevenueInfo> RevenueTable;

    @FXML
    private TableColumn<RevenueInfo, Double> ServiceCost;

    @FXML
    private TableColumn<RevenueInfo, String> ServicesColumn;

    @FXML
    private Label TotalLabel;

    private void initializeTable() {
        AppointmenstIDColumn.setCellValueFactory(new PropertyValueFactory<RevenueInfo,Integer>("appointmentID"));
        ServicesColumn.setCellValueFactory(new PropertyValueFactory<RevenueInfo,String>("services"));
        ServiceCost.setCellValueFactory(new PropertyValueFactory<RevenueInfo,Double>("serviceCost"));
        loadRevenueData();
    }

    private void loadRevenueData() {
        RevenueList.clear();
        totalRevenue = 0.0;

        String query = "SELECT AppointmentID, Service, ServiceCost FROM revenue";

        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int appointmentID = rs.getInt("AppointmentID");
                String serviceName = rs.getString("Service");
                double serviceCost = rs.getDouble("ServiceCost");

                RevenueList.add(new RevenueInfo(appointmentID, serviceName, serviceCost));
                totalRevenue += serviceCost;
            }

            RevenueTable.setItems(RevenueList);
            TotalLabel.setText(String.valueOf("₱" + totalRevenue));

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Database Error");
            alert.setContentText("Failed to load revenue data: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
