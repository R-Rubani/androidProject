package com.example.healthconnectapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

public class PatientRecordsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPatientRecords;
    private AppointmentAdapter appointmentAdapter; // Create an adapter for the RecyclerView
    private AppointmentDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_records);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }

        ImageButton addNewRecord = findViewById(R.id.imageButtonAddNewRecord);
        addNewRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientRecordsActivity.this,OldOrNewPatientActivity.class));
            }
        });

        recyclerViewPatientRecords = findViewById(R.id.recyclerViewPatientRecords);
        recyclerViewPatientRecords.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new AppointmentDatabaseHelper(this);

        // Fetch shared preferences to determine who logged in
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
        String userType = sharedPreferences.getString("userType", null); // "doctor" or "patient"
        String userEmail = sharedPreferences.getString("userEmail", null); // Email used for login

        if (userEmail==null) {
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
            recyclerViewPatientRecords.setAdapter(appointmentAdapter);
        }
    }

    // Fetch appointments for a patient using their email
    private void fetchPatientAppointments(String email) {
        List<Appointment> appointments = dbHelper.getAppointmentsByPatientEmail(email);

        if (appointments.isEmpty()) {
            Toast.makeText(this, "No appointments found for the logged-in patient.", Toast.LENGTH_SHORT).show();
        } else {
            appointmentAdapter = new AppointmentAdapter(this, appointments);
            recyclerViewPatientRecords.setAdapter(appointmentAdapter);
        }
    }

    //bottom Navigation methods
    public void openDocProfile(MenuItem item) {
        startActivity(new Intent(PatientRecordsActivity.this, DoctorProfileActivity.class));
    }
    public void openAppointments(MenuItem item) {
        startActivity(new Intent(PatientRecordsActivity.this, AppointmentDetailsActivity.class));
    }
    public void openPatientRecords(MenuItem item) {
        startActivity(new Intent(PatientRecordsActivity.this, PatientRecordsActivity.class));
    }
}