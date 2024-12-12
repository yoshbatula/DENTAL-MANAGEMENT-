package org.example.dentalmanagement.ADMINCONTROLLER;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.dentalmanagement.DATABASE.DATABASECONNECTIVITY;

import java.io.IOException;
import java.sql.*;

public class DoctorsController {

    @FXML
    private Button AddDoctorsBTN;

    @FXML
    private TableColumn<DoctorsInfoData, String> ContactInfoColumn;

    @FXML
    private TableColumn<DoctorsInfoData, Integer> DoctorIDColumn;

    @FXML
    private TableView<DoctorsInfoData> DoctorTable;

    @FXML
    private TableColumn<DoctorsInfoData, String> FullNameColumn;

    @FXML
    private TableColumn<DoctorsInfoData, String> SpecializationColumn;

    private ObservableList<DoctorsInfoData> doctorList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        if (DoctorTable == null) {
            System.out.println("DoctorTable is null!");
            return;
        }

        loadDoctorsFromDatabase();
        DoctorIDColumn.setCellValueFactory(new PropertyValueFactory<>("doctorID"));
        FullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        SpecializationColumn.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        ContactInfoColumn.setCellValueFactory(new PropertyValueFactory<>("contactInfo"));

    }

    void loadDoctorsFromDatabase() {
        doctorList.clear();
        try {
            DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();
            Connection connection = db.getConnection();
            String query = "SELECT * FROM doctor";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("DoctorID");
                String fullName = rs.getString("FullName");
                String specialization = rs.getString("Specialization");
                String contactInfo = rs.getString("ContactInfo");

                doctorList.add(new DoctorsInfoData(id, fullName, specialization, contactInfo));
            }

            DoctorTable.setItems(doctorList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void SwitchToAddDoctor(ActionEvent event) throws IOException {
        if (event.getSource() == AddDoctorsBTN) {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/dentalmanagement/ADMIN INTERFACE/AddDoctors.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            AddDoctorController addDoctorController = fxmlLoader.getController();
            addDoctorController.setDoctorsController(this);

            stage.setScene(scene);
            stage.show();
        }
    }

}
