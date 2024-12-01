package com.example.healthconnectapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private UserRegistrationDatabaseHelper dbHelper;
    private EditText editTextFirstName, editTextLastName, editTextEmailAddress, editTextPhone, editTextPassword, editTextDate;
    private Button buttonRegister;
    private TextView tvClickToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize the database helper
        dbHelper = new UserRegistrationDatabaseHelper(this);

        // Initialize views
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextEmailAddress = findViewById(R.id.editTextEmailAddress);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextDate = findViewById(R.id.editTextDate);
        buttonRegister = findViewById(R.id.buttonRegister);
        tvClickToLogin = findViewById(R.id.tvClickToLogin);

        // Set onClick listener for the register button
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        // Optional: Handle "Click to Login" text if you want navigation
        tvClickToLogin.setOnClickListener(v -> {
            // Logic for navigating to login screen can be added here
            Toast.makeText(RegisterActivity.this, "Login navigation clicked!", Toast.LENGTH_SHORT).show();
        });
    }

    private void registerUser() {
        // Get input values
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String email = editTextEmailAddress.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String birthDate = editTextDate.getText().toString().trim();

        // Validate input fields
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || birthDate.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insert data into the database
        boolean isInserted = dbHelper.insertUserDetails(firstName, lastName, email, phone, birthDate);
        if (isInserted) {
            Toast.makeText(this, "User registered successfully!", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(this, "Registration failed. Try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        editTextFirstName.setText("");
        editTextLastName.setText("");
        editTextEmailAddress.setText("");
        editTextPhone.setText("");
        editTextPassword.setText("");
        editTextDate.setText("");
    }
}
