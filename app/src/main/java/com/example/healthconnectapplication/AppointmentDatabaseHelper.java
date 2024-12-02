package com.example.healthconnectapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class AppointmentDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "appointments.db";
    public static final int DATABASE_VERSION = 5;

    public static final String TABLE_NAME = "appointments";
    public static final String COLUMN_APPOINTMENT_ID = "appointment_id";
    public static final String COLUMN_PATIENT_ID = "patient_id";
    public static final String COLUMN_PATIENT_EMAIL = "patient_email";
    public static final String COLUMN_DOCTOR_ID = "doctor_id";
    public static final String COLUMN_DOCTOR_EMAIL ="doctor_email";

    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_APPOINTMENT_DATE = "appointment_date";
    public static final String COLUMN_DIAGNOSIS = "diagnosis";
    public static final String COLUMN_TREATMENT = "treatment";
    public static final String COLUMN_MEDICATION = "medication";

    // SQL statement to create the table
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_APPOINTMENT_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PATIENT_ID + " TEXT, " +
                    COLUMN_PATIENT_EMAIL + " TEXT, " +
                    COLUMN_DOCTOR_ID + " TEXT, " +
                    COLUMN_DOCTOR_EMAIL + " TEXT, " +
                    COLUMN_FIRST_NAME + " TEXT, " +
                    COLUMN_LAST_NAME + " TEXT, " +
                    COLUMN_APPOINTMENT_DATE + " TEXT, " +
                    COLUMN_DIAGNOSIS + " TEXT, " +
                    COLUMN_TREATMENT + " TEXT, " +
                    COLUMN_MEDICATION + " TEXT)";

    // SQL statement to delete the table
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public AppointmentDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES); // Create the appointments table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES); // Drop the table if it exists
        onCreate(db); // Create a new one
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                Appointment appointment = new Appointment(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PATIENT_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PATIENT_EMAIL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR_EMAIL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIAGNOSIS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TREATMENT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MEDICATION))
                );
                appointments.add(appointment);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return appointments;
    }

    public List<Appointment> getAppointmentsByEmail(String patientEmail) {
        List<Appointment> appointments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to fetch appointments for the given email
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_PATIENT_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{patientEmail});

        if (cursor.moveToFirst()) {
            do {
                Appointment appointment = new Appointment(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PATIENT_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PATIENT_EMAIL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOCTOR_EMAIL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APPOINTMENT_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DIAGNOSIS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TREATMENT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MEDICATION))
                );
                appointments.add(appointment);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return appointments;
    }

    public boolean updateRecord(
            int appointmentId,
            String diagnosis,
            String treatment,
            String medication)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DIAGNOSIS, diagnosis);
        values.put(COLUMN_TREATMENT, treatment);
        values.put(COLUMN_MEDICATION, medication);

        // Update the row and check the number of rows affected
        int rowsAffected = db.update(TABLE_NAME, values, COLUMN_APPOINTMENT_ID + " = ?",
                new String[]{String.valueOf(appointmentId)});

        return rowsAffected > 0;
    }




}
