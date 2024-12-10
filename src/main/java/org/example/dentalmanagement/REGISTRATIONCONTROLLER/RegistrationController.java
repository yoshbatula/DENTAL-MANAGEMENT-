package org.example.dentalmanagement.REGISTRATIONCONTROLLER;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.dentalmanagement.DATABASE.DATABASECONNECTIVITY;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class RegistrationController {


    @FXML
    private AnchorPane containerReg;

    @FXML
    private TextField RegContactInfoTF;

    @FXML
    private TextField RegFullNameTF;

    @FXML
    private TextField RegPasswordTF;

    @FXML
    private Button RegSubmit;

    @FXML
    private PasswordField RegPasswordPT;

    @FXML
    private TextField RecepContactInfoTF;

    @FXML
    private PasswordField RecepPasswordTF;

    @FXML
    private Button RecepSubmitBTN;

    @FXML
    private TextField RecepUsernameTF;


    public void RegistrationFormAdmin(ActionEvent event) {
        String username = RegFullNameTF.getText();
        String password = RegPasswordPT.getText();
        String contactinfo = RegContactInfoTF.getText();

        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();

        if (RegFullNameTF.getText().isEmpty()) {
            RegFullNameTF.setStyle("-fx-border-color: red");
        } if (RegPasswordPT.getText().isEmpty()) {
            RegPasswordPT.setStyle("-fx-border-color: red");
        } if (RegContactInfoTF.getText().isEmpty()) {
            RegContactInfoTF.setStyle("-fx-border-color: red");
        } else if (event.getSource() == RegSubmit) {
            try {
                Statement stmt = db.getConnection().createStatement();
                String sql = "INSERT INTO admin (Username,ContactInfo,Password) VALUES (?,?,?)";
                PreparedStatement pmt = db.getConnection().prepareStatement(sql);
                pmt.setString(1, username);
                pmt.setString(2, contactinfo);
                pmt.setString(3, password);

                int rows = pmt.executeUpdate();

                if (rows > 0) {
                    System.out.println("Succesfully Inserted");
                } else {
                    System.out.println("FAILED");
                }

                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/dentalmanagement/LOGIN INTERFACE/ADMIN.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
                stage.show();

                Stage window = (Stage) RegSubmit.getScene().getWindow();
                window.close();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void RegistrationFormReceptionist(ActionEvent event) {
        String username = RecepUsernameTF.getText();
        String contact = RecepContactInfoTF.getText();
        String password = RecepPasswordTF.getText();

        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();

        if (RecepUsernameTF.getText().isBlank()) {
            RecepUsernameTF.setStyle("-fx-border-color: red");
        } else if (RecepContactInfoTF.getText().isBlank()) {
            RecepContactInfoTF.setStyle("-fx-border-color: red");
        } else if (RecepPasswordTF.getText().isBlank()) {
            RecepPasswordTF.setStyle("-fx-border-color: red");
        } else {
            try {
              Statement stmt = db.getConnection().createStatement();
              String sql = "INSERT INTO receptionist (Username,ContactInfo,Password) VALUES (?,?,?)";
              PreparedStatement pmt = db.getConnection().prepareStatement(sql);
              pmt.setString(1, username);
              pmt.setString(2, contact);
              pmt.setString(3, password);

              int rows = pmt.executeUpdate();

              if (rows > 0) {
                  System.out.println("Succesfully Inserted");
              } else {
                  System.out.println("FAILED");
              }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
