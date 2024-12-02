package com.example.healthconnectapplication;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class AppointmentDateDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "appointmentsDate.db";
    public static final int DATABASE_VERSION = 6; // Increment version

    // Table name and columns for the AppointmentDate table
    public static final String TABLE_APPOINTMENT_DATE = "AppointmentDate";
    public static final String COLUMN_PATIENT_ID = "patient_id";
    public static final String COLUMN_PATIENT_EMAIL = "patient_email";

    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_APPOINTMENT_DATE = "appointment_date";

    // SQL statement to create the AppointmentDate table
    private static final String SQL_CREATE_APPOINTMENT_DATE_TABLE =
            "CREATE TABLE " + TABLE_APPOINTMENT_DATE + " (" +
                    COLUMN_PATIENT_ID + " TEXT, " +
                    COLUMN_PATIENT_EMAIL + " TEXT, " +
                    COLUMN_FIRST_NAME + " TEXT, " +
                    COLUMN_LAST_NAME + " TEXT, " +
                    COLUMN_APPOINTMENT_DATE + " TEXT, " +
                    "PRIMARY KEY (" + COLUMN_PATIENT_ID + ", " + COLUMN_APPOINTMENT_DATE + "));";

    // SQL statement to delete the AppointmentDate table
    private static final String SQL_DELETE_APPOINTMENT_DATE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_APPOINTMENT_DATE;

    public AppointmentDateDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_APPOINTMENT_DATE_TABLE); // Create the AppointmentDate table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_APPOINTMENT_DATE_TABLE); // Drop the AppointmentDate table if it exists
        onCreate(db); // Create a new one
    }
    public void addAppointmentDate(String patientId, String patientEmail, String firstName, String lastName, String appointmentDate) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Create SQL statement for inserting data
        String insertSQL = "INSERT INTO " + TABLE_APPOINTMENT_DATE + " (" +
                COLUMN_PATIENT_ID + ", " +
                COLUMN_PATIENT_EMAIL + ", " +
                COLUMN_FIRST_NAME + ", " +
                COLUMN_LAST_NAME + ", " +
                COLUMN_APPOINTMENT_DATE + ") VALUES (?, ?, ?, ?,?)";

        // Prepare and execute the insertion
        db.execSQL(insertSQL, new Object[]{patientId, patientEmail,firstName, lastName, appointmentDate});
    }

    public List<AppointmentDate> getAppointmentsByEmail(String patientEmail) {
        List<AppointmentDate> appointmentDates = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to select data based on the patient's email
        String query = "SELECT * FROM " + TABLE_APPOINTMENT_DATE + " WHERE " + COLUMN_PATIENT_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{patientEmail});

        if (cursor.moveToFirst()) {
            do {
                // Create an AppointmentDate object for each row
                AppointmentDate appointmentDate = new AppointmentDate(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PATIENT_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PATIENT_EMAIL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_DATE))
                );
                appointmentDates.add(appointmentDate);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return appointmentDates;
    }


}
