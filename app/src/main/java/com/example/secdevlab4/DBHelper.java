package com.example.secdevlab4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Hospital.db";

    //User Table
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FNAME = "fName";
    private static final String COLUMN_LNAME = "lName";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";

    //Patient Table
    private static final String TABLE_PATIENTS = "patients";
    private static final String COLUMN_PATIENT_ID = "id";
    private static final String COLUMN_PATIENT_FNAME = "fName";
    private static final String COLUMN_PATIENT_LNAME = "lName";
    private static final String COLUMN_PATIENT_AGE = "age";
    private static final String COLUMN_PATIENT_GENDER = "gender";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //User Table
        String CreateUsersTable = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_FNAME + " TEXT, "
                + COLUMN_LNAME + " TEXT, "
                + COLUMN_EMAIL + " TEXT UNIQUE, "
                + COLUMN_PASSWORD + " TEXT)";

        String CreatePatientsTable = "CREATE TABLE " + TABLE_PATIENTS + "("
                + COLUMN_PATIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PATIENT_FNAME + " TEXT, "
                + COLUMN_PATIENT_LNAME + " TEXT, "
                + COLUMN_PATIENT_AGE + " INTEGER, "
                + COLUMN_PATIENT_GENDER + " TEXT)";

        db.execSQL(CreateUsersTable);
        db.execSQL(CreatePatientsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENTS);
        onCreate(db);
    }

    public boolean insertPatient(String fName, String lName, int age, String gender) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PATIENT_FNAME, fName);
        contentValues.put(COLUMN_PATIENT_LNAME, lName);
        contentValues.put(COLUMN_PATIENT_AGE, age);
        contentValues.put(COLUMN_PATIENT_GENDER, gender);
        long result = myDB.insert(TABLE_PATIENTS, null, contentValues);
        return result != -1;
    }

    public boolean insertData(String fName, String lName, String email, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FNAME, fName);
        contentValues.put(COLUMN_LNAME, lName);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PASSWORD, password);
        long result = myDB.insert(TABLE_USERS, null, contentValues);
        return result != -1;
    }

    public boolean checkUser(String email, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from " + TABLE_USERS + " where email=? and password=?", new String[]{email,password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public boolean checkUserEmail(String email){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from " + TABLE_USERS + " where email=?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public List<Patient> getPatients() {
        List<Patient> patients = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PATIENTS, null);

        if (cursor.moveToFirst()) {
            do {

                patients.add(new Patient(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PATIENT_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PATIENT_FNAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PATIENT_LNAME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PATIENT_AGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PATIENT_GENDER))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return patients;
    }

    public boolean updatePatient(int patientId, String firstName, String lastName, int age, String gender) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PATIENT_FNAME, firstName);
        contentValues.put(COLUMN_PATIENT_LNAME, lastName);
        contentValues.put(COLUMN_PATIENT_AGE, age);
        contentValues.put(COLUMN_PATIENT_GENDER, gender);

        int result = myDB.update(TABLE_PATIENTS, contentValues, COLUMN_PATIENT_ID + "=?", new String[]{String.valueOf(patientId)});
        return result > 0;
    }

    //Deletes the patient from the database from the ID.
    public boolean deletePatient(int patientId) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        int result = myDB.delete(TABLE_PATIENTS, COLUMN_PATIENT_ID + "=?", new String[]{String.valueOf(patientId)});
        return result > 0;
    }
}