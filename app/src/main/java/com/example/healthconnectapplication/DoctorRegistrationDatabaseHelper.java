package com.example.healthconnectapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DoctorRegistrationDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DoctorDetails.db";
    private static final int DATABASE_VERSION = 5;  // Increment version to trigger onUpgrade
    private static final String TABLE_NAME = "DoctorDetails";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FIRST_NAME = "firstName";
    private static final String COLUMN_LAST_NAME = "lastName";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONE = "phone";  // Ensure this column is defined
    private static final String COLUMN_DATE_OF_BIRTH = "dateOfBirth";  // Ensure this column is defined

    public DoctorRegistrationDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table with 'phone' column included
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_FIRST_NAME + " TEXT, "
                + COLUMN_LAST_NAME + " TEXT, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_PHONE + " TEXT, "  // Add 'phone' column here
                + COLUMN_DATE_OF_BIRTH + " TEXT" + ");";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 4) {
            // Upgrade to version 4: Add 'phone' and 'dateOfBirth' columns if missing
            // Adding 'phone' column
            String ALTER_TABLE = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_PHONE + " TEXT;";
            db.execSQL(ALTER_TABLE);

            // Adding 'dateOfBirth' column if not already added
            String ALTER_TABLE2 = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_DATE_OF_BIRTH + " TEXT;";
            db.execSQL(ALTER_TABLE2);
        }
    }

    public boolean insertDoctorDetails(String firstName, String lastName, String email, String phone, String dateOfBirth) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PHONE, phone);  // Insert 'phone' value
        values.put(COLUMN_DATE_OF_BIRTH, dateOfBirth);  // Insert 'dateOfBirth' value

        // Insert the values into the table
        long result = db.insert(TABLE_NAME, null, values);

        // Close the database after insertion to free up resources
//        db.close();

        // If the insert is successful, result will not be -1
        return result != -1;
    }
}