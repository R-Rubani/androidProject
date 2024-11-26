package com.example.healthconnectapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserDetails.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "UserDetails";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FIRST_NAME = "firstName";
    private static final String COLUMN_LAST_NAME = "lastName";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_DATE_OF_BIRTH = "dateOfBirth";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FIRST_NAME + " TEXT,"
                + COLUMN_LAST_NAME + " TEXT,"
                + COLUMN_PHONE + " TEXT,"
                + COLUMN_DATE_OF_BIRTH + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertUserDetails(String firstName, String lastName, String phone, String dateOfBirth) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_DATE_OF_BIRTH, dateOfBirth);
        long result = db.insert(TABLE_NAME, null, values);
       // db.close();
        return result != -1;
    }
}
