package org.example.dentalmanagement.LOGINCONTROLLER;

import com.mysql.cj.xdevapi.Result;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.dentalmanagement.DATABASE.DATABASECONNECTIVITY;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private ComboBox<String> OtherAcc;

    @FXML
    private StackPane ContainerLogin;

    @FXML
    private AnchorPane AnchorLogin;

    @FXML
    private Button CreateAdmin;

    @FXML
    private Button ReceptionistCreateAcc;

    @FXML
    private TextField adminsTF;

    @FXML
    private Button loginADMIN;

    @FXML
    private PasswordField passwordTF;

    @FXML
    private PasswordField ReceptionPasswordTF;

    @FXML
    private Button ReceptionSubmitBTN;

    @FXML
    private TextField ReceptionUsernameTF;


    private ObservableList<String> Other = FXCollections.observableArrayList("ADMIN", "RECEPTIONIST");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OtherAcc.setValue("Select");
        OtherAcc.getItems().addAll("ADMIN" , "RECEPTIONIST");

    }

    public void SwitchForm() {
        try {
            String selectedRole = OtherAcc.getSelectionModel().getSelectedItem();

            if (selectedRole == null) {
                System.out.println("No role selected.");
                return;
            }

            FXMLLoader loader = new FXMLLoader();

            if ("RECEPTIONIST".equals(selectedRole)) {
                loader.setLocation(getClass().getResource("/org/example/dentalmanagement/LOGIN INTERFACE/RECEPTIONIST.fxml"));
            } else if ("ADMIN".equals(selectedRole)) {
                loader.setLocation(getClass().getResource("/org/example/dentalmanagement/LOGIN INTERFACE/ADMIN.fxml"));
            } else {
                System.out.println("Invalid selection.");
                return;
            }

            Parent root = loader.load();

            if (AnchorLogin != null) {
                AnchorLogin.getChildren().clear();
                AnchorLogin.getChildren().add(root);


                AnchorPane.setTopAnchor(root, 0.0);
                AnchorPane.setBottomAnchor(root, 0.0);
                AnchorPane.setLeftAnchor(root, 0.0);
                AnchorPane.setRightAnchor(root, 0.0);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading FXML: " + e.getMessage());
        }
    }

    public void RegForm(ActionEvent event) throws IOException {

        if (event.getSource() == CreateAdmin) {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/dentalmanagement/REGISTRATION INTERFACE/ADMINREG.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();

            Stage window = (Stage) CreateAdmin.getScene().getWindow();
            window.close();
        } else if (event.getSource() == ReceptionistCreateAcc) {
            Stage stage = new Stage();
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/org/example/dentalmanagement/REGISTRATION INTERFACE/RECEPTIONREG.fxml"));
            Scene scene = new Scene(fxml.load());
            stage.setScene(scene);
            stage.show();

            Stage window = (Stage) ReceptionistCreateAcc.getScene().getWindow();
            window.close();
        }
    }

    public void setLoginADMIN() {
        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();

        try {
            Statement stmt = db.getConnection().createStatement();
            String sql = "SELECT * FROM admin WHERE Username = ? AND Password = ?";
            PreparedStatement pstmt = db.getConnection().prepareStatement(sql);
            pstmt.setString(1, adminsTF.getText());
            pstmt.setString(2, passwordTF.getText());
            ResultSet result = pstmt.executeQuery();

            if (!result.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Username or Password is incorrect.");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFORMATION");
                alert.setHeaderText(null);
                alert.setContentText("SUCCESFULLY LOGIN");
                alert.show();

                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/dentalmanagement/ADMIN INTERFACE/MainStructureAdmin.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                stage.setScene(scene);
                stage.show();

                Stage window = (Stage) CreateAdmin.getScene().getWindow();
                window.close();


            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void setLoginReceptionist() {
        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();

        try{
            Statement stmt = db.getConnection().createStatement();
            String sql = "SELECT * FROM receptionist WHERE Username = ? AND Password = ?";
            PreparedStatement pstmt = db.getConnection().prepareStatement(sql);
            pstmt.setString(1, ReceptionUsernameTF.getText());
            pstmt.setString(2, ReceptionPasswordTF.getText());
            ResultSet result = pstmt.executeQuery();

            if (!result.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Username or Password is incorrect.");
                alert.show();
            } else {

                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/dentalmanagement/MAINPAGE INTERFACE/MainStructureReceptionist.fxml"));
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.show();

                Stage window = (Stage) ReceptionistCreateAcc.getScene().getWindow();
                window.close();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
