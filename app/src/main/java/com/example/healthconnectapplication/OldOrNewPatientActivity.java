package com.example.healthconnectapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class OldOrNewPatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_or_new_patient);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }

        Button oldPatient = findViewById(R.id.buttonAsOldPatient);
        Button newPatient = findViewById(R.id.buttonAsNewPatient);
        oldPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OldOrNewPatientActivity.this,AddNewPatientRecordActivity.class));
            }
        });

        newPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OldOrNewPatientActivity.this,RegisterActivity.class));
            }
        });

    }
    public void openDocProfile(MenuItem item) {
        startActivity(new Intent(OldOrNewPatientActivity.this, DoctorProfileActivity.class));
    }
    public void openAppointments(MenuItem item) {
        startActivity(new Intent(OldOrNewPatientActivity.this, AppointmentDetailsActivity.class));
    }
    public void openPatientRecords(MenuItem item) {
        startActivity(new Intent(OldOrNewPatientActivity.this, PatientRecordsActivity.class));
    }
}