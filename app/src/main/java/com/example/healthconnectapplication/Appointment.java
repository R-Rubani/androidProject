package com.example.healthconnectapplication;

import android.os.Parcel;
import android.os.Parcelable;

public class Appointment implements Parcelable {
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

    // Setter methods
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    // toString() method for debugging and logging purposes
    @Override
    public String toString() {
        return "Appointment ID: " + appointmentId + "\n" +
                "Patient: " + firstName + " " + lastName + "\n" +
                "Doctor: " + firstName + " " + lastName + "\n" +
                "Date: " + appointmentDate + "\n" +
                "Diagnosis: " + diagnosis + "\n" +
                "Treatment: " + treatment + "\n" +
                "Medication: " + medication;
    }

    // Parcelable implementation
    protected Appointment(Parcel in) {
        appointmentId = in.readInt();
        patientId = in.readString();
        patientEmail = in.readString();
        doctorId = in.readString();
        doctorEmail = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        appointmentDate = in.readString();
        diagnosis = in.readString();
        treatment = in.readString();
        medication = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(appointmentId);
        dest.writeString(patientId);
        dest.writeString(patientEmail);
        dest.writeString(doctorId);
        dest.writeString(doctorEmail);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(appointmentDate);
        dest.writeString(diagnosis);
        dest.writeString(treatment);
        dest.writeString(medication);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Appointment> CREATOR = new Creator<Appointment>() {
        @Override
        public Appointment createFromParcel(Parcel in) {
            return new Appointment(in);
        }

        @Override
        public Appointment[] newArray(int size) {
            return new Appointment[size];
        }
    };
}
