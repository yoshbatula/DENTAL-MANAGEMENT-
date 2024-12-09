package org.example.dentalmanagement.LOGINCONTROLLER;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private ComboBox<String> OtherAcc;

    @FXML
    private StackPane ContainerLogin;

    private ObservableList<String> Other = FXCollections.observableArrayList("ADMIN", "RECEPTIONIST");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OtherAcc.setItems(Other);
    }

    public void SwitchForm() throws IOException {
        if (OtherAcc.getSelectionModel().getSelectedItem().equals("ADMIN")) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/org/example/dentalmanagement/LOGIN INTERFACE/ADMIN.fxml"));
            Parent root = fxmlLoader.load();
            ContainerLogin.getChildren().add(root);
        } else if (OtherAcc.getSelectionModel().getSelectedItem().equals("RECEPTIONIST")) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/org/example/dentalmanagement/LOGIN INTERFACE/RECEPTIONIST.fxml"));
            Parent root = fxmlLoader.load();
            ContainerLogin.getChildren().add(root);
        }
    }

}
