package com.example.healthconnectapplication;

public class User {
    private String firstName;
    private String lastName;
    private String phone;
    private String dateOfBirth;

    public User(String firstName, String lastName, String phone, String dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }
}
