package org.example.dentalmanagement.ADMINCONTROLLER;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ServicesController {

    @FXML
    private Button AddServicesBTN;

    public void SwitchToAddServices(ActionEvent event) throws IOException {
        if (event.getSource() == AddServicesBTN) {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/dentalmanagement/ADMIN INTERFACE/AddServices.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        }
    }

    public void AddServices() {


    }
}
