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


        // Initialize the "Add Appointment" button
        ImageButton addAppointment = findViewById(R.id.imageButtonAddAppointment);
        addAppointment.setOnClickListener(v ->
                startActivity(new Intent(AppointmentDetailsActivity.this, NewAppointmentActivity.class))
        );

        // Set up the RecyclerView
        recyclerViewAppointmentDetails = findViewById(R.id.recyclerViewAppointmentDetails);
        recyclerViewAppointmentDetails.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the database helper
        dbHelper = new AppointmentDatabaseHelper(this);

        // Fetch appointments from the database
        List<Appointment> appointments = dbHelper.getAllAppointments();

        // Display appointments in the RecyclerView
        if (appointments.isEmpty()) {
            Toast.makeText(this, "No appointments found.", Toast.LENGTH_SHORT).show();
        } else {
            AppointmentAdapter adapter = new AppointmentAdapter(appointments);
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
