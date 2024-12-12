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
        PreparedStatement pst;
        Connection conn = db.getConnection();
        Statement statement;
        try {
            pst = conn.prepareStatement("SELECT * FROM doctor");
            statement = conn.createStatement();
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                DoctorsInfoData data = new DoctorsInfoData(
                        rs.getInt("DoctorID"),
                        rs.getString("Fullname"),
                        rs.getString("Specialization"),
                        rs.getString("ContactInfo")
                );

                System.out.println(data.getDoctorID());
                System.out.println(data.getFullname());
                System.out.println(data.getSpecialization());
                System.out.println(data.getContactInfo());
                list.add(data);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void ShowDoctorTable() throws SQLException {
        ObservableList<DoctorsInfoData> list = DoctorsList();

        DoctorIDColumn.setCellValueFactory(new PropertyValueFactory<DoctorsInfoData,Integer>("DoctorID"));
        FullNameColumn.setCellValueFactory(new PropertyValueFactory<DoctorsInfoData, String>("Fullname"));
        SpecializationColumn.setCellValueFactory(new PropertyValueFactory<DoctorsInfoData, String>("Specialization"));
        ContactInfoColumn.setCellValueFactory(new PropertyValueFactory<DoctorsInfoData, String>("ContactInfo"));

        TablesDoctor.setItems(list);
    }
}
