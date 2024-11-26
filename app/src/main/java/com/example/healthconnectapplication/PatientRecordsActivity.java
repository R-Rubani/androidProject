package com.example.healthconnectapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class PatientRecordsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_records);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }

    }
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