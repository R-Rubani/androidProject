package com.example.healthconnectapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

public class AppointmentDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }


    }
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