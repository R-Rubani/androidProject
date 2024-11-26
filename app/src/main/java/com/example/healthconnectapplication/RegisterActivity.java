package com.example.healthconnectapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseHelper dbHelper;

    private EditText firstNameEditText,
            lastNameEditText,
            emailEditText,
            passwordEditText,
            phoneEditText,
            dateEditText;
    private Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        dbHelper = new DatabaseHelper(this);

        firstNameEditText = findViewById(R.id.editTextFirstName);
        lastNameEditText = findViewById(R.id.editTextLastName);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        phoneEditText = findViewById(R.id.editTextPhone);
        dateEditText = findViewById(R.id.editTextDate);
        registerButton = findViewById(R.id.buttonRegister);

        registerButton.setOnClickListener(view -> registerUser());



        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }

    }

    private void registerUser() {
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String dateOfBirth = dateEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(firstName)
                || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(dateOfBirth)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Store additional details in SQLite
                        boolean isInserted = dbHelper.insertUserDetails(firstName, lastName, phone, dateOfBirth);
                        if (isInserted) {
                            Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            // Navigate to login screen or main activity
                            //finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Failed to save details in SQLite", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}