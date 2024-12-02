package com.example.healthconnectapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DoctorProfileActivity extends AppCompatActivity {

    private FirebaseAuthUtils authUtils;
    private DoctorRegistrationDatabaseHelper doctorDbHelper;

    private TextView textViewDoctorName, textViewEmailAddress, textViewContactNumber, textViewDOB;
    private Button buttonLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);

        // Initialize Firebase Auth Utils and database helper
        authUtils = new FirebaseAuthUtils();
        doctorDbHelper = new DoctorRegistrationDatabaseHelper(this);

        // Initialize UI components
        textViewDoctorName = findViewById(R.id.textViewDoctorName);
        textViewEmailAddress = findViewById(R.id.textViewEmailAddress);
        textViewContactNumber = findViewById(R.id.textViewContactNumber);
        textViewDOB = findViewById(R.id.textViewDOB);
        buttonLogOut = findViewById(R.id.buttonLogOut);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }

        // Load user details
        loadDoctorDetails();

        // Handle logout button click
        buttonLogOut.setOnClickListener(view -> {
            authUtils.signOut();
            Toast.makeText(this, "Logged out successfully.", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity
        });
    }

    private void loadDoctorDetails() {
        String userEmail = authUtils.getLoggedInUserEmail();
        if (userEmail != null) {
            Doctor doctor = doctorDbHelper.getDoctorByEmail(userEmail);
            if (doctor != null) {
                textViewDoctorName.setText(doctor.getName());
                textViewEmailAddress.setText(doctor.getEmail());
                textViewContactNumber.setText(doctor.getContactNumber());
                textViewDOB.setText(doctor.getDob());
            } else {
                Toast.makeText(this, "Doctor details not found.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "User not logged in.", Toast.LENGTH_SHORT).show();
        }
    }

    public void openDocProfile(MenuItem item) {
        // Placeholder for menu action
    }

    public void openAppointments(MenuItem item) {
        // Navigate to appointment details
        startActivity(new Intent(DoctorProfileActivity.this, AppointmentDetailsActivity.class));
    }

    public void openPatientRecords(MenuItem item) {
        // Navigate to patient records
        startActivity(new Intent(DoctorProfileActivity.this, PatientRecordsActivity.class));
    }
}
