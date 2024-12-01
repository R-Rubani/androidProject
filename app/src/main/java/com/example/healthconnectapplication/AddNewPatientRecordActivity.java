package com.example.healthconnectapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class AddNewPatientRecordActivity extends AppCompatActivity {

    private EditText editTextPatientID, editTextFirstName, editTextLastName, editTextAppointmentDate,
            editTextDiagnosis, editTextTreatment, editTextMedication;
    private ImageButton imageBtnAddRecord;

    private FirebaseAuth mAuth;
    private AppointmentDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_patient_record);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Views
        editTextPatientID = findViewById(R.id.editTextPatientID);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextAppointmentDate = findViewById(R.id.editTextAppointmentDate);
        editTextDiagnosis = findViewById(R.id.editTextDiagnosis);
        editTextTreatment = findViewById(R.id.editTextTreatment);
        editTextMedication = findViewById(R.id.editTextMedication);
        imageBtnAddRecord = findViewById(R.id.imageBtnAddRecord);

        // Initialize the database helper for SQLite
        dbHelper = new AppointmentDatabaseHelper(this);

        // Set onClickListener for the "Add Record" button
        imageBtnAddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPatientRecord();
            }
        });
    }
    public void openDocProfile(MenuItem item) {
        startActivity(new Intent(AddNewPatientRecordActivity.this, DoctorProfileActivity.class));
    }
    public void openAppointments(MenuItem item) {
        startActivity(new Intent(AddNewPatientRecordActivity.this, AppointmentDetailsActivity.class));
    }
    public void openPatientRecords(MenuItem item) {
        startActivity(new Intent(AddNewPatientRecordActivity.this, PatientRecordsActivity.class));
    }

    private void addPatientRecord() {
        // Retrieve input values from the EditTexts
        String patientID = editTextPatientID.getText().toString().trim();
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String appointmentDate = editTextAppointmentDate.getText().toString().trim();
        String diagnosis = editTextDiagnosis.getText().toString().trim();
        String treatment = editTextTreatment.getText().toString().trim();
        String medication = editTextMedication.getText().toString().trim();

        // Validate the input fields
        if (patientID.isEmpty() || firstName.isEmpty() || lastName.isEmpty() ||
                appointmentDate.isEmpty() || diagnosis.isEmpty() || treatment.isEmpty() || medication.isEmpty()) {
            Toast.makeText(AddNewPatientRecordActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the patient ID is valid (could be checked against a database if needed)
        if (!isPatientIDValid(patientID)) {
            Toast.makeText(AddNewPatientRecordActivity.this, "Invalid Patient ID", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save the patient record to the SQLite database
        savePatientRecord(patientID, firstName, lastName, appointmentDate, diagnosis, treatment, medication);

        // Show a success message
        Toast.makeText(AddNewPatientRecordActivity.this, "Record added successfully", Toast.LENGTH_SHORT).show();
        finish(); // Close activity after adding the record
    }

    // Simulate checking if a Patient ID exists
    private boolean isPatientIDValid(String patientID) {
        // In a real scenario, you would query the patient database to check if the ID exists.
        // Here, just returning true for simplicity.
        return true;
    }

    // Method to save the patient record into the SQLite database
    private void savePatientRecord(String patientID, String firstName, String lastName, String appointmentDate,
                                   String diagnosis, String treatment, String medication) {
        // Get writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a ContentValues object to hold the data
        ContentValues values = new ContentValues();
        values.put(AppointmentDatabaseHelper.COLUMN_PATIENT_ID, patientID);
        values.put(AppointmentDatabaseHelper.COLUMN_FIRST_NAME, firstName);
        values.put(AppointmentDatabaseHelper.COLUMN_LAST_NAME, lastName);
        values.put(AppointmentDatabaseHelper.COLUMN_APPOINTMENT_DATE, appointmentDate);
        values.put(AppointmentDatabaseHelper.COLUMN_DIAGNOSIS, diagnosis);
        values.put(AppointmentDatabaseHelper.COLUMN_TREATMENT, treatment);
        values.put(AppointmentDatabaseHelper.COLUMN_MEDICATION, medication);

        // Insert the record into the database
        long newRowId = db.insert(AppointmentDatabaseHelper.TABLE_NAME, null, values);
        if (newRowId == -1) {
            Toast.makeText(this, "Failed to insert the record", Toast.LENGTH_SHORT).show();
        }
    }

}
