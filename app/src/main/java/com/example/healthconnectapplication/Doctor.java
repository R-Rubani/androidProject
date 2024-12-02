package com.example.healthconnectapplication;

public class Doctor {
    private String name;
    private String email;
    private String contactNumber;
    private String dob;

    public Doctor(String name, String email, String contactNumber, String dob) {
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getDob() {
        return dob;
    }
}
