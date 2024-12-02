package com.example.healthconnectapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PatientRecordsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewPatientRecords;
    private AppointmentDatabaseHelper dbHelper;
    private EditText editTextAppointmentDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_records);

      editTextAppointmentDate = findViewById(R.id.editTextApptDate);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
//            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }

        editTextAppointmentDate = findViewById(R.id.editTextApptDate);
        if (editTextAppointmentDate != null) {
            editTextAppointmentDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar calendar = Calendar.getInstance();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(PatientRecordsActivity.this,
                            (view, year, month, dayOfMonth) -> {
                                // Format selected date and set it to EditText
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
        } else {
            Log.e("PatientRecordsActivity", "EditText not found. Check your layout XML.");
        }



        ImageButton addNewRecord = findViewById(R.id.imageButtonAddNewRecord);
        addNewRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientRecordsActivity.this,OldOrNewPatientActivity.class));
            }
        });

        recyclerViewPatientRecords = findViewById(R.id.recyclerViewPatientRecords);
        recyclerViewPatientRecords.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new AppointmentDatabaseHelper(this);

        // Fetch shared preferences to determine who logged in
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREFS_NAME, MODE_PRIVATE);
        String userType = sharedPreferences.getString("userType", null); // "doctor" or "patient"
        String userEmail = sharedPreferences.getString("userEmail", null); // Email used for login

        if (userEmail==null) {
            Toast.makeText(this, "No user email found. Please log in again.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Fetch and display data based on user type

        recyclerViewPatientRecords = findViewById(R.id.recyclerViewPatientRecords);
        recyclerViewPatientRecords.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the database helper
        dbHelper = new AppointmentDatabaseHelper(this);

        // Fetch appointments from the database
        List<Appointment> appointments = dbHelper.getAllAppointments();

        // Display appointments in the RecyclerView
        if (appointments.isEmpty()) {
            Toast.makeText(this, "No appointments found.", Toast.LENGTH_SHORT).show();
        } else {
            AppointmentAdapter adapter = new AppointmentAdapter(appointments,this);
            recyclerViewPatientRecords.setAdapter(adapter);
        }
    }


    public void editRecord(View view){
        startActivity(new Intent(PatientRecordsActivity.this, EditRecordActivity.class));
    }

    //bottom Navigation methods
    public void openDocProfile(MenuItem item) {
        startActivity(new Intent(PatientRecordsActivity.this, DoctorProfileActivity.class));
    }
    public void openAppointments(MenuItem item) {
        startActivity(new Intent(PatientRecordsActivity.this, AppointmentDetailsActivity.class));
    }
    public void openPatientRecords(MenuItem item) {
        startActivity(new Intent(PatientRecordsActivity.this, PatientRecordsActivity.class));
    }
}