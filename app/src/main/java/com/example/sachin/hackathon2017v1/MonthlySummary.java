package com.example.sachin.hackathon2017v1;

import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MonthlySummary extends AppCompatActivity {
    DBAdapter myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_summary);
    }

    /*
    public int[] getMonthlyData(int month)
    {
        int[] array;
        Cursor cursor = myDb.getAllRows();

        int food = 0 , gas = 0, groceries = 0, pItems = 0, nonE = 0, others = 0;


        if (cursor.moveToFirst()) {

            do {
                if(getMonthValue(cursor.getInt(DBAdapter.COL_DATE)) == month)
                {
                    food = cursor.getInt(DBAdapter.COL_FOOD) + food;
                    gas = cursor.getInt(DBAdapter.COL_GAS) + gas;
                    groceries = cursor.getInt(DBAdapter.COL_GROCEREIES) + groceries;
                    pItems = cursor.getInt(DBAdapter.COL_PITEMS) + pItems;
                    nonE = cursor.getInt(DBAdapter.COL_NE) + nonE;
                }

            } while(cursor.moveToNext());
        }
        array = new int[] {food, gas, groceries, pItems, nonE};
        return array;

    }

    /*public int getMonthValue(int date)
    {
        String value = date.charAt(4) + date.charAt(5);

        return Integer.parseInt(value);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }

    private void openDB() {
        myDb = new DBAdapter(this);
        myDb.open();
    }

    private void closeDB() {
        myDb.close();
    }

    public void addRecord(int date, int food, int gas, int grocereies, int pItem, int nonE, int other) {

        myDb.insertRow(date, food, gas, grocereies, pItem, nonE, other);

        // Cursor cursor = myDb.getRow(date);
        // displayRecordSet(cursor);
    }


    private String getMonth(Cursor cursor) {
        String month = cursor.getString(DBAdapter.COL_DATE);
        String returnValue = null;
        String[] parts = month.split("-");

        returnValue = parts[0] + parts[1];
        return returnValue;
    }

    private String getYear(Cursor cursor) {
        String month = cursor.getString(DBAdapter.COL_DATE);
        String returnValue = null;
        String[] parts = month.split("-");

        returnValue = parts[4] + parts[5];
        return returnValue;
    }


    // KEY_DATE, KEY_FOOD, KEY_GAS, KEY_GROCERIES, KEY_PITEMS, KEY_NE, KEY_OTHER
    // Display an entire recordset to the screen.
    private String displayRecordSet(Cursor cursor) {
        String message = "";
        // populate the message from the cursor

        // Reset cursor to start, checking to see if there's data:
        if (cursor.moveToFirst()) {
            do {
                // Process the data:
                String date = cursor.getString(DBAdapter.COL_DATE);
                int food = cursor.getInt(DBAdapter.COL_FOOD);
                int gas = cursor.getInt(DBAdapter.COL_GAS);
                int groceries = cursor.getInt(DBAdapter.COL_GROCEREIES);
                int pItems = cursor.getInt(DBAdapter.COL_PITEMS);
                int nonE = cursor.getInt(DBAdapter.COL_NE);
                int other = cursor.getInt(DBAdapter.COL_OTHER);
                Log.i(myDb.TAG, "Entered");

                // Append data to the message:
                message += "Date= " + date
                        + ", food= " + food
                        + ", gas= " + gas
                        + ", groceries= " + groceries
                        + ", personal Items= " + pItems
                        + ", Non Essentials= " + nonE
                        + ", other= " + other

                        + "\n";
            } while (cursor.moveToNext());

        }

        // Close the cursor to avoid a resource leak.
        cursor.close();
        return message;
    }

    //Enter date you want to update and the value you want to update
//Enter 0 if you dont want to update that column
    public void updateRecord(int date, int food, int gas, int grocereies, int pItem, int nonE, int other) {
        Cursor cursor = myDb.getRow(date);

        // int  date1 = cursor.getInt(DBAdapter.COL_DATE) + date;
        int food1 = cursor.getInt(DBAdapter.COL_FOOD) + food;
        int gas1 = cursor.getInt(DBAdapter.COL_GAS) + gas;
        int grocereies1 = cursor.getInt(DBAdapter.COL_GROCEREIES) + grocereies;
        int pItem1 = cursor.getInt(DBAdapter.COL_PITEMS) + pItem;
        int nonE1 = cursor.getInt(DBAdapter.COL_NE) + nonE;
        int other1 = cursor.getInt(DBAdapter.COL_OTHER) + other;

        if (myDb.updateRow(date, food1, gas1, grocereies1, pItem1, nonE1, other1)) {
            Log.i("Trying to Update", "True");
        } else Log.i("Trying to Update", "False");


    }

    public boolean doesItExist(int date) {
        try {
            Log.i("doesItExist: ", "Checking DB");
            Cursor cursor = myDb.getAllRows();
            if (cursor.moveToFirst()) {
                Log.i("no ", "dontexist");
                return true;
            } else {
                return false;
            }
        } catch (CursorIndexOutOfBoundsException e) {
            addRecord(date, 0, 0, 0, 0, 0, 0);
            return true;
        }
    }


    public int[] getDailyData(int date)
    {
        int data[];

        try {

            Cursor cursor = myDb.getRow(date);


            int food = cursor.getInt(DBAdapter.COL_FOOD);
            int gas = cursor.getInt(DBAdapter.COL_GAS);
            int groceries = cursor.getInt(DBAdapter.COL_GROCEREIES);
            int pItems = cursor.getInt(DBAdapter.COL_PITEMS);
            int nonE = cursor.getInt(DBAdapter.COL_NE);
            int other = cursor.getInt(DBAdapter.COL_OTHER);

            return (data = new int[]{food, gas, groceries, pItems, nonE, other});
        }
        catch (CursorIndexOutOfBoundsException e){
            addRecord(date,0,0,0,0,0,0);
            return (data = new int[]{0,0,0,0,0,0});
        }

    }

    private String getTodayTotal(int date)
    {
        int[] dailyArray = getDailyData(date);
        int total = 0;
        for (int i = 0; i < (dailyArray.length - 2); i++)
        {
            total += dailyArray[i];
        }
        return "" + (dailyArray[dailyArray.length-1] - total);
    }
    */
}
