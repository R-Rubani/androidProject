package com.example.healthconnectapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewAppointmentActivity extends AppCompatActivity {

    private EditText editTextPatientID, editTextFirstName, editTextLastName, editTextAppointmentDate;
    private AppointmentDateDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_appointment);



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

                // Validate input fields
                if (patientId.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || appointmentDate.isEmpty()) {
                    Toast.makeText(NewAppointmentActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Save data in the database
                    dbHelper.addAppointmentDate(patientId, firstName, lastName, appointmentDate);
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
