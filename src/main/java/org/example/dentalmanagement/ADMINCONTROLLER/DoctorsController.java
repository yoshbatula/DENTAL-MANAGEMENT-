package org.example.dentalmanagement.ADMINCONTROLLER;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.dentalmanagement.DATABASE.DATABASECONNECTIVITY;

import java.io.IOException;
import java.sql.*;

public class DoctorsController {

    @FXML
    private Button AddDoctorsBTN;

    @FXML
    private Button AddBTN;

    @FXML
    private TextField ContactInfoTF;

    @FXML
    private TextField FullNameTF;

    @FXML
    private TextField SpecTF;

    @FXML
    private TableColumn<DoctorsInfoData, Integer> DoctorIDColumn;
    @FXML
    private TableColumn<DoctorsInfoData, String> FullNameColumn;
    @FXML
    private TableColumn<DoctorsInfoData, String> SpecializationColumn;
    @FXML
    private TableColumn<DoctorsInfoData, String> ContactInfoColumn;
    @FXML
    private TableView<DoctorsInfoData> TablesDoctor;

    private ObservableList<DoctorsInfoData> doctorList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        DoctorIDColumn.setCellValueFactory(new PropertyValueFactory<>("doctorID"));
        FullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        SpecializationColumn.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        ContactInfoColumn.setCellValueFactory(new PropertyValueFactory<>("contactInfo"));

        TablesDoctor.setItems(doctorList);

        loadDoctorsFromDatabase();
    }

    public void SwitchToAddDoctor(ActionEvent event) throws IOException {
        if (event.getSource() == AddDoctorsBTN) {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/dentalmanagement/ADMIN INTERFACE/AddDoctors.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.show();
        }
    }

    public void AddDoctors(ActionEvent event) throws IOException {
        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();
        String fullname = FullNameTF.getText().trim();
        String spec = SpecTF.getText().trim();
        String contact = ContactInfoTF.getText().trim();

        if (fullname.isEmpty()) {
            FullNameTF.setStyle("-fx-border-color: red");
            return;
        }
        if (spec.isEmpty()) {
            SpecTF.setStyle("-fx-border-color: red");
            return;
        }
        if (contact.isEmpty()) {
            ContactInfoTF.setStyle("-fx-border-color: red");
            return;
        } else {
            try {
                PreparedStatement pmt = db.getConnection().prepareStatement("INSERT INTO doctor (FullName, Specialization, ContactInfo) VALUES (?, ?, ?)");
                pmt.setString(1, fullname);
                pmt.setString(2, spec);
                pmt.setString(3, contact);

                if (pmt.executeUpdate() > 0) {
                    System.out.println("Doctor added successfully.");
                    loadDoctorsFromDatabase();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private void loadDoctorsFromDatabase() {
        doctorList.clear();
        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();

        try {
            Statement stmt = db.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM doctor");

            while (rs.next()) {
                int id = rs.getInt("DoctorID");
                String fullname = rs.getString("FullName");
                String specialization = rs.getString("Specialization");
                String contact = rs.getString("ContactInfo");

                doctorList.add(new DoctorsInfoData(id, fullname, specialization, contact));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
