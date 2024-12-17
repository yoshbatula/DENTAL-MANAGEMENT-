package org.example.dentalmanagement.ADMINCONTROLLER;

public class AppointmentInfo {

    private int appointmentID;
    private String patientName;
    private String appointmentDate;
    private String appointmentTime;
    private String service;
    private String paymentStatus;
    private Double serviceCost;

    public AppointmentInfo(int appointmentID, String patientName, String appointmentDate, String appointmentTime, String service, String paymentStatus, Double serviceCost) {
        this.appointmentID = appointmentID;
        this.patientName = patientName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.service = service;
        this.paymentStatus = paymentStatus;
        this.serviceCost = serviceCost;
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

    public Double getServiceCost() {
        return serviceCost;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }
}
