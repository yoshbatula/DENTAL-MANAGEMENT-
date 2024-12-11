package org.example.dentalmanagement.ADMINCONTROLLER;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.dentalmanagement.DATABASE.DATABASECONNECTIVITY;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    private TableColumn<DoctorsInfoData, String> Contact;

    @FXML
    private TableColumn<DoctorsInfoData, Integer> DoctorID;

    @FXML
    private TableColumn<DoctorsInfoData, String> FullName;

    @FXML
    private TableColumn<DoctorsInfoData, String> Specialization;

    @FXML
    private TableView<DoctorsInfoData> TableDoctor;

    private int DoctorsID;

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

        if (FullNameTF.getText().isBlank()) {
            FullNameTF.setStyle("-fx-border-color: red");
        } else if (ContactInfoTF.getText().isBlank()) {
            ContactInfoTF.setStyle("-fx-border-color: red");
        } else if (SpecTF.getText().isBlank()) {
            SpecTF.setStyle("-fx-border-color: red");
        } else {
            try {
                Statement stmt = db.getConnection().createStatement();
                String sql = "INSERT INTO doctor (Fullname, Specialization, ContactInfo) VALUES (?,?,?)";
                PreparedStatement pmt = db.getConnection().prepareStatement(sql);
                pmt.setString(1, fullname);
                pmt.setString(2, spec);
                pmt.setString(3, contact);

                int rows = pmt.executeUpdate();

                if (rows > 0) {
                    System.out.println("Doctor added successfully");
                    FullNameTF.setText("");
                    SpecTF.setText("");
                    ContactInfoTF.setText("");

                    ShowDoctorTable();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ObservableList<DoctorsInfoData> DoctorsList() throws SQLException {
        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();
        ObservableList<DoctorsInfoData> list = FXCollections.observableArrayList();

        try {
            Statement stmt = db.getConnection().createStatement();
            String sql = "SELECT * FROM doctor"; // Ensure DoctorID is part of the query
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                DoctorsInfoData data = new DoctorsInfoData(
                        rs.getInt("DoctorID"), // Retrieve the auto-incremented DoctorID
                        rs.getString("Fullname"),
                        rs.getString("Specialization"),
                        rs.getString("ContactInfo")
                );
                list.add(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void ShowDoctorTable() throws SQLException {
        ObservableList<DoctorsInfoData> list = DoctorsList();

        DoctorID.setCellValueFactory(new PropertyValueFactory<>("DoctorID"));
        FullName.setCellValueFactory(new PropertyValueFactory<>("Fullname"));
        Specialization.setCellValueFactory(new PropertyValueFactory<>("Specialization"));
        Contact.setCellValueFactory(new PropertyValueFactory<>("ContactInfo"));

        TableDoctor.setItems(list);
    }
}
