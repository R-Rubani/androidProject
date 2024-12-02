package com.example.healthconnectapplication;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private UserRegistrationDatabaseHelper dbHelper;
    private DoctorRegistrationDatabaseHelper dDbHelper;

    private EditText editTextFirstName, editTextLastName, editTextEmailAddress, editTextPhone, editTextPassword, editTextDate;
    private Button buttonRegister;
    private TextView tvClickToLogin;

    // SharedPreferences Constants
    public static final String PREFS_NAME = "UserPrefs";
    public static final String USER_TYPE_KEY = "userType";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize the database helpers
        dbHelper = new UserRegistrationDatabaseHelper(this);
        dDbHelper = new DoctorRegistrationDatabaseHelper(this);

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
        buttonRegister.setOnClickListener(v -> registerUser());

        // Optional: Handle "Click to Login" text if you want navigation
        tvClickToLogin.setOnClickListener(v -> {
            Toast.makeText(RegisterActivity.this, "Login navigation clicked!", Toast.LENGTH_SHORT).show();
        });

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, (view,year, month, dayOfMonth) ->
                        {
                            // Format selected date and set it to TextView
                            calendar.set(year, month, dayOfMonth);
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                            String selectedDate = sdf.format(calendar.getTime());
                            editTextDate.setText(selectedDate);
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );

                datePickerDialog.show();
            }
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

        // Firebase Authentication - Create User with Email and Password
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // User successfully registered in Firebase
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        String userId = firebaseUser != null ? firebaseUser.getUid() : "";

                        // After Firebase signup, store user details in SQLite
                        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                        String userType = sharedPreferences.getString(USER_TYPE_KEY, "patient"); // Default to "patient"

                        boolean isInserted;

                        // Insert data based on user type
                        if (userType.equals("doctor")) {
                            isInserted = dbHelper.insertUserDetails(firstName, lastName, email, phone, birthDate);
                        } else if (userType.equals("admin")) {
                            isInserted = dDbHelper.insertDoctorDetails(firstName, lastName, email, phone, birthDate);
                        } else {
                            isInserted = dbHelper.insertUserDetails(firstName, lastName, email, phone, birthDate);
                        }

                        // Show appropriate message
                        if (isInserted) {
                            Toast.makeText(RegisterActivity.this, userType + " registered successfully!", Toast.LENGTH_SHORT).show();
                            clearFields();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration failed. Try again.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Firebase signup failed
                        String errorMessage = task.getException() != null ? task.getException().getMessage() : "Registration failed!";
                        Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
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
