package org.example.dentalmanagement.RECEPTIONIST_CONTROLLER;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.dentalmanagement.DATABASE.DATABASECONNECTIVITY;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ServicesController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoadServices();
        ServiceIDColumn.setCellValueFactory(new PropertyValueFactory<>("servicesID"));
        ServinceNameColumn.setCellValueFactory(new PropertyValueFactory<>("servicesName"));
        ServiceCost.setCellValueFactory(new PropertyValueFactory<>("serviceCost"));
    }

    @FXML
    private TableColumn<ServicesInfo, Double> ServiceCost;

    @FXML
    private TableColumn<ServicesInfo, Integer> ServiceIDColumn;

    @FXML
    private TableView<ServicesInfo> ServiceTable;

    @FXML
    private TableColumn<ServicesInfo, String> ServinceNameColumn;

    private ObservableList<ServicesInfo> Services = FXCollections.observableArrayList();

    public void LoadServices() {
        Services.clear();
        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();
        try {
            Connection con = db.getConnection();
            String sql = "SELECT * FROM services";
            Statement stmt = con.createStatement();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int ServiceID = rs.getInt("ServiceID");
                String ServiceName = rs.getString("ServiceName");
                double ServiceCost = rs.getDouble("ServiceCost");

                Services.add(new ServicesInfo(ServiceID, ServiceName, ServiceCost));
            }

            ServiceTable.setItems(Services);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}