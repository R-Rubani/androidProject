package com.example.healthconnectapplication;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MedicalHistoryActivity extends AppCompatActivity {

    EditText editTextAppointmentDate;
    private TextView textViewAppointments;
    public static final String USER_EMAIL_KEY = "userEmail";
    private AppointmentDatabaseHelper appointmentDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history);

        // Initialize the AppointmentDatabaseHelper
        appointmentDbHelper = new AppointmentDatabaseHelper(this);

        // Initialize TextView to display appointments
        textViewAppointments = findViewById(R.id.textViewAppointments);

        // Retrieve the email from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
        String userEmail = sharedPreferences.getString(USER_EMAIL_KEY, null);

        if (userEmail != null) {
            // Fetch the appointments based on the email
            fetchAppointments(userEmail);
        } else {
            Toast.makeText(this, "User email not found", Toast.LENGTH_SHORT).show();
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }

        // Setup DatePicker for appointment date
        editTextAppointmentDate = findViewById(R.id.editTextApptDate);
        editTextAppointmentDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(MedicalHistoryActivity.this, (view, year, month, dayOfMonth) -> {
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
        });
    }

    // Method to fetch appointments from the database based on email
    private void fetchAppointments(String userEmail) {
        List<Appointment> appointments = appointmentDbHelper.getAppointmentsByEmail(userEmail);

        if (appointments != null && !appointments.isEmpty()) {
            StringBuilder appointmentsDetails = new StringBuilder();

            // Build a string to display the appointment details
            for (Appointment appointment : appointments) {
                appointmentsDetails.append("Appointment Date: ").append(appointment.getAppointmentDate()).append("\n");
                appointmentsDetails.append("Doctor: ").append(appointment.getDoctorEmail()).append("\n");
                appointmentsDetails.append("Diagnosis: ").append(appointment.getDiagnosis()).append("\n");
                appointmentsDetails.append("Treatment: ").append(appointment.getTreatment()).append("\n");
                appointmentsDetails.append("Medication: ").append(appointment.getMedication()).append("\n\n");
            }

            // Set the appointments details to the TextView
            textViewAppointments.setText(appointmentsDetails.toString());
        } else {
            textViewAppointments.setText("No appointments found.");
        }
    }

    public void openPatientProfile(MenuItem item) {
        startActivity(new Intent(MedicalHistoryActivity.this, PatientProfileActivity.class));
    }

    public void openPatientAppointments(MenuItem item) {
        startActivity(new Intent(MedicalHistoryActivity.this, PatientAppointmentsActivity.class));
    }

    public void openMedicalHistory(MenuItem item) {
        startActivity(new Intent(MedicalHistoryActivity.this, MedicalHistoryActivity.class));
    }
}
