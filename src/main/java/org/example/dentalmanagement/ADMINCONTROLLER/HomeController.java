package org.example.dentalmanagement.ADMINCONTROLLER;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.dentalmanagement.DATABASE.DATABASECONNECTIVITY;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        PatientNameColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        AppointmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
        AppointmentTimeColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentTime"));
        ServiceColumn.setCellValueFactory(new PropertyValueFactory<>("service"));
        PaymentStatusColumn.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));

        loadAppointments();
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


    public void loadAppointments() {
        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();

        try {
            Connection conn = db.getConnection();
            String query = "SELECT a.AppointmentID, p.FullName, a.AppointmentDate, a.AppointmentTime, s.ServiceName " +
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
                String paymentStatus = "Pending";


                AppointmentList.add(new AppointmentInfo(appointmentID, patientName, appointmentDate, appointmentTime, service, paymentStatus));
            }

            AppointmentTable.setItems(AppointmentList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
