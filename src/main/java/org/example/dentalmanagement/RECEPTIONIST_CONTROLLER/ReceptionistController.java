package org.example.dentalmanagement.RECEPTIONIST_CONTROLLER;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReceptionistController implements Initializable {

    @FXML
    private Button RAppointmentBTN;

    @FXML
    private Button RDoctorBTN;

    @FXML
    private Button RHomeBTN;

    @FXML
    private Button RRevenueBTN;

    @FXML
    private Button RServiceBTN;

    @FXML
    private StackPane container;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            switchToView("home");
        } catch (RuntimeException e) {
            e.printStackTrace();
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
                    pane = loadFXML("/org/example/dentalmanagement/RECEPTIONIST INTERFACE/home.fxml");
                    break;
                case "appointment":
                    pane = loadFXML("/org/example/dentalmanagement/RECEPTIONIST INTERFACE/appointment.fxml");
                    break;
                case "doctors":
                    pane = loadFXML("/org/example/dentalmanagement/RECEPTIONIST INTERFACE/doctors.fxml");
                    break;
                case "services":
                    pane = loadFXML("/org/example/dentalmanagement/RECEPTIONIST INTERFACE/services.fxml");
                    break;
                case "revenue":
                    pane = loadFXML("/org/example/dentalmanagement/RECEPTIONIST INTERFACE/revenue.fxml");
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

    @FXML
    public void RSwitchform(ActionEvent event) {
        if (event.getSource() == RHomeBTN) {
            switchToView("home");
        } else if (event.getSource() == RAppointmentBTN) {
            switchToView("appointment");
        } else if (event.getSource() == RDoctorBTN) {
            switchToView("doctors");
        } else if (event.getSource() == RServiceBTN) {
            switchToView("services");
        } else if (event.getSource() == RRevenueBTN) {
            switchToView("revenue");
        }
    }
}
