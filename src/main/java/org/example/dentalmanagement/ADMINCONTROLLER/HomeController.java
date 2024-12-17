package org.example.dentalmanagement.ADMINCONTROLLER;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.dentalmanagement.DATABASE.DATABASECONNECTIVITY;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAppointments();
        AppointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        PatientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        AppointmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        AppointmentTimeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTime"));
        ServiceColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        PaymentStatusColumn.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));

    }


    @FXML
    private TableColumn<AppointmentInfo, String> AppointmentTimeColumn;

    @FXML
    private TableColumn<AppointmentInfo, String> AppointmentDateColumn;

    @FXML
    private TableColumn<AppointmentInfo, Integer> AppointmentIDColumn;

    @FXML
    private TableColumn<AppointmentInfo, String> PatientNameColumn;

    @FXML
    private TableColumn<AppointmentInfo, String> PaymentStatusColumn;

    @FXML
    private TableColumn<AppointmentInfo, String> ServiceColumn;

    @FXML
    private TableView<AppointmentInfo> AppointmentTable;

    private ObservableList<AppointmentInfo> AppointmentList = FXCollections.observableArrayList();

    @FXML
    private Button DeleteBTN;

    @FXML
    private Button ResetBTN;

    @FXML
    private Button PaymentBTN;

    public void PaymentAppointment() throws IOException {
        AppointmentInfo SelectedAppointment = AppointmentTable.getSelectionModel().getSelectedItem();

        if (SelectedAppointment != null) {

            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/org/example/dentalmanagement/ADMIN INTERFACE/PaymentDetails.fxml"));
            Scene scene = new Scene(fxml.load());
            Stage stage = new Stage();

            PaymentController paymentMethod = fxml.getController();
            paymentMethod.setData(
                    SelectedAppointment.getAppointmentID(),
                    SelectedAppointment.getPatientName(),
                    SelectedAppointment.getService(),
                    servicecost
            );

            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Appointment Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select an appointment from the table.");
            alert.showAndWait();
        }
    }

    public void UpdateAppointments() {
       AppointmentInfo SelectedAppointment = AppointmentTable.getSelectionModel().getSelectedItem();

       if (SelectedAppointment != null) {
           FXMLLoader fxml = new FXMLLoader(getClass().getResource("/org/example/dentalmanagement/ADMIN INTERFACE/appointment.fxml"));
           AppointmentController appointmentController = fxml.getController();
           appointmentController.setHomeController(this);
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("GO TO APPOINTMENT");
           alert.setHeaderText(null);
           alert.setContentText("GO TO ADD APPOINTMENT");
           alert.showAndWait();
       }
    }

    public void DeleteAppointment() {
        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();
        AppointmentInfo SelectedAppointment = AppointmentTable.getSelectionModel().getSelectedItem();

        if (SelectedAppointment != null) {
            try {
                PreparedStatement pmt = db.getConnection().prepareStatement("DELETE FROM appointment WHERE AppointmentID = ?");
                pmt.setInt(1, SelectedAppointment.getAppointmentID());

                if (pmt.executeUpdate() > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("APPOINTMENT DELETED");
                    alert.setHeaderText(null);
                    alert.setContentText("APPOINTMENT DELETED");
                    alert.showAndWait();

                    AppointmentList.clear();
                    loadAppointments();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void ResetAppointments() {
        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();

        try {
            Statement stmt = db.getConnection().createStatement();
            String sql = "TRUNCATE TABLE appointment";
            int rows = stmt.executeUpdate(sql);

            if (rows == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("RESET APPOINTMENT");
                alert.setHeaderText(null);
                alert.setContentText("RESET APPOINTMENT");
                alert.showAndWait();

                AppointmentList.clear();
                loadAppointments();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private int PatientID;
    private int appointmentID;
    private String patientName;
    private String appointmentDate;
    private String appointmentTime;
    private String service;
    private Double servicecost;

    public void loadAppointments() {
        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();
        Singleton instance = Singleton.getInstance();

        try {
            Connection conn = db.getConnection();

            String query = "SELECT a.AppointmentID, p.FullName, a.AppointmentDate, a.AppointmentTime, " +
                    "s.ServiceName, s.ServiceCost " +
                    "FROM appointment a " +
                    "JOIN patient p ON a.PatientID = p.PatientID " +
                    "JOIN services s ON a.ServiceID = s.ServiceID";

            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int appointmentID = rs.getInt("AppointmentID");
                String patientName = rs.getString("FullName");
                String appointmentDate = rs.getString("AppointmentDate");
                String appointmentTime = rs.getString("AppointmentTime");
                String service = rs.getString("ServiceName");
                servicecost = rs.getDouble("ServiceCost");

                String paymentStatus = "Pending";

                AppointmentList.add(new AppointmentInfo(appointmentID, patientName, appointmentDate,
                        appointmentTime, service, paymentStatus));

                instance.setServiceCosts(servicecost);
                System.out.println("Service Cost for " + service + ": " + servicecost);
            }

            AppointmentTable.setItems(AppointmentList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
