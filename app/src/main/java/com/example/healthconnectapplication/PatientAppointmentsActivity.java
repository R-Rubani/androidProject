package com.example.healthconnectapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class PatientAppointmentsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPatientAppointments;
    private AppointmentDateAdapter appointmentDateAdapter;
    private List<AppointmentDate> appointmentDateList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_appointments);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            setContentView(R.layout.activity_patient_appointments);

            recyclerViewPatientAppointments = findViewById(R.id.recyclerViewPatientAppointments);
            recyclerViewPatientAppointments.setLayoutManager(new LinearLayoutManager(this));

            appointmentDateList = new ArrayList<>();

            // Retrieve email from SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            String email = sharedPreferences.getString("user_email", null);

            if (email != null) {
                fetchAppointments(email);
            } else {
                Toast.makeText(this, "User email not found!", Toast.LENGTH_SHORT).show();
            }

            // Set up the RecyclerView adapter
            appointmentDateAdapter = new AppointmentDateAdapter(appointmentDateList);
            recyclerViewPatientAppointments.setAdapter(appointmentDateAdapter);
        }

    }

    private void fetchAppointments(String email) {
        AppointmentDateDatabaseHelper dbHelper = new AppointmentDateDatabaseHelper(this);
        List<AppointmentDate> appointments = dbHelper.getAppointmentsByEmail(email);

        if (appointments.isEmpty()) {
            Toast.makeText(this, "No appointments found for this user.", Toast.LENGTH_SHORT).show();
        } else {
            appointmentDateList.addAll(appointments);
            appointmentDateAdapter.notifyDataSetChanged(); // Update the adapter
        }
    }

    public void openPatientProfile(MenuItem item) {
        startActivity(new Intent(PatientAppointmentsActivity.this, PatientProfileActivity.class));
    }
    public void openPatientAppointments(MenuItem item) {
        startActivity(new Intent(PatientAppointmentsActivity.this, PatientAppointmentsActivity.class));
    }
    public void openMedicalHistory(MenuItem item) {
        startActivity(new Intent(PatientAppointmentsActivity.this, MedicalHistoryActivity.class));
    }
}