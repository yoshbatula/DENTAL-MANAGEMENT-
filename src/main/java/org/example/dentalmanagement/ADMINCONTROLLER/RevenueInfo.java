package org.example.dentalmanagement.ADMINCONTROLLER;

public class RevenueInfo {

    private int appointmentID;
    private String services;
    private Double serviceCost;

    RevenueInfo(int appointmentID, String services, Double serviceCost) {
        this.appointmentID = appointmentID;
        this.services = services;
        this.serviceCost = serviceCost;
    }

    public int getAppointmentID() {
        return appointmentID;
    }
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public Double getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(Double serviceCost) {
        this.serviceCost = serviceCost;
    }
}
