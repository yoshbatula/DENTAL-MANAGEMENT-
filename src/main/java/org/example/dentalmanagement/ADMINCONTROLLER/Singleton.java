package org.example.dentalmanagement.ADMINCONTROLLER;

public class Singleton {

    private static Singleton instance;
    private int PatientID;
    private int DoctorID;
    private int ServiceID;
    private Double ServiceCosts;

    public int getDoctorID() {
        return DoctorID;
    }

    public void setDoctorID(int doctorID) {
        DoctorID = doctorID;
    }

    public int getServiceID() {
        return ServiceID;
    }

    public void setServiceID(int serviceID) {
        ServiceID = serviceID;
    }

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public Double getServiceCosts() {
        return ServiceCosts;
    }

    public void setServiceCosts(Double serviceCosts) {
        ServiceCosts = serviceCosts;
    }
    public int getPatientID() {
        return PatientID;
    }

    public void setPatientID(int patientID) {
        PatientID = patientID;
    }

}
