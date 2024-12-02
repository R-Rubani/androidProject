package com.example.healthconnectapplication;


import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class NewAppointmentActivity extends AppCompatActivity {

    private EditText editTextPatientID, editTextFirstName, editTextLastName, editTextAppointmentDate;
    private AppointmentDateDatabaseHelper dbHelper;
    private UserRegistrationDatabaseHelper uDbHelper;
    String patientEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_appointment);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }

        uDbHelper = new UserRegistrationDatabaseHelper(this);

        editTextAppointmentDate = findViewById(R.id.editTextAppointmentDate);
        editTextAppointmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewAppointmentActivity.this, (view, year, month, dayOfMonth) ->
                {
                    // Format selected date and set it to TextView
                    calendar.set(year, month, dayOfMonth);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    String selectedDate = sdf.format(calendar.getTime());
                    editTextAppointmentDate.setText(selectedDate);
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );

                datePickerDialog.show();
            }
        });



        // Initialize views
        editTextPatientID = findViewById(R.id.editTextPatientID);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextAppointmentDate = findViewById(R.id.editTextAppointmentDate);
        ImageButton imageBtnAddRecord = findViewById(R.id.imageBtnAddRecord);

        // Initialize the database helper
        dbHelper = new AppointmentDateDatabaseHelper(this);

        // Set click listener for the add record button
        imageBtnAddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get data from input fields
                String patientId = editTextPatientID.getText().toString().trim();
                String firstName = editTextFirstName.getText().toString().trim();
                String lastName = editTextLastName.getText().toString().trim();
                String appointmentDate = editTextAppointmentDate.getText().toString().trim();
                patientEmail = uDbHelper.getEmailByPatientId(parseInt(patientId));

                // Validate input fields
                if (patientId.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || appointmentDate.isEmpty()) {
                    Toast.makeText(NewAppointmentActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Save data in the database
                    dbHelper.addAppointmentDate(patientId,patientEmail, firstName, lastName, appointmentDate);
                    Toast.makeText(NewAppointmentActivity.this, "Appointment added successfully", Toast.LENGTH_SHORT).show();
                    // Optionally, navigate back to AppointmentDetailsActivity or another activity
                    startActivity(new Intent(NewAppointmentActivity.this, AppointmentDetailsActivity.class));
                    finish();  // Close current activity
                }
            }
        });
    }

    // Bottom navigation methods
    public void openDocProfile(MenuItem item) {
        startActivity(new Intent(NewAppointmentActivity.this, DoctorProfileActivity.class));
    }

    public void openAppointments(MenuItem item) {
        startActivity(new Intent(NewAppointmentActivity.this, AppointmentDetailsActivity.class));
    }

    public void openPatientRecords(MenuItem item) {
        startActivity(new Intent(NewAppointmentActivity.this, PatientRecordsActivity.class));
    }
}
