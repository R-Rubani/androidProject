package com.example.healthconnectapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditPatientProfileActivity extends AppCompatActivity {


    private EditText  editTextContactNumber;
    private String patientEmail;
    private UserRegistrationDatabaseHelper dbHelper;
    private Button btnSaveChanges;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_patient_profile);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }
        dbHelper = new UserRegistrationDatabaseHelper(this);

        Intent intent = getIntent();
        String patientEmail = intent.getStringExtra("patientEmail");


        editTextContactNumber = findViewById(R.id.editTextContactNumber);
        String contactNumber = intent.getStringExtra("phone");
        editTextContactNumber.setText(contactNumber);


        btnSaveChanges = findViewById(R.id.buttonSaveChanges);
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String updatedPhone = editTextContactNumber.getText().toString();


                boolean isUpdated = dbHelper.updateRecord(
                        patientEmail,
                        updatedPhone
                );
                if (isUpdated) {
                    Toast.makeText(EditPatientProfileActivity.this, "Patient Profile updated successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditPatientProfileActivity.this,PatientProfileActivity.class));
                    finish();

                } else {
                    Toast.makeText(EditPatientProfileActivity.this, "Failed to update patient profile.", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
    public void openPatientProfile(MenuItem item) {
        startActivity(new Intent(EditPatientProfileActivity.this, PatientProfileActivity.class));
    }
    public void openPatientAppointments(MenuItem item) {
        startActivity(new Intent(EditPatientProfileActivity.this, PatientAppointmentsActivity.class));
    }
    public void openMedicalHistory(MenuItem item) {
        startActivity(new Intent(EditPatientProfileActivity.this, MedicalHistoryActivity.class));
    }

}