package com.example.sachin.hackathon2017v1;

/**
 * Created by ParmJohal on 2017-01-21.
 */


// ------------------------------------ DBADapter.java ---------------------------------------------

// TODO: Change the package to match your project.

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Date;
import java.text.SimpleDateFormat;


// TO USE:
// Change the package (at top) to match your project.
// Search for "TODO", and make the appropriate changes.
public class DBAdapter {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /////////////////////////////////////////////////////////////////////
    //	Constants & Data
    /////////////////////////////////////////////////////////////////////
    // For logging:
    public static final String TAG = "DBAdapter";

    // DB Fields
    /*
     * CHANGE 1:
     */
    // TODO: Setup your fields here:
    public static final String KEY_DATE = "date";
    public static final String KEY_FOOD = "food";
    public static final String KEY_GAS = "gas";
    public static final String KEY_GROCERIES = "groceries";
    public static final String KEY_PITEMS = "personalItems";
    public static final String KEY_NE = "NonEssentials";
    public static final String KEY_OTHER = "other";


    // TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)

    public static final int COL_DATE = 0;
    public static final int COL_FOOD = 1;
    public static final int COL_GAS = 2;
    public static final int COL_GROCEREIES = 3;
    public static final int COL_PITEMS =4;
    public static final int COL_NE = 5;
    public static final int COL_OTHER = 6;


    public static final String[] ALL_KEYS = new String[] {KEY_DATE, KEY_FOOD, KEY_GAS, KEY_GROCERIES, KEY_PITEMS, KEY_NE, KEY_OTHER };

    // DB info: it's name, and the table we are using (just one).
    public static final String DATABASE_NAME = "MyDb";
    public static final String DATABASE_TABLE = "mainTable";
    // Track DB version if a new version of your app changes the format.
    public static final int DATABASE_VERSION = 23;

    private static final String DATABASE_CREATE_SQL =
            "create table " + DATABASE_TABLE
                    + " (" + KEY_DATE + " integer not null, "
                    + KEY_FOOD + " integer not null, "
                    + KEY_GAS + " integer not null, "
                    + KEY_GROCERIES + " integer not null, "
                    + KEY_PITEMS + " integer not null, "
                    + KEY_NE + " integer not null, "
                    + KEY_OTHER + " integer not null"
                    + ");";

    // Context of application who uses us.
    private final Context context;

    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    /////////////////////////////////////////////////////////////////////
    //	Public methods:
    /////////////////////////////////////////////////////////////////////

    public DBAdapter(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    // Open the database connection.
    public DBAdapter open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    // Close the database connection.
    public void close() {
        myDBHelper.close();
    }

    // KEY_DATE, KEY_FOOD, KEY_GAS, KEY_GROCERIES, KEY_PITEMS, KEY_NE, KEY_OTHER
    // Add a new set of values to the database.
    public long insertRow(int date, int food, int gas, int grocereies, int pItem, int nonE, int other) {
		/*
		 * CHANGE 3:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues initialValues = new ContentValues();

        initialValues.put(KEY_DATE, date);
        initialValues.put(KEY_FOOD, food);
        initialValues.put(KEY_GAS, gas);
        initialValues.put(KEY_GROCERIES, grocereies);
        initialValues.put(KEY_PITEMS, pItem);
        initialValues.put(KEY_NE, nonE);
        initialValues.put(KEY_OTHER, other);


        // Insert it into the database.
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRow(int date) {
        String where = KEY_DATE + "=" + date;
        return db.delete(DATABASE_TABLE, where, null) != 0;
    }

    public void deleteAll()
    {
        db.delete(DATABASE_TABLE,null,null);
        db.execSQL("delete from "+ DATABASE_TABLE);
        db.execSQL("TRUNCATE table" + DATABASE_TABLE);
        db.close();
    }

    // Return all data in the database.
    public Cursor getAllRows() {
        String where = null;
        Cursor c = 	db.query(DATABASE_TABLE, ALL_KEYS, where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Get a specific row (by date)
    public Cursor getRow(int date) {
        String where = KEY_DATE + " = " + date ;
        Cursor c = 	db.query(true,DATABASE_TABLE, ALL_KEYS, where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // KEY_DATE, KEY_FOOD, KEY_GAS, KEY_GROCERIES, KEY_PITEMS, KEY_NE, KEY_OTHER
    // Change an existing row to be equal to new data.
    public boolean updateRow(int date, int food, int gas, int grocereies, int pItem, int nonE, int other) {
        String where = KEY_DATE + " = " + date;

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_DATE, date);
        newValues.put(KEY_FOOD, food);
        newValues.put(KEY_GAS, gas);
        newValues.put(KEY_GROCERIES, grocereies);
        newValues.put(KEY_PITEMS, pItem);
        newValues.put(KEY_NE, nonE);
        newValues.put(KEY_OTHER, other);


        // Insert it into the database.
        return db.update(DATABASE_TABLE, newValues, where, null) != 0;
    }

    private int getMonth(String date)
    {
        String returnValue = null;
        String[] parts = date.split("-");

        returnValue = parts[4] + parts[5];

        return Integer.parseInt(returnValue);
    }

    private int getYear(String date)
    {
        String returnValue = null;
        String[] parts = date.split("-");

        returnValue = parts[0] + parts[1] + parts[2] + parts[3];
        return Integer.parseInt(returnValue);
    }

    private int getDay(String date)
    {
        String returnValue = null;
        String[] parts = date.split("-");
        returnValue = parts[6] + parts[7];

        return Integer.parseInt(returnValue);
    }

    private Date convertToDate(String date)
    {
        Date d = new Date(getYear(date),getMonth(date),getDay(date));
        return d;
    }




    /////////////////////////////////////////////////////////////////////
    //	Private Helper Classes:
    /////////////////////////////////////////////////////////////////////

    /**
     * Private class which handles database creation and upgrading.
     * Used to handle low-level database access.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DATABASE_CREATE_SQL);
            Log.w(TAG, " DataBase Created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

            // Recreate new database:
            onCreate(_db);
        }
    }
}
