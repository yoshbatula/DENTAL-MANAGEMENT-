package org.example.dentalmanagement.LAUNCHER;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LAUNCHER extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/dentalmanagement/ADMIN INTERFACE/MainStructureAdmin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("MAIN!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}