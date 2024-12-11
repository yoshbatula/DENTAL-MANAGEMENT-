package org.example.dentalmanagement.ADMINCONTROLLER;

public class DoctorsInfoData {

    private String DoctorName;
    private String Specialization;
    private String ContactInfo;

    public DoctorsInfoData(String doctorName, String specialization, String contactInfo) {
        DoctorName = doctorName;
        Specialization = specialization;
        ContactInfo = contactInfo;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        DoctorName = doctorName;
    }

    public String getSpecialization() {
        return Specialization;
    }

    public void setSpecialization(String specialization) {
        Specialization = specialization;
    }

    public String getContactInfo() {
        return ContactInfo;
    }

    public void setContactInfo(String contactInfo) {
        ContactInfo = contactInfo;
    }
}
