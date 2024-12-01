package com.example.healthconnectapplication;

public class Appointment {

    private int id;
    private String patientName;
    private String appointmentDate;
    private String diagnosis;
    private String treatment;

    public Appointment(int id, String patientName, String appointmentDate, String diagnosis, String treatment) {
        this.id = id;
        this.patientName = patientName;
        this.appointmentDate = appointmentDate;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
    }

    public int getId() { return id; }
    public String getPatientName() { return patientName; }
    public String getAppointmentDate() { return appointmentDate; }
    public String getDiagnosis() { return diagnosis; }
    public String getTreatment() { return treatment; }
}

