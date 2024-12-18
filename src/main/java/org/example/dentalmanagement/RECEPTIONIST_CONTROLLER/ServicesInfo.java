package org.example.dentalmanagement.RECEPTIONIST_CONTROLLER;

public class ServicesInfo {

    private Integer ServicesID;
    private String ServicesName;
    private Double ServiceCost;

    public ServicesInfo(Integer servicesID, String servicesName, Double serviceCost) {
        ServicesID = servicesID;
        ServicesName = servicesName;
        ServiceCost = serviceCost;
    }

    public Integer getServicesID() {
        return ServicesID;
    }

    public void setServicesID(Integer servicesID) {
        ServicesID = servicesID;
    }

    public String getServicesName() {
        return ServicesName;
    }

    public void setServicesName(String servicesName) {
        ServicesName = servicesName;
    }

    public Double getServiceCost() {
        return ServiceCost;
    }
}
