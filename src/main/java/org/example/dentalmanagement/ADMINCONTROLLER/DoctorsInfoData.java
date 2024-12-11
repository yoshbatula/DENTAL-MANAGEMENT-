package org.example.dentalmanagement.ADMINCONTROLLER;

public class DoctorsInfoData {
    private int DoctorID;
    private String Fullname;
    private String Specialization;
    private String ContactInfo;

    public DoctorsInfoData(Integer DoctorID, String Fullname, String Specialization, String ContactInfo) {
        this.DoctorID = DoctorID;
        this.Fullname = Fullname;
        this.Specialization = Specialization;
        this.ContactInfo = ContactInfo;
    }

    public Integer getDoctorID() {
        return DoctorID;
    }

    public String getFullname() {
        return Fullname;
    }

    public String getSpecialization() {
        return Specialization;
    }

    public String getContactInfo() {
        return ContactInfo;
    }
}