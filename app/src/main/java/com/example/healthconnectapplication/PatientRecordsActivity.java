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

        recyclerViewPatientRecords = findViewById(R.id.recyclerViewPatientRecords);
        recyclerViewPatientRecords.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the database helper
        dbHelper = new AppointmentDatabaseHelper(this);

        // Fetch appointments from the database
        List<Appointment> appointments = dbHelper.getAllAppointments();

        // Display appointments in the RecyclerView
        if (appointments.isEmpty()) {
            Toast.makeText(this, "No appointments found.", Toast.LENGTH_SHORT).show();
        } else {
            AppointmentAdapter adapter = new AppointmentAdapter(appointments);
            recyclerViewPatientRecords.setAdapter(adapter);
        }
    }
    // Fetch appointments for a doctor using their email


    // Fetch appointments for a patient using their email


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