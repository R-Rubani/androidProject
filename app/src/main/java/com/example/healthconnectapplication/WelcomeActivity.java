package com.example.healthconnectapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);



        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }
//        DoctorRegistrationDatabaseHelper doctorHelper = new DoctorRegistrationDatabaseHelper(this);
//        UserRegistrationDatabaseHelper userHelper = new UserRegistrationDatabaseHelper(this);
//        AppointmentDatabaseHelper appointmentHelper = new AppointmentDatabaseHelper(this);
//        AppointmentDateDatabaseHelper appointmentDateHelper = new AppointmentDateDatabaseHelper(this);
//
//        if (!doctorHelper.isDoctorEmailExists("john.doe@example.com")) {
//            doctorHelper.insertDoctorDetails("John", "Doe", "john.doe@example.com", "1234567890", "1980-01-01");
//        }
//        if (!doctorHelper.isDoctorEmailExists("jane.smith@example.com")) {
//            doctorHelper.insertDoctorDetails("Jane", "Smith", "jane.smith@example.com", "0987654321", "1985-05-05");
//        }
//
//        // Check if UserDetails table is empty and insert dummy data if necessary
//        if (!userHelper.isPatientExists("alice.johnson@example.com")) {
//            userHelper.insertUserDetails("Alice", "Johnson", "alice.johnson@example.com", "1122334455", "1990-07-15");
//        }
//        if (!userHelper.isPatientExists("bob.williams@example.com")) {
//            userHelper.insertUserDetails("Bob", "Williams", "bob.williams@example.com", "5566778899", "1992-12-20");
//        }
//
//        if (!appointmentHelper.isAppointmentExists(1, 1)) {
//            appointmentHelper.insertAppointmentDetails(
//                    1, "patient1@example.com", 1, "doctor1@example.com",
//                    "John", "Doe", "2024-12-10", "Flu", "Rest", "Paracetamol"
//            );
//        }
//
//        if (!appointmentHelper.isAppointmentExists(2, 2)) {
//            appointmentHelper.insertAppointmentDetails(
//                    2, "patient2@example.com", 2, "doctor2@example.com",
//                    "Jane", "Smith", "2024-12-12", "Cold", "Hydration", "Ibuprofen"
//            );
//        }
//
//        if (!appointmentDateHelper.isAppointmentDateExists("2024-12-10")) {
//            appointmentDateHelper.addAppointmentDate("1","patient1@example.com", "Alice", "Johnson","2024-12-10");
//        }
//        if (!appointmentDateHelper.isAppointmentDateExists("2024-12-12")) {
//            appointmentDateHelper.addAppointmentDate("2", "alice.johnson@example.com", "Alice","Johnson","2024-12-12");
//        }
//


        TimerTask task = new TimerTask( ) {

            @Override
            public void run() {
               finish();
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            }
       };

        Timer opening = new Timer( );
        opening.schedule(task,3000);

    }
}