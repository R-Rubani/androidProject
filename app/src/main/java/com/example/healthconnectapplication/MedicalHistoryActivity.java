package com.example.healthconnectapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MedicalHistoryActivity extends AppCompatActivity {

    EditText editTextAppointmentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }

        editTextAppointmentDate=findViewById(R.id.editTextApptDate);
        editTextAppointmentDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(MedicalHistoryActivity.this, (view, year, month, dayOfMonth) ->
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