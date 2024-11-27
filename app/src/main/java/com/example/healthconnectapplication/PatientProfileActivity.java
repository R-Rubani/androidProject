package com.example.healthconnectapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class PatientProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }

    }
    public void openPatientProfile(MenuItem item) {
        startActivity(new Intent(PatientProfileActivity.this, PatientProfileActivity.class));
    }
    public void openPatientAppointments(MenuItem item) {
        startActivity(new Intent(PatientProfileActivity.this, PatientAppointmentsActivity.class));
    }
    public void openMedicalHistory(MenuItem item) {
        startActivity(new Intent(PatientProfileActivity.this, MedicalHistoryActivity.class));
    }
}