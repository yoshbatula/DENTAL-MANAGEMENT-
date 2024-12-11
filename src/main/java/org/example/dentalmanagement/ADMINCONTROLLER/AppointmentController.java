package org.example.dentalmanagement.ADMINCONTROLLER;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.dentalmanagement.DATABASE.DATABASECONNECTIVITY;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.sql.Date.valueOf;

public class AppointmentController {

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
    private Label PatientIDLabel;

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
                        int PatientID = rs.getInt("PatientID");
                        System.out.println("Patient ID: " + PatientID);
                        instance.setPatientID(PatientID);
                        PatientIDLabel.setText(String.valueOf(PatientID));

                    }
                } else {
                    System.out.println("FAILED");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
