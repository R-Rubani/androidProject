package com.example.healthconnectapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    public static final String USER_EMAIL_KEY = "userEmail";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
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
                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                loginUser(email, password);
            }
        });
    }
    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {


                        // Login successful
                        FirebaseUser user = mAuth.getCurrentUser();

                        Toast.makeText(LoginActivity.this, "Welcome " + user.getEmail(), Toast.LENGTH_SHORT).show();

                        String userEmail = user.getEmail().toString();
                        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
                        //SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(USER_EMAIL_KEY,userEmail);
                        editor.apply(); // Save the preference

                        // Navigate to the next screen
                        Intent intent = new Intent(LoginActivity.this, DoctorProfileActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Login failed
                        Toast.makeText(LoginActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}