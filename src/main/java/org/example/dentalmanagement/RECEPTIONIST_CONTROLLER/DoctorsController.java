package org.example.dentalmanagement.RECEPTIONIST_CONTROLLER;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.dentalmanagement.DATABASE.DATABASECONNECTIVITY;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DoctorsController {

    @FXML
    private TableColumn<DoctorsInfoData, String> Contact;

    @FXML
    private TableColumn<DoctorsInfoData, Integer> DoctorID;

    @FXML
    private TableColumn<DoctorsInfoData, String> FullName;

    @FXML
    private TableColumn<DoctorsInfoData, String> Specialization;

    @FXML
    private TableView<DoctorsInfoData> TableDoctor;

    private ObservableList<DoctorsInfoData> doctorList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        if (TableDoctor == null) {
            System.out.println("DoctorTable is null!");
            return;
        }

        loadDoctorsFromDatabase();
        DoctorID.setCellValueFactory(new PropertyValueFactory<>("doctorID"));
        FullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        Specialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        Contact.setCellValueFactory(new PropertyValueFactory<>("contactInfo"));

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

            TableDoctor.setItems(doctorList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
