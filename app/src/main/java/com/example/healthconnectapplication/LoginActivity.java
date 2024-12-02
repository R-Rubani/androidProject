package com.example.healthconnectapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private DoctorRegistrationDatabaseHelper doctorDbHelper;
    private UserRegistrationDatabaseHelper patientDbHelper;

    public static final String USER_EMAIL_KEY = "userEmail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth and database helpers
        mAuth = FirebaseAuth.getInstance();
        doctorDbHelper = new DoctorRegistrationDatabaseHelper(this);
        patientDbHelper = new UserRegistrationDatabaseHelper(this);

        emailEditText = findViewById(R.id.editTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }

        loginButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                validateAndLogin(email, password);
            }
        });
    }

    private void validateAndLogin(String email, String password) {
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        String userType = sharedPreferences.getString(MainActivity.USER_TYPE_KEY, "");

        if (userType.isEmpty()) {
            Toast.makeText(this, "User type not selected. Please try again.", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean emailExists;
        if (userType.equals("doctor")) {
            emailExists = doctorDbHelper.isDoctorEmailExists(email);
        } else if (userType.equals("patient")) {
            emailExists = patientDbHelper.isPatientEmailExists(email);
        } else {
            Toast.makeText(this, "Invalid user type.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (emailExists) {
            loginUser(email, password);
        } else {
            Toast.makeText(this, "Email not found in the " + userType + " database.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Login successful
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(LoginActivity.this, "Welcome " + user.getEmail(), Toast.LENGTH_SHORT).show();

                        // Save user email in SharedPreferences
                        saveUserEmail(user.getEmail());
                        navigateBasedOnUserType();
                    } else {
                        // Login failed
                        Toast.makeText(LoginActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveUserEmail(String email) {
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_EMAIL_KEY, email);
        editor.apply();
    }

    private void navigateBasedOnUserType() {
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        String userType = sharedPreferences.getString(MainActivity.USER_TYPE_KEY, "");

        Intent intent;
        switch (userType) {
            case "doctor":
                intent = new Intent(LoginActivity.this, DoctorProfileActivity.class);
                break;
            case "patient":
                intent = new Intent(LoginActivity.this, PatientProfileActivity.class);
                break;
            case "admin":
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                break;
            default:
                Toast.makeText(this, "Invalid user type. Please try again.", Toast.LENGTH_SHORT).show();
                return;
        }

        startActivity(intent);
        finish();
    }
}
