package org.example.dentalmanagement.ADMINCONTROLLER;

public class DoctorsInfoData {
    private Integer DoctorID;
    private String FullName;
    private String Specialization;
    private String ContactInfo;

    public DoctorsInfoData(Integer DoctorID, String FullName, String Specialization, String ContactInfo) {
        this.DoctorID = DoctorID;
        this.FullName = FullName;
        this.Specialization = Specialization;
        this.ContactInfo = ContactInfo;
    }

    public Integer getDoctorID() {
        return DoctorID;
    }

    public String getFullname() {
        return FullName;
    }

    public String getSpecialization() {
        return Specialization;
    }

    public String getContactInfo() {
        return ContactInfo;
    }
}