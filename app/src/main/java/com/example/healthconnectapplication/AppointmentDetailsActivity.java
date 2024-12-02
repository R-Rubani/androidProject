package com.example.healthconnectapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AppointmentDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAppointmentDetails;
    private AppointmentAdapter appointmentAdapter; // Create an adapter for the RecyclerView
    private AppointmentDatabaseHelper dbHelper;
    private EditText editTextAppointmentDate;
    private static final String CHANNEL_ID = "appointment_channel";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_details);

        // Set the app's logo in the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Define the channel name and importance
            CharSequence name = "Appointment Notifications";
            String description = "Notifications for scheduled appointments.";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Register the channel with the system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }


        editTextAppointmentDate = findViewById(R.id.editTextApptDate);

        //get the current date
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String currentDate = formatter.format(new Date());

        editTextAppointmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(AppointmentDetailsActivity.this, (view, year, month, dayOfMonth) ->
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
        String selectedDate = editTextAppointmentDate.getText().toString();
        if(selectedDate.equals(currentDate)){

            NotificationCompat.Builder builder = new NotificationCompat.Builder(AppointmentDetailsActivity.this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.health_connect_logo)
                    .setContentTitle("Reminder")
                    .setContentText("You have an appointment scheduled for today. Check your appointment details for more information.")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, builder.build());

        }


        ImageButton addAppointment = findViewById(R.id.imageButtonAddAppointment);
        addAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppointmentDetailsActivity.this,NewAppointmentActivity.class));
            }
        });

        recyclerViewAppointmentDetails = findViewById(R.id.recyclerViewAppointmentDetails);
        recyclerViewAppointmentDetails.setLayoutManager(new LinearLayoutManager(this));

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
        if (userType.equals("doctor")) {
            fetchDoctorAppointments(userEmail);
        } else if (userType.equals("patient")) {
            fetchPatientAppointments(userEmail);
        } else {
            Toast.makeText(this, "Invalid user type. Please log in again.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    // Fetch appointments for a doctor using their email
    private void fetchDoctorAppointments(String email) {
        List<Appointment> appointments = dbHelper.getAppointmentsByDoctorEmail(email);

        if (appointments.isEmpty()) {
            Toast.makeText(this, "No appointments found for the logged-in doctor.", Toast.LENGTH_SHORT).show();
        } else {
            appointmentAdapter = new AppointmentAdapter(this, appointments);
            recyclerViewAppointmentDetails.setAdapter(appointmentAdapter);
        }
    }

    // Fetch appointments for a patient using their email
    private void fetchPatientAppointments(String email) {
        List<Appointment> appointments = dbHelper.getAppointmentsByPatientEmail(email);

        if (appointments.isEmpty()) {
            Toast.makeText(this, "No appointments found for the logged-in patient.", Toast.LENGTH_SHORT).show();
        } else {
            appointmentAdapter = new AppointmentAdapter(this, appointments);
            recyclerViewAppointmentDetails.setAdapter(appointmentAdapter);
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
