package com.example.healthconnectapplication;

public class Appointment {
    private int appointmentId;
    private String patientId;
    private String patientEmail;
    private String doctorId;
    private String doctorEmail;
    private String firstName;
    private String lastName;
    private String appointmentDate;
    private String diagnosis;
    private String treatment;
    private String medication;

    // Constructor
    public Appointment(int appointmentId, String patientId, String patientEmail, String doctorId,
                       String doctorEmail, String firstName, String lastName,
                       String appointmentDate, String diagnosis, String treatment, String medication) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.patientEmail = patientEmail;
        this.doctorId = doctorId;
        this.doctorEmail = doctorEmail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.appointmentDate = appointmentDate;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.medication = medication;
    }

    // Getter methods
    public int getAppointmentId() {
        return appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getDoctorEmail() {
        return doctorEmail;
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

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public String getMedication() {
        return medication;
    }
}
