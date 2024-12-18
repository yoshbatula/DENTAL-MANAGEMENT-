package org.example.dentalmanagement.ADMINCONTROLLER;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.dentalmanagement.DATABASE.DATABASECONNECTIVITY;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class RevenueController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTable();
    }

    @FXML
    private TableColumn<RevenueInfo, Integer> AppointmentsIDColumn;

    @FXML
    private TableView<RevenueInfo> RevenueTable;

    @FXML
    private TableColumn<RevenueInfo, Double> ServiceCostColumn;

    @FXML
    private TableColumn<RevenueInfo, String> ServicesColumn;

    @FXML
    private Label TotalLabel;

    private ObservableList<RevenueInfo> RevenueList = FXCollections.observableArrayList();

    private double totalRevenue = 0.0;

    private void initializeTable() {
        AppointmentsIDColumn.setCellValueFactory(new PropertyValueFactory<RevenueInfo,Integer>("appointmentID"));
        ServicesColumn.setCellValueFactory(new PropertyValueFactory<RevenueInfo,String>("service"));
        ServiceCostColumn.setCellValueFactory(new PropertyValueFactory<RevenueInfo,Double>("serviceCost"));
        loadRevenueData();
    }

    private void loadRevenueData() {
        RevenueList.clear();
        totalRevenue = 0.0;

        String query = "SELECT AppointmentID, ServiceName, ServiceCost FROM revenue";

        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int appointmentID = rs.getInt("AppointmentID");
                String serviceName = rs.getString("ServiceName");
                double serviceCost = rs.getDouble("ServiceCost");

                RevenueList.add(new RevenueInfo(appointmentID, serviceName, serviceCost));
                totalRevenue += serviceCost;
            }

            RevenueTable.setItems(RevenueList);
            TotalLabel.setText("Total Revenue: ₱" + totalRevenue);

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
