package org.example.dentalmanagement.ADMINCONTROLLER;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.dentalmanagement.DATABASE.DATABASECONNECTIVITY;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ServicesController implements Initializable {

    @FXML
    private Button AddServicesBTN;

    @FXML
    private TableColumn<ServicesInfo, Double> ServiceCost;

    @FXML
    private TableColumn<ServicesInfo, Integer> ServiceIDColumn;

    @FXML
    private TableColumn<ServicesInfo, String> ServiceNameColumn;

    @FXML
    private TableView<ServicesInfo> ServiceTable;

    private ObservableList<ServicesInfo> Services = FXCollections.observableArrayList();

    @FXML
    private Button DeleteBTN;

    @FXML
    private Button ResetBTN;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        LoadServices();
        ServiceIDColumn.setCellValueFactory(new PropertyValueFactory<>("servicesID"));
        ServiceNameColumn.setCellValueFactory(new PropertyValueFactory<>("servicesName"));
        ServiceCost.setCellValueFactory(new PropertyValueFactory<>("serviceCost"));
    }

    public void SwitchToAddServices(ActionEvent event) throws IOException {
        if (event.getSource() == AddServicesBTN) {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/dentalmanagement/ADMIN INTERFACE/AddServices.fxml"));
            Scene scene = new Scene(loader.load());

            AddServices addServices = loader.getController();
            addServices.setServicesController(this);

            stage.setScene(scene);
            stage.show();
        }
    }

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

    public void UpdateServices(ActionEvent event) throws IOException {
        ServicesInfo SelectedServices = ServiceTable.getSelectionModel().getSelectedItem();

        if (SelectedServices != null) {
            Stage stage = new Stage();
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/org/example/dentalmanagement/ADMIN INTERFACE/AddServices.fxml"));
            Scene scene = new Scene(fxml.load());
            AddServices addServices = fxml.getController();
            addServices.setServicesController(this);
            addServices.setServicesData(
                    SelectedServices.getServicesID(),
                    SelectedServices.getServicesName(),
                    SelectedServices.getServiceCost()
            );
            stage.setScene(scene);
            stage.show();
        }
    }

    public void DeleteService() {
        ServicesInfo selectedService = ServiceTable.getSelectionModel().getSelectedItem();
        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();

        try {
            Statement stmt = db.getConnection().createStatement();
            String sql = "DELETE FROM services WHERE ServiceID = ?";
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setInt(1, selectedService.getServicesID());


            if (ps.executeUpdate() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("DELETE");
                alert.setHeaderText(null);
                alert.setContentText("Deleted Service successfully");
                alert.showAndWait();
                LoadServices();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ResetService() {
        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();
        try {
            Statement stmt = db.getConnection().createStatement();
            String sql = "TRUNCATE TABLE services";
            int rows = stmt.executeUpdate(sql);

            if (rows == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("RESET");
                alert.setHeaderText(null);
                alert.setContentText("Reset Service Successfully");
                alert.showAndWait();
                LoadServices();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
