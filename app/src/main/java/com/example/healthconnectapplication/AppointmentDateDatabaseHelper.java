package com.example.healthconnectapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDateDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "appointmentsDate.db";
    public static final int DATABASE_VERSION = 5;

    public static final String TABLE_APPOINTMENT_DATE = "AppointmentDate";
    public static final String COLUMN_PATIENT_ID = "patient_id";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";
    public static final String COLUMN_APPOINTMENT_DATE = "appointment_date";

    private static final String SQL_CREATE_APPOINTMENT_DATE_TABLE =
            "CREATE TABLE " + TABLE_APPOINTMENT_DATE + " (" +
                    COLUMN_PATIENT_ID + " TEXT, " +
                    COLUMN_FIRST_NAME + " TEXT, " +
                    COLUMN_LAST_NAME + " TEXT, " +
                    COLUMN_APPOINTMENT_DATE + " TEXT, " +
                    "PRIMARY KEY (" + COLUMN_PATIENT_ID + ", " + COLUMN_APPOINTMENT_DATE + "));";

    private static final String SQL_DELETE_APPOINTMENT_DATE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_APPOINTMENT_DATE;

    public AppointmentDateDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_APPOINTMENT_DATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_APPOINTMENT_DATE_TABLE);
        onCreate(db);
    }

    // Add appointment data
    public void addAppointmentDate(String patientId, String firstName, String lastName, String appointmentDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PATIENT_ID, patientId);
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_APPOINTMENT_DATE, appointmentDate);
        db.insert(TABLE_APPOINTMENT_DATE, null, values);
    }

    // Fetch all appointments
    public List<AppointmentDate> getAllAppointments() {
        List<AppointmentDate> appointmentsDate = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to get all appointments
        Cursor cursor = db.query(TABLE_APPOINTMENT_DATE, null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String patientId = cursor.getString(cursor.getColumnIndex(COLUMN_PATIENT_ID));
                String firstName = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME));
                String lastName = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME));
                String appointmentDate = cursor.getString(cursor.getColumnIndex(COLUMN_APPOINTMENT_DATE));
                appointmentsDate.add(new AppointmentDate(patientId, firstName, lastName, appointmentDate));
            }
            cursor.close();
        }

        return appointmentsDate;
    }
}
