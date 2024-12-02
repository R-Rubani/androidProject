package com.example.healthconnectapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PatientProfileActivity extends AppCompatActivity {

    private FirebaseAuthUtils mAuthUtils;
    private UserRegistrationDatabaseHelper dbHelper;

    private TextView textViewPatientName, textViewEmailAddress, textViewContactNumber, textViewDOB;
    private Button buttonLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        mAuthUtils = new FirebaseAuthUtils();
        dbHelper = new UserRegistrationDatabaseHelper(this);

        // Initialize UI components
        textViewPatientName = findViewById(R.id.textViewPatientName);
        textViewEmailAddress = findViewById(R.id.textViewEmailAddress);
        textViewContactNumber = findViewById(R.id.textViewContactNumber);
        textViewDOB = findViewById(R.id.textViewDOB);
        buttonLogOut = findViewById(R.id.buttonLogOut);

        loadPatientDetails();

        buttonLogOut.setOnClickListener(view -> {
            mAuthUtils.signOut();
            Toast.makeText(this, "Logged out successfully.", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity
        });

        // Set up ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }
    }

    private void loadPatientDetails() {
        String userEmail = mAuthUtils.getLoggedInUserEmail();
        if (userEmail != null) {
            Cursor cursor = dbHelper.getUserDetailsByEmail(userEmail);
            if (cursor != null) {
                // Get User object from cursor
                User user = dbHelper.getUserFromCursor(cursor);
                if (user != null) {
                    // Set the data to UI elements
                    textViewPatientName.setText(user.getFullName());
                    textViewEmailAddress.setText(userEmail);
                    textViewContactNumber.setText(user.getPhone());
                    textViewDOB.setText(user.getDateOfBirth());
                } else {
                    Toast.makeText(this, "Patient details not found.", Toast.LENGTH_SHORT).show();
                }
                // Close the cursor after use
                dbHelper.closeCursor(cursor);
            }
        } else {
            Toast.makeText(this, "User not logged in.", Toast.LENGTH_SHORT).show();
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
