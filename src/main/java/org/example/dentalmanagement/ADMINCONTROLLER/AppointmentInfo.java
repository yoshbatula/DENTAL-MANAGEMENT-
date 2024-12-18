package org.example.dentalmanagement.ADMINCONTROLLER;

public class AppointmentInfo {

    private int appointmentID;
    private String patientName;
    private String appointmentDate;
    private String appointmentTime;
    private String service;
    private String paymentStatus;
    private Double serviceCost;

    public AppointmentInfo(int appointmentID, String patientName, String appointmentDate, String appointmentTime,
                           String service, Double serviceCost, String paymentStatus) {
        this.appointmentID = appointmentID;
        this.patientName = patientName;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.service = service;
        this.serviceCost = serviceCost;
        this.paymentStatus = paymentStatus;
    }

    // Getters and Setters
    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Double getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(Double serviceCost) {
        this.serviceCost = serviceCost;
    }
}