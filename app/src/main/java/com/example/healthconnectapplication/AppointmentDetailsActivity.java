package com.example.healthconnectapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class AppointmentDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAppointmentDetails;
    private AppointmentAdapter appointmentAdapter; // Create an adapter for the RecyclerView
    private AppointmentDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

        // Set the app's logo in the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }

        recyclerViewAppointmentDetails = findViewById(R.id.recyclerViewAppointmentDetails);
        recyclerViewAppointmentDetails.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new AppointmentDatabaseHelper(this);

        // Fetch shared preferences to determine who logged in
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
        String userType = sharedPreferences.getString("userType", ""); // "doctor" or "patient"
        String userEmail = sharedPreferences.getString("email", ""); // Email used for login

        if (userEmail.isEmpty()) {
            Toast.makeText(this, "No user email found. Please log in again.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Fetch and display data based on user type
        if (userType.equals("doctor")) {
            fetchDoctorAppointments(userEmail);
        } else if (userType.equals("patient")) {
            fetchPatientAppointments(userEmail);
        } else {
            Toast.makeText(this, "Invalid user type. Please log in again.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    // Fetch appointments for a doctor using their email
    private void fetchDoctorAppointments(String email) {
        List<Appointment> appointments = dbHelper.getAppointmentsByDoctorEmail(email);

        if (appointments.isEmpty()) {
            Toast.makeText(this, "No appointments found for the logged-in doctor.", Toast.LENGTH_SHORT).show();
        } else {
            appointmentAdapter = new AppointmentAdapter(this, appointments);
            recyclerViewAppointmentDetails.setAdapter(appointmentAdapter);
        }
    }

    // Fetch appointments for a patient using their email
    private void fetchPatientAppointments(String email) {
        List<Appointment> appointments = dbHelper.getAppointmentsByPatientEmail(email);

        if (appointments.isEmpty()) {
            Toast.makeText(this, "No appointments found for the logged-in patient.", Toast.LENGTH_SHORT).show();
        } else {
            appointmentAdapter = new AppointmentAdapter(this, appointments);
            recyclerViewAppointmentDetails.setAdapter(appointmentAdapter);
        }
    }

    // Bottom navigation methods
    public void openDocProfile(MenuItem item) {
        startActivity(new Intent(AppointmentDetailsActivity.this, DoctorProfileActivity.class));
    }

    public void openAppointments(MenuItem item) {
        startActivity(new Intent(AppointmentDetailsActivity.this, AppointmentDetailsActivity.class));
    }

    public void openPatientRecords(MenuItem item) {
        startActivity(new Intent(AppointmentDetailsActivity.this, PatientRecordsActivity.class));
    }
}
