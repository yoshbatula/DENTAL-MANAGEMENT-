package org.example.dentalmanagement.ADMINCONTROLLER;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.dentalmanagement.DATABASE.DATABASECONNECTIVITY;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.io.IOException;
import java.sql.PreparedStatement;
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
    private TableColumn<DoctorsInfoData, String> DoctorID;

    @FXML
    private TableColumn<DoctorsInfoData, String> FullName;

    @FXML
    private TableColumn<?, ?> Specialization;

    @FXML
    private TableView<?> TableDoctor;


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

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
