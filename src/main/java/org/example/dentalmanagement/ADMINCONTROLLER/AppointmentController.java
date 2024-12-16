package org.example.dentalmanagement.ADMINCONTROLLER;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.dentalmanagement.DATABASE.DATABASECONNECTIVITY;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import static java.sql.Date.valueOf;

public class AppointmentController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppointmentTime.setItems(Time);
        Doctors();
        Services();
    }

    @FXML
    private TextField AddressTF;

    @FXML
    private TextField ContactNumberTF;

    @FXML
    private TextField DateOfBirthTF;

    @FXML
    private TextField FullNameTF;

    @FXML
    private TextField GenderTF;

    @FXML
    private Button ProceedBTN;

    @FXML
    private ComboBox<String> ServicesComboBox;

    @FXML
    private DatePicker AppointmentDatePicekr;

    @FXML
    private ComboBox<String> AppointmentTime;

    @FXML
    private ComboBox<String> DoctorComboBox;

    @FXML
    private Label PatientIDLabel;

    private List<String> DoctorNames;

    private List<String> ServicesNames;

    private ObservableList<String> Time = FXCollections.observableArrayList("9:00am", "10:00am", "11:00am", "12:00pm", "1:00pm", "2:00pm", "3:00pm", "4:00pm", "5:00pm", "6:00pm");

    public void Patient() {
        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();
        Singleton instance = Singleton.getInstance();
        String dobText = DateOfBirthTF.getText();
        LocalDate dob;

        if (FullNameTF.getText().isBlank()) {
            FullNameTF.setStyle("-fx-border-color: red");
        } else if (AddressTF.getText().isBlank()) {
            AddressTF.setStyle("-fx-border-color: red");
        } else if (ContactNumberTF.getText().isBlank()) {
            ContactNumberTF.setStyle("-fx-border-color: red");
        } else if (DateOfBirthTF.getText().isBlank()) {
            DateOfBirthTF.setStyle("-fx-border-color: red");
        } else if (GenderTF.getText().isBlank()) {
            GenderTF.setStyle("-fx-border-color: red");
        } else {
            try {
                dob = LocalDate.parse(dobText, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e) {
                DateOfBirthTF.setStyle("-fx-border-color: red");
                throw new IllegalArgumentException("Invalid date format. Please use yyyy-MM-dd.");
            }
            try {
                Statement stmt = db.getConnection().createStatement();
                String sql = "INSERT INTO patient (Fullname, DateOfBirth, Gender, ContactInfo, Address) VALUES (?,?,?,?,?)";
                PreparedStatement pstmt = db.getConnection().prepareStatement(sql);
                pstmt.setString(1, FullNameTF.getText());
                pstmt.setDate(2, Date.valueOf(dob));
                pstmt.setString(3, GenderTF.getText());
                pstmt.setString(4, ContactNumberTF.getText());
                pstmt.setString(5, AddressTF.getText());

                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                    System.out.println("Succesfully inserted");

                    Statement stm = db.getConnection().createStatement();
                    String SQL = "SELECT PatientID FROM patient WHERE FullName = ?";
                    PreparedStatement ptm = db.getConnection().prepareStatement(SQL);
                    ptm.setString(1, FullNameTF.getText());
                    ResultSet rs = ptm.executeQuery();

                    while (rs.next()) {
                        System.out.println(rs.getString("PatientID"));
                        PatientID = rs.getInt("PatientID");
                        System.out.println("Patient ID: " + PatientID);
                        instance.setPatientID(PatientID);
                        PatientIDLabel.setText(String.valueOf(PatientID));
                        FullNameTF.setText("");
                        AddressTF.setText("");
                        ContactNumberTF.setText("");
                        DateOfBirthTF.setText("");
                        GenderTF.setText("");
                    }
                } else {
                    System.out.println("FAILED");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private int DoctorID;
    private int ServiceID;
    private int PatientID;

    public void AddAppointment() {
        Singleton instance = Singleton.getInstance();
        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();
        String SelectedDoctors = DoctorComboBox.getSelectionModel().getSelectedItem();
        String SelectedTime = AppointmentTime.getSelectionModel().getSelectedItem();
        LocalDate SelectedDate = AppointmentDatePicekr.getValue();
        String SelectedServices = ServicesComboBox.getSelectionModel().getSelectedItem();

        if (DoctorComboBox.getItems().isEmpty()) {
            DoctorComboBox.setStyle("-fx-border-color: red");
        } if (AppointmentDatePicekr.getChronology().isIsoBased()) {
            AppointmentDatePicekr.setStyle("-fx-border-color: red");
        } if (ServicesComboBox.getItems().isEmpty()) {
            ServicesComboBox.setStyle("-fx-border-color: red");
        } if (AppointmentTime.getItems().isEmpty()) {
            AppointmentTime.setStyle("-fx-border-color: red");
        } else {
            try {
                Statement smt = db.getConnection().createStatement();
                String sql = "SELECT DoctorID FROM doctor WHERE FullName = ?";
                PreparedStatement psmt = db.getConnection().prepareStatement(sql);
                psmt.setString(1, SelectedDoctors);
                ResultSet rs = psmt.executeQuery();

                while (rs.next()) {
                    System.out.println("DoctorID");
                    DoctorID = rs.getInt("DoctorID");
                    instance.setDoctorID(DoctorID);
                }

                String SQL = "SELECT ServiceID FROM services WHERE ServiceName = ?";
                psmt = db.getConnection().prepareStatement(SQL);
                psmt.setString(1, SelectedServices);
                rs = psmt.executeQuery();

                while (rs.next()) {
                    System.out.println("ServiceID");
                    ServiceID = rs.getInt("ServiceID");
                    instance.setServiceID(ServiceID);
                }

                String insert = "INSERT INTO appointment (PatientID,DoctorID,ServiceID,AppointmentDate,AppointmentTime) VALUES (?,?,?,?,?)";
                psmt = db.getConnection().prepareStatement(insert);
                psmt.setInt(1, PatientID);
                psmt.setInt(2, DoctorID);
                psmt.setInt(3, ServiceID);
                psmt.setDate(4, Date.valueOf(SelectedDate));
                psmt.setString(5, SelectedTime);

                int rowsaffected = psmt.executeUpdate();

                if (rowsaffected > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("ADDING PATIENTS");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added");
                    alert.showAndWait();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void Doctors() {
        this.DoctorNames = new ArrayList<>();

        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();

        try {
            Connection conn = db.getConnection();
            String query = "SELECT FullName FROM doctor";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {

                this.DoctorNames.add(rs.getString("FullName"));
            }
            this.DoctorComboBox.getItems().addAll(this.DoctorNames);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Services() {
        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();
        this.ServicesNames = new ArrayList<>();

        try {
            Connection conn = db.getConnection();
            String query = "SELECT ServiceName FROM Services";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                this.ServicesNames.add(rs.getString("ServiceName"));
            }

            this.ServicesComboBox.getItems().addAll(this.ServicesNames);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
