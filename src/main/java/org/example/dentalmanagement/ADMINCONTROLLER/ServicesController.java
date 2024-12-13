package org.example.dentalmanagement.ADMINCONTROLLER;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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
}
