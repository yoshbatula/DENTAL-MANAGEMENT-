package org.example.dentalmanagement.LOGINCONTROLLER;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private ComboBox<String> OtherAcc;

    @FXML
    private StackPane ContainerLogin;

    @FXML
    private AnchorPane AnchorLogin;

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


}
