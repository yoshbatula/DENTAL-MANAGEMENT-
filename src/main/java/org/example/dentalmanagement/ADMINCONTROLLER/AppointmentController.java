package org.example.dentalmanagement.ADMINCONTROLLER;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.dentalmanagement.DATABASE.DATABASECONNECTIVITY;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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

    public void Patient() {
        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();

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
                Statement stmt = db.getConnection().createStatement();
                String sql = "INSERT INTO patient (Fullname, DateOfBirth, Gender, ContactInfo, Address) VALUES (?,?,?,?,?)";
                PreparedStatement pstmt = db.getConnection().prepareStatement(sql);
                pstmt.setString(1, FullNameTF.getText());
                pstmt.setDate(2, Date.valueOf(DateOfBirthTF.getText()));
                pstmt.setString(3, GenderTF.getText());
                pstmt.setString(4, ContactNumberTF.getText());
                pstmt.setString(5, AddressTF.getText());

                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                    System.out.println("Succesfully inserted");
                } else {
                    System.out.println("Failed");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
