package com.example.healthconnectapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button buttonAsDoctor;
    private Button buttonAsPatient;
    private Button buttonAsAdmin; // Reference to the Admin button

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
        buttonAsPatient = findViewById(R.id.buttonAsPatient);
        buttonAsAdmin = findViewById(R.id.buttonAsAdmin); // Initialize Admin button

        buttonAsAdmin.setVisibility(View.INVISIBLE);

        buttonAsDoctor.setOnClickListener(v -> {
            storeUserType("doctor");
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

        });

        buttonAsPatient.setOnClickListener(v -> {
            storeUserType("patient");
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);

        });

        // Make Admin button visible on long press of the Doctor button
        buttonAsDoctor.setOnLongClickListener(v -> {
            buttonAsAdmin.setVisibility(View.VISIBLE);
            Toast.makeText(MainActivity.this, "Admin button is now visible", Toast.LENGTH_SHORT).show();
            return true;
        });

        buttonAsAdmin.setOnClickListener(v -> {
            storeUserType("admin");
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);

        });


        clearUserType();
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


    private void clearUserType() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(USER_TYPE_KEY);
        editor.apply();
    }
}
