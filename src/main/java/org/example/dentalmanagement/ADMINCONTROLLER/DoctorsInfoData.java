package org.example.dentalmanagement.ADMINCONTROLLER;

import javafx.beans.property.*;

public class DoctorsInfoData {

    private int doctorID;
    private String fullName;
    private String specialization;
    private String contactInfo;

    public DoctorsInfoData(int doctorID, String fullName, String specialization, String contactInfo) {
        this.doctorID = doctorID;
        this.fullName = fullName;
        this.specialization = specialization;
        this.contactInfo = contactInfo;
    }

    public int getDoctorID() { return doctorID; }
    public String getFullName() { return fullName; }
    public String getSpecialization() { return specialization; }
    public String getContactInfo() { return contactInfo; }
}