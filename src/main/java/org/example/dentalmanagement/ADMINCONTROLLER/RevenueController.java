package org.example.dentalmanagement.ADMINCONTROLLER;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.dentalmanagement.DATABASE.DATABASECONNECTIVITY;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class RevenueController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        AppointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
//        ServiceColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
//        ServiceCost.setCellValueFactory(new PropertyValueFactory<>("serviceCost"));
//
//        loadRevenueTable();
    }

    @FXML
    private TableColumn<RevenueInfo, Integer> AppointmentIDColumn;

    @FXML
    private TableView<RevenueInfo> RevenueTable;

    @FXML
    private TableColumn<RevenueInfo, String> ServiceColumn;

    @FXML
    private TableColumn<RevenueInfo, Double> ServiceCost;

    @FXML
    private Label TotalLabel;

    private AppointmentInfo selectedInfo;

    public void loadRevenueTable() {
        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();
        ObservableList<RevenueInfo> appointmentList = FXCollections.observableArrayList();
        double totalRevenue = 0.0;

        try (Connection conn = db.getConnection()) {

            if (selectedInfo != null) {
                String insertSQL = "INSERT INTO revenue (AppointmentID, Service, ServiceCost) VALUES (?, ?, ?)";
                try (PreparedStatement pmt = conn.prepareStatement(insertSQL)) {
                    pmt.setInt(1, selectedInfo.getAppointmentID());
                    pmt.setString(2, selectedInfo.getService());
                    pmt.setDouble(3, selectedInfo.getServiceCost());

                    int rows = pmt.executeUpdate();
                    if (rows > 0) {
                        System.out.println("INSERTED VALUES INTO REVENUE TABLE");
                    }
                }
            }

            String query = "SELECT AppointmentID, Service, ServiceCost FROM revenue";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    int appointmentID = rs.getInt("AppointmentID");
                    String serviceName = rs.getString("Service");
                    double serviceCost = rs.getDouble("ServiceCost");

                    totalRevenue += serviceCost;

                    appointmentList.add(new RevenueInfo(appointmentID, serviceName, serviceCost));
                }
            }

            RevenueTable.setItems(appointmentList);
            TotalLabel.setText(String.format("Total Revenue: $%.2f", totalRevenue));

        } catch (SQLException e) {
            throw new RuntimeException("Error loading revenue data: " + e.getMessage(), e);
        }
    }


}
