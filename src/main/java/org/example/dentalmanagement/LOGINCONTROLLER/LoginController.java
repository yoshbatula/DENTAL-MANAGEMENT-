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

    private TextField adminTF;

    @FXML
    private Button loginADMIN;

    @FXML
    private PasswordField passwordTF;


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
        }
    }

    public void login() {
        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();
        String username = adminTF.getText();
        String password = passwordTF.getText();

        try {
            Statement stmt = db.getConnection().createStatement();
            String sql = "SELECT * FROM WHERE Username = ? AND Password = ?";
            PreparedStatement pstmt = db.getConnection().prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet result = pstmt.executeQuery();

            if (!result.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Username or Password is incorrect.");
                alert.show();
            } else {
                FXMLLoader fxmlLoader = new FXMLLoader();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
