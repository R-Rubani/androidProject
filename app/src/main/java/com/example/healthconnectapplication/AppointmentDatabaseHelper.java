package com.example.healthconnectapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class AppointmentDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "appointments.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "appointments";
    public static final String COLUMN_PATIENT_ID = "patient_id";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_APPOINTMENT_DATE = "appointment_date";
    public static final String COLUMN_DIAGNOSIS = "diagnosis";
    public static final String COLUMN_TREATMENT = "treatment";
    public static final String COLUMN_MEDICATION = "medication";

    // SQL statement to create the table
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_PATIENT_ID + " TEXT PRIMARY KEY, " +
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

    public List<Appointment> getAppointmentsByPatientEmail(String email) {
        List<Appointment> appointments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM appointments WHERE patient_email = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        if (cursor.moveToFirst()) {
            do {
                appointments.add(new Appointment(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("patient_name")),
                        cursor.getString(cursor.getColumnIndex("appointment_date")),
                        cursor.getString(cursor.getColumnIndex("diagnosis")),
                        cursor.getString(cursor.getColumnIndex("treatment"))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return appointments;
    }

    public List<Appointment> getAppointmentsByDoctorEmail(String email) {
        List<Appointment> appointments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM appointments WHERE doctor_email = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        if (cursor.moveToFirst()) {
            do {
                appointments.add(new Appointment(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("patient_name")),
                        cursor.getString(cursor.getColumnIndex("appointment_date")),
                        cursor.getString(cursor.getColumnIndex("diagnosis")),
                        cursor.getString(cursor.getColumnIndex("treatment"))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return appointments;
    }

}
