package org.example.dentalmanagement.ADMINCONTROLLER;

public class AppointmentInfo {

    private int appointmentID;
    private String patientName;
    private String appointmentDate;
    private String appointmentTime;
    private String service;
    private String paymentStatus;

    public AppointmentInfo(int appointmentID, String patientName, String appointmentDate, String appointmentTime, String service, String paymentStatus) {
        this.appointmentID = appointmentID;
        this.patientName = patientName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.service = service;
        this.paymentStatus = paymentStatus;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public String getService() {
        return service;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }
}
