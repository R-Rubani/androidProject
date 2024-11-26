package com.example.healthconnectapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button buttonAsDoctor;
    private Button buttonAsAdmin;
    private Button buttonAsPatient;

    // SharedPreferences Constants
    public static final String PREFS_NAME = "UserPrefs";
    public static final String USER_TYPE_KEY = "userType";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }

        // Initialize buttons
        buttonAsDoctor = findViewById(R.id.buttonAsDoctor);
        buttonAsAdmin = findViewById(R.id.buttonAsAdmin);
        buttonAsPatient = findViewById(R.id.buttonAsPatient);

        // Set up click listeners for the buttons to store the user type
        buttonAsDoctor.setOnClickListener(v -> {
            storeUserType("doctor"); // Store as doctor
            // Optionally, navigate to the Doctor's activity
            // startActivity(new Intent(MainActivity.this, DoctorActivity.class));
        });

        buttonAsDoctor.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Show Admin button on long press
                buttonAsAdmin.setVisibility(View.VISIBLE);
                return true; // Indicate that the long press is handled
            }
        });

        buttonAsPatient.setOnClickListener(v -> {
            storeUserType("patient"); // Store as patient
            // Optionally, navigate to the Patient's activity
            // startActivity(new Intent(MainActivity.this, PatientActivity.class));
        });

        buttonAsAdmin.setOnClickListener(v -> {
            storeUserType("admin"); // Store as admin
            // Optionally, navigate to the Admin's activity
            // startActivity(new Intent(MainActivity.this, AdminActivity.class));
        });
    }

    /**
     * Store the user type (doctor, patient, or admin) in SharedPreferences.
     */
    private void storeUserType(String userType) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_TYPE_KEY, userType);
        editor.apply(); // Save the preference
    }
}
