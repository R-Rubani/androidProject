package com.example.healthconnectapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditRecordActivity extends AppCompatActivity {

    private EditText editTextDiagnosis, editTextTreatment, editTextMedication;
    private int appointmentId;
    private AppointmentDatabaseHelper dbHelper;
    private Button btnSaveChanges;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_record);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }

        dbHelper = new AppointmentDatabaseHelper(this);

        Intent intent = getIntent();
        appointmentId = intent.getIntExtra("appointmentId", -1);

        editTextDiagnosis = findViewById(R.id.editTextDiagnosis);
        editTextTreatment = findViewById(R.id.editTextTreatment);
        editTextMedication = findViewById(R.id.editTextMedication);

        String diagnosis = intent.getStringExtra("diagnosis");
        String treatment = intent.getStringExtra("treatment");
        String medication = intent.getStringExtra("medication");


        editTextDiagnosis.setText(diagnosis);
        editTextTreatment.setText(treatment);
        editTextMedication.setText(medication);

        btnSaveChanges = findViewById(R.id.buttonSaveChanges);
        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String updatedDiagnosis = editTextDiagnosis.getText().toString();
                String updatedTreatment = editTextTreatment.getText().toString();
                String updatedMedication = editTextMedication.getText().toString();

                boolean isUpdated = dbHelper.updateRecord(
                        appointmentId,
                        updatedDiagnosis,
                        updatedTreatment,
                        updatedMedication
                );
                if (isUpdated) {
                    Toast.makeText(EditRecordActivity.this, "Appointment updated successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditRecordActivity.this,PatientRecordsActivity.class));
                    finish();
                } else {
                    Toast.makeText(EditRecordActivity.this, "Failed to update appointment.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void openDocProfile(MenuItem item) {
        startActivity(new Intent(EditRecordActivity.this, DoctorProfileActivity.class));
    }
    public void openAppointments(MenuItem item) {
        startActivity(new Intent(EditRecordActivity.this, AppointmentDetailsActivity.class));
    }
    public void openPatientRecords(MenuItem item) {
        startActivity(new Intent(EditRecordActivity.this, PatientRecordsActivity.class));
    }

}