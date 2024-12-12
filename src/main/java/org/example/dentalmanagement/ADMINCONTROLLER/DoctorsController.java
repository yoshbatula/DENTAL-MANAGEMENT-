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
    private TableColumn<DoctorsInfoData, String> ContactInfoColumn;

    @FXML
    private TableColumn<DoctorsInfoData, Integer> DoctorIDColumn;

    @FXML
    private TableColumn<DoctorsInfoData, String> FullNameColumn;

    @FXML
    private TableColumn<DoctorsInfoData, String> SpecializationColumn;

    @FXML
    private TableView<DoctorsInfoData> TablesDoctor;

    private ObservableList<DoctorsInfoData> doctorData;

    @FXML
    public void initialize() {

        assert DoctorIDColumn != null : "fx:id 'DoctorIDColumn' was not injected: check your FXML file.";
        assert FullNameColumn != null : "fx:id 'FullNameColumn' was not injected: check your FXML file.";
        assert SpecializationColumn != null : "fx:id 'SpecializationColumn' was not injected: check your FXML file.";
        assert ContactInfoColumn != null : "fx:id 'ContactInfoColumn' was not injected: check your FXML file.";
        assert TablesDoctor != null : "fx:id 'TablesDoctor' was not injected: check your FXML file.";

        doctorData = FXCollections.observableArrayList();

        DoctorIDColumn.setCellValueFactory(new PropertyValueFactory<>("doctorID"));
        FullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        SpecializationColumn.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        ContactInfoColumn.setCellValueFactory(new PropertyValueFactory<>("contactInfo"));

        loadDoctorsData();
    }

    public void loadDoctorsData() {
        doctorData.clear();

        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();
        try (Statement stmt = db.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM doctor");

            while (rs.next()) {
                doctorData.add(new DoctorsInfoData(
                        rs.getInt("DoctorID"),
                        rs.getString("FullName"),
                        rs.getString("Specialization"),
                        rs.getString("ContactInfo")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TablesDoctor.setItems(doctorData);
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

    public void AddDoctors() {
        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();

        String fullname = FullNameTF.getText();
        String spec = SpecTF.getText();
        String contact = ContactInfoTF.getText();

        if (fullname.isBlank()) {
            FullNameTF.setStyle("-fx-border-color: red");
            return;
        }
        if (spec.isBlank()) {
            SpecTF.setStyle("-fx-border-color: red");
            return;
        }
        if (contact.isBlank()) {
            ContactInfoTF.setStyle("-fx-border-color: red");
            return;
        }

        try (PreparedStatement pmt = db.getConnection().prepareStatement(
                "INSERT INTO doctor (FullName, Specialization, ContactInfo) VALUES (?, ?, ?)")) {
            pmt.setString(1, fullname);
            pmt.setString(2, spec);
            pmt.setString(3, contact);

            int rows = pmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Doctor added successfully");
                loadDoctorsData();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
