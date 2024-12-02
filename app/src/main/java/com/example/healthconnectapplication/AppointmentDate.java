package com.example.healthconnectapplication;

public class AppointmentDate {

    private String patientId;
    private String firstName;
    private String lastName;
    private String appointmentDate;

    public AppointmentDate(String patientId, String firstName, String lastName, String appointmentDate) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.appointmentDate = appointmentDate;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }
}
