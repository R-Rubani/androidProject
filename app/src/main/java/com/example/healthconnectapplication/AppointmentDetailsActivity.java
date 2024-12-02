package com.example.healthconnectapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

public class AppointmentDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAppointmentDetails;
    private AppointmentDateDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }

        // Add appointment button
        ImageButton addAppointment = findViewById(R.id.imageButtonAddAppointment);
        addAppointment.setOnClickListener(v ->
                startActivity(new Intent(AppointmentDetailsActivity.this, NewAppointmentActivity.class))
        );

        // Set up the RecyclerView
        recyclerViewAppointmentDetails = findViewById(R.id.recyclerViewAppointmentDetails);
        recyclerViewAppointmentDetails.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the database helper
        dbHelper = new AppointmentDateDatabaseHelper(this);

        // Fetch appointment dates from the database
        List<AppointmentDate> appointmentDateList = dbHelper.getAllAppointments();

        // Display appointment dates in the RecyclerView
        if (appointmentDateList.isEmpty()) {
            Toast.makeText(this, "No appointments found.", Toast.LENGTH_SHORT).show();
        } else {
            AppointmentDateAdapter adapter = new AppointmentDateAdapter(appointmentDateList);
            recyclerViewAppointmentDetails.setAdapter(adapter);
        }
    }

    // Bottom navigation methods
    public void openDocProfile(MenuItem item) {
        startActivity(new Intent(AppointmentDetailsActivity.this, DoctorProfileActivity.class));
    }

    public void openAppointments(MenuItem item) {
        startActivity(new Intent(AppointmentDetailsActivity.this, AppointmentDetailsActivity.class));
    }

    public void openPatientRecords(MenuItem item) {
        startActivity(new Intent(AppointmentDetailsActivity.this, PatientRecordsActivity.class));
    }
}
