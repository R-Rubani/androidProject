package com.example.healthconnectapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DoctorRegistrationDatabaseHelper dbDoctorHelper;
    private UserRegistrationDatabaseHelper dbUserHelper;

    private EditText firstNameEditText, lastNameEditText, emailEditText, passwordEditText, phoneEditText, dateEditText;
    private Button registerButton;

    // Date variables
    private int mYear, mMonth, mDay;

    // SharedPreferences Constants
    public static final String PREFS_NAME = "UserPrefs";
    public static final String USER_TYPE_KEY = "userType";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        dbDoctorHelper = new DoctorRegistrationDatabaseHelper(this);

        // Initialize views
        firstNameEditText = findViewById(R.id.editTextFirstName);
        lastNameEditText = findViewById(R.id.editTextLastName);
        emailEditText = findViewById(R.id.editTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextPassword);
        phoneEditText = findViewById(R.id.editTextPhone);
        dateEditText = findViewById(R.id.editTextDate);
        registerButton = findViewById(R.id.buttonRegister);

        // Date picker logic
        final Calendar calendar = Calendar.getInstance();
        dateEditText.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    RegisterActivity.this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;

                        // Format selected date
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(mYear, mMonth, mDay);
                        String formattedDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedDate.getTime());
                        dateEditText.setText(formattedDate);
                    },
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });

        // Register button logic
        registerButton.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String dateOfBirth = dateEditText.getText().toString().trim();

        // Validate input fields
        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(email)
                || TextUtils.isEmpty(password) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(dateOfBirth)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Retrieve user type from SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                        String userType = sharedPreferences.getString(USER_TYPE_KEY, "");

                        if ("admin".equals(userType)) {
                            // Insert doctor details for admin
                            boolean isInserted = dbDoctorHelper.insertDoctorDetails(firstName, lastName, email, phone, dateOfBirth);
                            if (isInserted) {
                                Toast.makeText(this, "Doctor details inserted successfully.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Failed to insert doctor details.", Toast.LENGTH_SHORT).show();
                            }
                        } else if ("doctor".equals(userType)) {
                            // Insert general user details for doctor
                            boolean isInserted = dbUserHelper.insertUserDetails(firstName, lastName, email, phone, dateOfBirth);
                            if (isInserted) {
                                Toast.makeText(this, "User details inserted successfully.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Failed to insert user details.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "User type not set or invalid.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
