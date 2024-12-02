package com.example.healthconnectapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DoctorProfileActivity extends AppCompatActivity {

    private FirebaseAuthUtils authUtils;
    private DoctorRegistrationDatabaseHelper doctorDbHelper;
    private AppointmentDateDatabaseHelper appointmentDateDbHelper;

    private TextView textViewDoctorName, textViewEmailAddress, textViewContactNumber, textViewDOB;
    private Button buttonLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        appointmentDateDbHelper = new AppointmentDateDatabaseHelper(this);

        // Initialize Firebase Auth Utils and database helper
        authUtils = new FirebaseAuthUtils();
        doctorDbHelper = new DoctorRegistrationDatabaseHelper(this);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = formatter.format(new Date());
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

        loadDoctorDetails();
        List<AppointmentDate> appointmentDates = appointmentDateDbHelper.getAllAppointments();
        for (AppointmentDate appointment : appointmentDates) {
            if (appointment.getAppointmentDate().equals(currentDate)) {
                // Show a toast if the appointment date matches the current date
                Toast.makeText(this, "REMINDER!!!  Please check your appointment for today.", Toast.LENGTH_LONG).show();
                break;  // Exit loop after the first match is found
            }
        }
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
