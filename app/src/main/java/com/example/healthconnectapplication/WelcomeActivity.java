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

        SQLiteDatabase db = openOrCreateDatabase("DoctorDetails.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS DoctorDetails (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "firstName TEXT NOT NULL, " +
                "lastName TEXT NOT NULL, " +
                "email TEXT NOT NULL)");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.mipmap.ic_launcher);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }
        TimerTask task = new TimerTask( ) {

            @Override
            public void run() {
               finish();
                startActivity(new Intent(WelcomeActivity.this, RegisterActivity.class));
            }
       };

        Timer opening = new Timer( );
        opening.schedule(task,3000);

    }
}