package org.example.dentalmanagement.ADMINCONTROLLER;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private StackPane container;

    @FXML
    private Button AppointmentBTN;

    @FXML
    private Button DoctorsBTN;

    @FXML
    private Button HomeBTN;

    @FXML
    private Button RevenueBTN;

    @FXML
    private Button ServicesBTN;

    @FXML
    private Button LogoutBTN;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            switchToView("home");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void Logout() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Logout");
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {

            Stage window = (Stage) LogoutBTN.getScene().getWindow();
            window.close();

            Stage stage1 = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/dentalmanagement/LOGIN INTERFACE/ADMIN.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage1.setResizable(false);
            stage1.setTitle("Main Panel");
            stage1.setScene(scene);
            stage1.show();
        }

    }
    private StackPane loadFXML(String fxmlFileName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFileName));
        return fxmlLoader.load();
    }


    private void switchToView(String viewName) {
        StackPane pane = null;
        try {
            switch (viewName) {
                case "home":
                    pane = loadFXML("/org/example/dentalmanagement/ADMIN INTERFACE/home.fxml");
                    break;
                case "appointment":
                    pane = loadFXML("/org/example/dentalmanagement/ADMIN INTERFACE/appointment.fxml");
                    break;
                case "doctors":
                    pane = loadFXML("/org/example/dentalmanagement/ADMIN INTERFACE/doctors.fxml");
                    break;
                case "services":
                    pane = loadFXML("/org/example/dentalmanagement/ADMIN INTERFACE/services.fxml");
                    break;
                case "revenue":
                    pane = loadFXML("/org/example/dentalmanagement/ADMIN INTERFACE/revenue.fxml");
                    break;
                default:
                    throw new IllegalArgumentException("Unknown view: " + viewName);
            }

            if (pane != null) {
                container.getChildren().clear();
                container.getChildren().add(pane);

                FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), pane);
                fadeTransition.setFromValue(0.0);
                fadeTransition.setToValue(1.0);

                TranslateTransition slideTransition = new TranslateTransition(Duration.millis(500), pane);
                slideTransition.setFromX(container.getWidth());
                slideTransition.setToX(0);

                fadeTransition.play();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void switchform(ActionEvent event) {
        if (event.getSource() == HomeBTN) {
            switchToView("home");
        } else if (event.getSource() == AppointmentBTN) {
            switchToView("appointment");
        } else if (event.getSource() == DoctorsBTN) {
            switchToView("doctors");
        } else if (event.getSource() == ServicesBTN) {
            switchToView("services");
        } else if (event.getSource() == RevenueBTN) {
            switchToView("revenue");
        }
    }

}
