package com.example.healthconnectapplication;

import static java.lang.Integer.parseInt;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddNewPatientRecordActivity extends AppCompatActivity {

    private EditText editTextPatientID, editTextFirstName, editTextLastName, editTextAppointmentDate,
            editTextDiagnosis, editTextTreatment, editTextMedication;
    private ImageButton imageBtnAddRecord;

    private FirebaseAuth mAuth;
    private FirebaseAuthUtils fbAuth;
    private AppointmentDatabaseHelper dbHelper;
    private UserRegistrationDatabaseHelper uDbHelper;
    private DoctorRegistrationDatabaseHelper dDbHelper;
    private int docId;
    private String doctorId;

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
        fbAuth = new FirebaseAuthUtils();

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
        dDbHelper = new DoctorRegistrationDatabaseHelper(this);
        uDbHelper = new UserRegistrationDatabaseHelper(this);

        editTextAppointmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewPatientRecordActivity.this, (view, year, month, dayOfMonth) ->
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

        // Get the doctor ID
        docId = getDocId(fbAuth.getLoggedInUserEmail());
        if (docId == -1) {
            Toast.makeText(this, "No doctor found with this email. Please check your login status.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            doctorId = String.valueOf(docId);
        }

        // Check if the patient ID is valid
        if (!isPatientIDValid(patientID)) {
            Toast.makeText(AddNewPatientRecordActivity.this, "Invalid Patient ID", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save the patient record to the SQLite database
        savePatientRecord(patientID, doctorId, firstName, lastName, appointmentDate, diagnosis, treatment, medication);

        // Show a success message and navigate back to the patient records activity
        Toast.makeText(AddNewPatientRecordActivity.this, "Record added successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(AddNewPatientRecordActivity.this, PatientRecordsActivity.class));
        finish(); // Close activity after adding the record
    }

    private boolean isPatientIDValid(String patientID) {
        // Validate patient ID against the database
        return uDbHelper.isPatientIdValid(parseInt(patientID));
    }

    private int getDocId(String doctorEmail) {
        Log.d("AddNewPatientRecord", "Doctor Email: " + doctorEmail); // Log the email for debugging
        return dDbHelper.getDoctorIdByEmail(doctorEmail);
    }

    private void savePatientRecord(String patientID, String doctorID, String firstName, String lastName,
                                   String appointmentDate, String diagnosis, String treatment, String medication) {
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
        values.put(AppointmentDatabaseHelper.COLUMN_DOCTOR_ID, doctorID); // Save the doctor ID

        // Insert the record into the database
        long newRowId = db.insert(AppointmentDatabaseHelper.TABLE_NAME, null, values);
        if (newRowId == -1) {
            Toast.makeText(this, "Failed to insert the record", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Record added successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
